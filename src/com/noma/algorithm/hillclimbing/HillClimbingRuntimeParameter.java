package com.noma.algorithm.hillclimbing;

import com.noma.algorithm.RuntimeParameter;

/**
 * 
 * Hill Climbing algorithm requires two stopping condition:<br>
 * - numOfSteps: the number of iterations<br>
 * - zeroThreshold: the stopping treshold for the minimum objective function
 *
 */
public class HillClimbingRuntimeParameter implements RuntimeParameter {

    private final int numOfSteps;
    private final double zeroThreshold;

    public static HillClimbingRuntimeParameter getDefaultParameter() {
        return new HillClimbingRuntimeParameter(2, 0.4);
    }

    public HillClimbingRuntimeParameter(int numOfSteps, double zeroThreshold) {
        this.numOfSteps = numOfSteps;
        this.zeroThreshold = zeroThreshold;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public double getZeroThreshold() {
        return zeroThreshold;
    }

}
