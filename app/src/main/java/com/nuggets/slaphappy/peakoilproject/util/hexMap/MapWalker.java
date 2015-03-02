package com.nuggets.slaphappy.peakoilproject.util.hexMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jack on 19/02/2015.
 */
public abstract class MapWalker<T> {
    protected HashSet<HexTile<T>> visited = new HashSet<>();
    protected final HexTile<T> startPoint;
    protected final WalkPriority<T> walkPriority;
    protected final HexFilter<T> filter;
    protected boolean isFinished = false;

    public static abstract class WalkPriority<T> implements Comparator<HexDirection> {
        private HexTile<T> sourceNode;

        public HexTile<T> getSource() {
            return sourceNode;
        }

        public void setSource(HexTile<T> sourceNode) {
            this.sourceNode = sourceNode;
        }
    }

    public MapWalker(HexTile<T> startPoint, WalkPriority<T> walkPriority, HexFilter<T> filter) {
        this.startPoint = startPoint;
        this.walkPriority = walkPriority;
        this.filter = filter;
    }

    public MapWalker(HexTile<T> startPoint) {
        this(startPoint, null, new HexFilter() {
            @Override
            public boolean accept(HexTile val) {
                return true;
            }
        });
    }

    public final void visit() {
        visit(startPoint,0);
    }

    private final void visit(HexTile<T> node,int stepNumber) {
        if (filter.accept(node) && !visited.contains(node)&&!isFinished) {
            visited.add(node);
            visitOperation(node, stepNumber);

            List<HexDirection> values = getOptimalDirections(node);

            for (HexDirection key : values) {
                visit(node.nodes.get(key), stepNumber+1);
            }
        }
    }

    private List<HexDirection> getOptimalDirections(HexTile<T> node) {
        List<HexDirection> list = node.getPossibleDirections();
        if (walkPriority != null) {
            walkPriority.setSource(node);
            Collections.sort(list, walkPriority);
        }
        return list;
    }
    public abstract void visitOperation(HexTile<T> visiting, int stepNumber);

}
