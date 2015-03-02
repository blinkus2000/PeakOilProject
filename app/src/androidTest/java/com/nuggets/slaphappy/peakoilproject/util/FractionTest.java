package com.nuggets.slaphappy.peakoilproject.util;

import junit.framework.TestCase;

public class FractionTest extends TestCase {
    public void testFraction() throws Exception{

    /* Test all three contructors and toString. */
            Fraction f0 = new Fraction();
            Fraction f1 = new Fraction(3);
            Fraction f2 = new Fraction(12, 20);

            System.out.println("\nTesting constructors (and toString):");
            System.out.println("The fraction f0 is " + f0.toString());
            System.out.println("The fraction f1 is " + f1); // toString is implicit
            System.out.println("The fraction f2 is " + f2);

    /* Test methods on Fraction: add and evaluate. */
            System.out.println("\nTesting add and evaluate:");
            System.out.println("The floating point value of " + f1 + " is " +
                    f1.evaluate());
            System.out.println("The floating point value of " + f2 + " is " +
                    f2.evaluate());

    }
}