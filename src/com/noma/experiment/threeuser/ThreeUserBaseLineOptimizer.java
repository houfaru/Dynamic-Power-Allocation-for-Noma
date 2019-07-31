package com.noma.experiment.threeuser;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.RuntimeParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

/**
 * 
 * An 'optimizer' which returns uniform power assignment
 *
 */
public class ThreeUserBaseLineOptimizer extends AbstractFiveGPowerOptimizer<RuntimeParameter> {

    public ThreeUserBaseLineOptimizer(Scenario c) {
        super(c, initParameter(c), null);
    }

    private static PowerParameter initParameter(Scenario scenario) {
        PowerParameter powerParameter = new PowerParameter();
        ThreeUserScenario threeUserScenario = (ThreeUserScenario) scenario;
        powerParameter.setPower(threeUserScenario.getBaseStationX(),
                threeUserScenario.getUserCenterA(), 1 / 2);
        powerParameter.setPower(threeUserScenario.getBaseStationX(),
                threeUserScenario.getUserEdgeC(), 1 / 2);
        powerParameter.setPower(threeUserScenario.getBaseStationY(),
                threeUserScenario.getUserCenterB(), 1 / 2);
        powerParameter.setPower(threeUserScenario.getBaseStationY(),
                threeUserScenario.getUserEdgeC(), 1 / 2);
        return powerParameter;
    }

    @Override
    public double objectiveFunction(Scenario scenario, PowerParameter powerParameter) {
        assert (scenario instanceof ThreeUserScenario);
        final ThreeUserScenario threeUserScenario = (ThreeUserScenario) scenario;

        final BaseStationManager bm = threeUserScenario.getBaseStationManager();

        final UserEquipment centerB = threeUserScenario.getUserCenterB();
        final UserEquipment centerA = threeUserScenario.getUserCenterA();
        final UserEquipment edgeC = threeUserScenario.getUserEdgeC();

        final BaseStation bsX = threeUserScenario.getBaseStationX();
        final BaseStation bsY = threeUserScenario.getBaseStationY();

        final HashMap<UserEquipment, Double> shannonCapacity = ShannonUtil.getShannonCapacity(bm, bsX,
                bsY, centerA, centerB, edgeC, powerParameter);
        final double[] values =
                ArrayUtils.toPrimitive(shannonCapacity.values().stream().toArray(Double[]::new));
        final StandardDeviation sd = new StandardDeviation();
        double std = sd.evaluate(values);
        return std;
    }

    @Override
    public PowerParameter execute() {
        return getInitialParameter();
    }

}
