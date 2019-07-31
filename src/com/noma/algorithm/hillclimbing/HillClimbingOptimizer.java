package com.noma.algorithm.hillclimbing;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.experiment.Scenario;

public class HillClimbingOptimizer
        extends AbstractFiveGPowerOptimizer<HillClimbingRuntimeParameter> {

    public HillClimbingOptimizer(Scenario scenario, PowerParameter powerParameter,
            HillClimbingRuntimeParameter runtimeParameter) {
        super(scenario, powerParameter, runtimeParameter);
    }

    public HillClimbingOptimizer(Scenario scenario, PowerParameter powerParameter) {
        super(scenario, powerParameter, HillClimbingRuntimeParameter.getDefaultParameter());
    }

    @Override
    public PowerParameter execute() {
        double oldCost = 1;
        double newCost = 0;
        while (Math.abs(oldCost - newCost) > runtimeParameter.getZeroThreshold()) {

            for (int i = 0; i < runtimeParameter.getNumOfSteps(); i++) {
                PowerParameter oldParameter = scenario.getParameter();
                PowerParameter newParameter = randomizeParameter(oldParameter);

                oldCost = objectiveFunction(scenario, oldParameter);
                newCost = objectiveFunction(scenario, newParameter);

                if (newCost < oldCost) {
                    scenario.applyParameter(newParameter);
                }
            }

        }
        return scenario.getParameter();
    }

}
