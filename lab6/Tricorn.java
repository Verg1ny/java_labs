package com.company;

import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {
    /*Constant for number of maximum iterations count */
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range){
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    public int numIterations(double x, double y)
    {
        /* Start iteration with 0 count */
        int iterNum = 0;
        double zReal = 0; // Real z part
        double zImaginary = 0; // Imaginary z part

        /*
         * Computes Z[n-1]^2. Complex conjugation at every iteration
         * zReal and zImaginary are parts of complex number. C - is the particular point of fractal,
         * given by x, y. Iterated until |z|>2 or maximum number of iterations reached
         * */
        while (iterNum < MAX_ITERATIONS && zReal * zReal + zImaginary * zImaginary < 4) {
            double zrealUpdated = zReal * zReal - zImaginary * zImaginary + x;
            double zimaginaryUpdated = -2 * zReal * zImaginary + y;
            zReal = zrealUpdated;
            zImaginary = zimaginaryUpdated;
            iterNum += 1;
        }

        /*
         * Return -1 in order to show that the point is inside the boundary
         */
        if (iterNum == MAX_ITERATIONS) {
            return -1;
        }
        return iterNum;
    }

    public String toString(){
        return "Tricorn";
    }
}
