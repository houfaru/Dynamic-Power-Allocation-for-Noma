package com.noma.experiment.threeuser;

import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public class ThreeUserScenario implements Scenario {

    private final BaseStationManager bsManager = new BaseStationManager();

    private final BaseStation baseStationX = new BaseStation("BS_X");
    private final BaseStation baseStationY = new BaseStation("BS_Y");

    private final UserEquipment userCenterA = new UserEquipment("UE_CA");
    private final UserEquipment userEdgeC = new UserEquipment("UE_EC");
    private final UserEquipment userCenterB = new UserEquipment("UE_CB");

    private final double snrdb;


    public ThreeUserScenario(double snrdb) {

        this.snrdb = snrdb;
        initBaseStationManager();
        initUserAndBaseStationConnections();
        initUserAndBaseStationDistances();

    };

    public BaseStationManager getBSManager() {
        return bsManager;

    }

    private void initBaseStationManager() {
        bsManager.addBaseStation(baseStationX);
        bsManager.addBaseStation(baseStationY);
        bsManager.setDistance(baseStationX, baseStationY, 2);
        bsManager.setSNRInDB(snrdb);
    }

    private void initUserAndBaseStationConnections() {
        baseStationX.connectUser(userCenterA, 0.2);
        baseStationX.connectUser(userEdgeC, 0.8);

        baseStationY.connectUser(userCenterB, 0.2);
        baseStationY.connectUser(userEdgeC, 0.8);

    };

    private void initUserAndBaseStationDistances() {
        baseStationX.setDistance(userCenterA, 0.1);
        baseStationX.setDistance(userCenterB, 1.6);
        baseStationX.setDistance(userEdgeC, 0.7);

        baseStationY.setDistance(userCenterA, bsManager.getDistance(baseStationX, baseStationY)
                - baseStationX.getDistance(userCenterA));
        baseStationY.setDistance(userCenterB, bsManager.getDistance(baseStationX, baseStationY)
                - baseStationX.getDistance(userCenterB));
        baseStationY.setDistance(userEdgeC, bsManager.getDistance(baseStationX, baseStationY)
                - baseStationX.getDistance(userEdgeC));
    }

    @Override
    public BaseStationManager getBaseStationManager() {
        return bsManager;
    }

    @Override
    public PowerParameter getParameter() {

        PowerParameter powerParameter = new PowerParameter();
        
        powerParameter.setPower(baseStationX, userCenterA, baseStationX.getPower(userCenterA));
        powerParameter.setPower(baseStationX, userEdgeC, baseStationX.getPower(userEdgeC));
        powerParameter.setPower(baseStationY, userEdgeC, baseStationY.getPower(userEdgeC));
        powerParameter.setPower(baseStationY, userCenterB, baseStationY.getPower(userCenterB));

        return powerParameter;
    }

    @Override
    public void applyParameter(PowerParameter powerParameter) {
        powerParameter.normalizePerBS();
        for (SimpleEntry<BaseStation, UserEquipment> simpleEntry : powerParameter) {
            BaseStation bs = simpleEntry.getKey();
            UserEquipment ue = simpleEntry.getValue();

            double power = powerParameter.getPower(bs, ue);
            // warning, no recheck for normalization validation
            bs.assignPower(ue, power);
        }
    }

    public HashMap<UserEquipment, Double> getShannonCapacities(PowerParameter newPar) {

        BaseStationManager bm = this.getBaseStationManager();

        UserEquipment centerA = this.getUserCenterA();
        UserEquipment centerB = this.getUserCenterB();
        UserEquipment edgeC = this.getUserEdgeC();

        BaseStation bsX = this.getBaseStationX();
        BaseStation bsY = this.getBaseStationY();

        return ShannonUtil.getShannonCapacity(bm, bsX, bsY, centerA, centerB, edgeC, newPar);

    }


    public BaseStation getBaseStationX() {
        return baseStationX;
    }

    public BaseStation getBaseStationY() {
        return baseStationY;
    }

    public UserEquipment getUserCenterA() {
        return userCenterA;
    }

    public UserEquipment getUserEdgeC() {
        return userEdgeC;
    }

    public UserEquipment getUserCenterB() {
        return userCenterB;
    }

}
