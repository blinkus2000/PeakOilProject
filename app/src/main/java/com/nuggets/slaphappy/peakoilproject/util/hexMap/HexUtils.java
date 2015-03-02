package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public abstract class HexUtils {
    /**
     * This returns a list of directions ordered by best guess direction first
     * So if the finish location was to the north east but weight heavily east the list would
     */
    public static List<HexDirection> bestGuess(HexLoc start, HexLoc dest, HexLoc limit) {
        List<HexDirection> list = new LinkedList<>();
        final EnumMap<HexDirection, Pair<HexLoc, Integer>> map = HexDirection.getMap();
        for (HexDirection dir : HexDirection.dvalues) {
            final HexLoc translate = dir.translate(start);
            if (limit.contains(translate)) {
                map.put(dir, new Pair<>(translate, hexDistance(translate, dest)));
                list.add(dir);
            }
        }
        Collections.sort(list, new Comparator<HexDirection>() {
            @Override
            public int compare(HexDirection lhs, HexDirection rhs) {
                return map.get(lhs).second.compareTo(map.get(rhs).second);
            }
        });

        return list;
    }

    public static int hexDistance(HexLoc start, HexLoc dest) {
        if (start.true_x > dest.true_x) {
            return hexDistance(dest, start);
        }
        final int yDiff = dest.second - start.second;
        final int xDiff = dest.true_x - start.true_x;
        if (start.true_x == dest.true_x) {
            return Math.abs(yDiff);
        } else if (start.second == dest.second) {
            return Math.abs(dest.first - start.first);
        }
        if (xDiff > 0 == yDiff > 0) {
            return Math.abs(xDiff + yDiff);
        } else {
            int dy = Math.abs(yDiff);
            int dx = Math.abs(xDiff);
            return Math.max(dx, dy);
        }

    }

    public static <T>  HexPath<T> getShortestPath(HexTile<T> start, HexTile<T> finish, HexFilter<T> filter, HexCost<T> travelWeight) {
        return new ShortestPath<>(start, finish, filter,travelWeight).getShortestPath();
    }

    private static class ShortestPath<T> {
        final PriorityQueue<A_StarNode<T>> frontier;
        final HashMap<HexTile<T>, A_StarNode<T>> map;
        final HexLoc finish;
        final HexTile<T> START, GOAL;
        final HexFilter<T> filter;
        final HexCost<T> weight;
        /**
         * @param startPoint - starting tile
         * @param finish  -  ending tile desired
         * @param filter - tiles not accepted by the filter are considered blocked
         * @param travelWeight - returns the weighted cost for travelling from tile A to B
         * */
        public ShortestPath(HexTile<T> startPoint, HexTile<T> finish, HexFilter<T> filter, HexCost<T> travelWeight) {
            this.finish = finish.loc();
            final Integer x = this.finish.first;
            final Integer y = this.finish.second;
            final Integer xSqr = x^2;
            final Integer ySqr = y^2;
            final Integer result = Math.max(5,Math.max(xSqr,ySqr));
            frontier = new PriorityQueue<>(result);
            map = new HashMap<>();
            START = startPoint;
            GOAL = finish;
            this.filter = filter;
            weight = travelWeight;
        }

        public HexPath<T> getShortestPath() {
            updateNode(START, START, 0, HexDirection.Center);
            while (!frontier.isEmpty()) {
                A_StarNode<T> current = frontier.poll();
                if (current.parent.equals(GOAL)) {
                    break;
                }
                for (HexDirection dir : current.parent.getPossibleDirections()) {
                    HexTile<T> next = current.parent.nodes.get(dir);
                    if (filter.accept(next)) {
                        int newCost = current.steps + weight.getCost(current.parent,next);
                        updateNode(current.parent, next, newCost, dir);
                    }
                }
            }
            A_StarNode node = map.get(GOAL);
            if (node != null) {
                HexPath<T> path = new HexPath<>();
                while (node.parent != node.visitingFrom) {
                    path.addFirst(node.parent,node.steps);
                    node = map.get(node.visitingFrom);
                }
                path.addFirst(START,0);
                return path;
            } else {
                return null;
            }
        }

        private void updateNode(HexTile<T> visitingFrom, HexTile<T> visiting, int newCost, HexDirection dir) {
            if (map.get(visiting) == null) {
                final A_StarNode value = new A_StarNode(visitingFrom, visiting, newCost);
                map.put(visiting, value);
                frontier.add(value);
            }else{
                final A_StarNode node = map.get(visiting);
                if (newCost<node.steps) {
                    frontier.remove(node);
                    node.updateVals(visitingFrom, newCost);
                    frontier.add(node);
                }
            }
        }


        private class A_StarNode<T> implements Comparable<A_StarNode<T>> {
            private final HexTile<T> parent;
            private HexTile<T> visitingFrom;
            Integer steps;
            Integer priority = null;

            A_StarNode(HexTile<T> visitingFrom, HexTile<T> parent, int steps) {
                this.parent = parent;
                updateVals(visitingFrom, steps);

            }

            private void updateVals(HexTile<T> visitingFrom, int steps) {
                this.visitingFrom = visitingFrom;
                this.steps = steps;
                updateCurrentCost();
            }

            private void updateCurrentCost() {
                priority = steps + hexDistance(this.parent.loc(), finish);
            }

            @Override
            public int compareTo(@NonNull A_StarNode<T> another) {
                if (this.priority == null) {
                    if (another.priority == null) {
                        return 0;
                    } else return -1;
                } else if (another.priority == null) {
                    return 1;
                }
                return priority.compareTo(another.priority);
            }
        }


    }

}


