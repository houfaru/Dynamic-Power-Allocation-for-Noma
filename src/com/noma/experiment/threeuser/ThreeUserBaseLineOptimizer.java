package com.noma.experiment.threeuser;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public class ThreeUserBaseLineOptimizer extends AbstractFiveGPowerOptimizer {

    public ThreeUserBaseLineOptimizer(Scenario c) {
        super(c, initParameter(c));
    }

    private static PowerParameter initParameter(Scenario c) {
        PowerParameter powerParameter = new PowerParameter();
        ThreeUserScenario scenario = (ThreeUserScenario) c;
        powerParameter.setPower(scenario.baseStationX, scenario.getUserCenterA(), 1 / 2);
        powerParameter.setPower(scenario.baseStationX, scenario.getUserEdgeC(), 1 / 2);
        powerParameter.setPower(scenario.baseStationY, scenario.getUserCenterB(), 1 / 2);
        powerParameter.setPower(scenario.baseStationY, scenario.getUserEdgeC(), 1 / 2);
        return powerParameter;
    }

    @Override
    public double objectiveFunction(Scenario c, PowerParameter newPar) {
        assert (c instanceof ThreeUserScenario);
        ThreeUserScenario threeUserScenario = (ThreeUserScenario) c;

        BaseStationManager bm = threeUserScenario.getBaseStationManager();

        UserEquipment centerB = threeUserScenario.getUserCenterB();
        UserEquipment centerA = threeUserScenario.getUserCenterA();
        UserEquipment edgeC = threeUserScenario.getUserEdgeC();

        BaseStation bsX = threeUserScenario.getBaseStationX();
        BaseStation bsY = threeUserScenario.getBaseStationY();

        HashMap<UserEquipment, Double> shannonCapacity =
                ShannonUtil.getShannonCapacity(bm, bsX, bsY, centerA, centerB, edgeC, newPar);
        double[] values =
                ArrayUtils.toPrimitive(shannonCapacity.values().stream().toArray(Double[]::new));
        StandardDeviation sd = new StandardDeviation();
        double std = sd.evaluate(values);
        return std;
    }

    @Override
    public PowerParameter execute() {
        return getInitialParameter();
    }

}
