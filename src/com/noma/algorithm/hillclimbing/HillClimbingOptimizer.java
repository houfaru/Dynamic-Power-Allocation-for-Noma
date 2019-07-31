package com.noma.algorithm.hillclimbing;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.experiment.Scenario;

public class HillClimbingOptimizer extends AbstractFiveGPowerOptimizer {

    HillClimbingRuntimeParameter parameter = HillClimbingRuntimeParameter.getDefaultParameter();

    public HillClimbingOptimizer(Scenario c, PowerParameter par,
            HillClimbingRuntimeParameter parameter) {
        super(c, par);
        this.parameter = parameter;
    }

    @Override
    public PowerParameter execute() {
        double oldCost = 1;
        double newCost = 0;
        while (Math.abs(oldCost - newCost) > parameter.getZeroThreshold()) {

            for (int i = 0; i < parameter.getNumOfSteps(); i++) {
                PowerParameter oldParameter = scenario.getParameter();
                PowerParameter newParameter = randomizeParameter(oldParameter);
                newParameter.normalizePerBS();

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
