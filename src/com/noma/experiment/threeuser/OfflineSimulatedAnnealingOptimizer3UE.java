package com.noma.experiment.threeuser;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.simulatedannealing.OfflineSimulatedAnnealingOptimizer;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public class OfflineSimulatedAnnealingOptimizer3UE extends OfflineSimulatedAnnealingOptimizer {

    public OfflineSimulatedAnnealingOptimizer3UE(Scenario c, PowerParameter par,
            SimulatedAnnealingRuntimeParameter parameter) {
        super(c, par, parameter);
    }

    @Override
    public double objectiveFunction(Scenario c, PowerParameter newPar) {
        assert (c instanceof ThreeUserScenario);
        ThreeUserScenario threeUserC = (ThreeUserScenario) c;

        BaseStationManager bm = threeUserC.getBaseStationManager();

        UserEquipment centerA = threeUserC.getUserCenterA();
        UserEquipment centerB = threeUserC.getUserCenterB();
        UserEquipment edgeC = threeUserC.getUserEdgeC();

        BaseStation bsX = threeUserC.getBaseStationX();
        BaseStation bsY = threeUserC.getBaseStationY();

        HashMap<UserEquipment, Double> shannonCapacity =
                ShannonUtil.getShannonCapacity(bm, bsX, bsY, centerA, centerB, edgeC, newPar);
        double[] values =
                ArrayUtils.toPrimitive(shannonCapacity.values().stream().toArray(Double[]::new));
        StandardDeviation sd = new StandardDeviation();
        double std = sd.evaluate(values);
        return std;
    }

}
