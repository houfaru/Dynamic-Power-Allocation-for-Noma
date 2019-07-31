package com.noma.algorithm;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public interface FiveGPowerOptimizer {

    /**
     * Our optimizer tries to increase fairness. So, we define the objective function to be a
     * minimization of the standard deviation for the shannon capacities per user
     * 
     * @param scenario
     * @param powerParameter
     * @return
     */
    public default double objectiveFunction(Scenario scenario, PowerParameter powerParameter) {

        HashMap<UserEquipment, Double> shannonCapacityPerUE =
                ShannonUtil.getShannonCapacity(scenario.getBaseStationManager(), powerParameter);

        double[] allShannonCapacities = ArrayUtils
                .toPrimitive(shannonCapacityPerUE.values().stream().toArray(Double[]::new));

        StandardDeviation sdCalculator = new StandardDeviation();
        double standardDeviation = sdCalculator.evaluate(allShannonCapacities);

        return standardDeviation;
    };

    public PowerParameter randomizeParameter(PowerParameter parameter);

    public PowerParameter getInitialParameter();

    public PowerParameter execute();

}
