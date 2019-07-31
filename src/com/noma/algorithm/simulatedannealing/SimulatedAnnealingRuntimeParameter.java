package com.noma.algorithm.simulatedannealing;

import com.noma.algorithm.RuntimeParameter;

/**
 * This class represents the parameter used for SimulatedAnnealing
 *
 */
public class SimulatedAnnealingRuntimeParameter implements RuntimeParameter {

    private int numOfSteps;
    private double startingTemperature;
    private double frozenTemperature;
    private double coolingRate;

    public static SimulatedAnnealingRuntimeParameter getDefaultParameter() {

        int numOfSteps = 2;

        double startingTemperature = 1d;
        double frozenTemperature = 0.05d;
        double coolingRate = 0.7d;

        return new SimulatedAnnealingRuntimeParameter(numOfSteps, startingTemperature,
                frozenTemperature, coolingRate);
    }

    public SimulatedAnnealingRuntimeParameter(int numOfSteps, double startingTemperature,
            double frozenTemperature, double coolingRate) {
        super();
        this.numOfSteps = numOfSteps;
        this.startingTemperature = startingTemperature;
        this.frozenTemperature = frozenTemperature;
        this.coolingRate = coolingRate;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public double getStartingTemperature() {
        return startingTemperature;
    }

    public double getFrozenTemperature() {
        return frozenTemperature;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

}
