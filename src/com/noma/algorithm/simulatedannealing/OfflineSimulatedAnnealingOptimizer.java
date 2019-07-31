package com.noma.algorithm.simulatedannealing;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.experiment.Scenario;

public class OfflineSimulatedAnnealingOptimizer
        extends AbstractFiveGPowerOptimizer<SimulatedAnnealingRuntimeParameter> {


    public OfflineSimulatedAnnealingOptimizer(Scenario scenario, PowerParameter powerParameter,
            SimulatedAnnealingRuntimeParameter runtimeParameter) {
        super(scenario, powerParameter, runtimeParameter);

    }

    public OfflineSimulatedAnnealingOptimizer(Scenario scenario, PowerParameter powerParameter) {
        super(scenario, powerParameter, SimulatedAnnealingRuntimeParameter.getDefaultParameter());
    }

    /**
     * A probability of accepting a worse PowerParameter<br>
     * to avoid local minima
     * 
     * @param oldCost
     * @param newCost
     * @param temperature
     * @return
     */
    public double acceptanceProbability(double oldCost, double newCost, double temperature) {
        return Math.pow(Math.E, (newCost - oldCost) / temperature);
    }

    @Override
    public PowerParameter execute() {
        double currentTemperature = runtimeParameter.getStartingTemperature();
        while (currentTemperature > runtimeParameter.getFrozenTemperature()) {

            for (int i = 0; i < runtimeParameter.getNumOfSteps(); i++) {
                final PowerParameter oldParameter = scenario.getParameter();
                final PowerParameter newParameter = randomizeParameter(oldParameter);

                final double oldCost = objectiveFunction(scenario, oldParameter);
                final double newCost = objectiveFunction(scenario, newParameter);

                if (newCost < oldCost) {
                    scenario.applyParameter(newParameter);
                } else {
                    boolean acceptNewSolution = acceptanceProbability(oldCost, newCost,
                            currentTemperature) > Math.random();
                    if (acceptNewSolution) {
                        scenario.applyParameter(newParameter);
                    }
                }
                currentTemperature = (double) currentTemperature * runtimeParameter.getCoolingRate();
            }
        }

        return scenario.getParameter();

    }


}
