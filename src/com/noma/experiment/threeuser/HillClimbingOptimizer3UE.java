package com.noma.experiment.threeuser;

import java.util.HashMap;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.hillclimbing.HillClimbingOptimizer;
import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

/**
 * A specific HillClimbing Algorithm for Three User Scenario
 *
 */
public class HillClimbingOptimizer3UE extends HillClimbingOptimizer {

    public HillClimbingOptimizer3UE(Scenario c, PowerParameter par,
            HillClimbingRuntimeParameter parameter) {
        super(c, par, parameter);
    }


    @Override
    public double objectiveFunction(Scenario scenario, PowerParameter newPowerParameter) {
        assert (scenario instanceof ThreeUserScenario);
        final ThreeUserScenario threeUserScenario = (ThreeUserScenario) scenario;

        final BaseStationManager bm = threeUserScenario.getBaseStationManager();

        final UserEquipment centerB = threeUserScenario.getUserCenterB();
        final UserEquipment centerA = threeUserScenario.getUserCenterA();
        final UserEquipment edgeC = threeUserScenario.getUserEdgeC();

        final BaseStation bsX = threeUserScenario.getBaseStationX();
        final BaseStation bsY = threeUserScenario.getBaseStationY();

        final HashMap<UserEquipment, Double> shannonCapacity = ShannonUtil.getShannonCapacity(bm,
                bsX, bsY, centerA, centerB, edgeC, newPowerParameter);
        final double[] values =
                ArrayUtils.toPrimitive(shannonCapacity.values().stream().toArray(Double[]::new));
        StandardDeviation sd = new StandardDeviation();
        double std = sd.evaluate(values);
        return std;
    }

}
