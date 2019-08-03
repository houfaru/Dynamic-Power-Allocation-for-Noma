package com.noma.experiment;

import java.util.AbstractMap.SimpleEntry;

import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;

public interface Scenario {
	public BaseStationManager getBaseStationManager();
	public default PowerParameter getParameter() {
		PowerParameter powerParameter=new PowerParameter();
		BaseStationManager baseStationManager = getBaseStationManager();
		for (BaseStation baseStation : baseStationManager.getBaseStations()) {
			for (UserEquipment userEquipment : baseStationManager.getUsers()) {
				if(baseStation.isUserConnected(userEquipment)) {
					powerParameter.setPower(baseStation, userEquipment, baseStation.getPower(userEquipment));
				}
			}
		}
		return powerParameter;
	};
	public default void applyParameter(PowerParameter powerParameter){
		powerParameter.normalizePerBS();
		for (SimpleEntry<BaseStation, UserEquipment> simpleEntry : powerParameter) {
			BaseStation baseStation = simpleEntry.getKey();
			UserEquipment userEquipment = simpleEntry.getValue();
			baseStation.assignPower(userEquipment, powerParameter.getPower(baseStation, userEquipment));
		}
	}
}
