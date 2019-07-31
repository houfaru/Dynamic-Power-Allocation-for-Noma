package com.noma.experiment.threeuser;

import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public class ThreeUserScenario implements Scenario{
	
	BaseStationManager bsManager=new BaseStationManager();
	
	BaseStation baseStationX=new BaseStation("BS_X");
	BaseStation baseStationY=new BaseStation("BS_Y");

	UserEquipment userCenterA=new UserEquipment("UE_CA");
	UserEquipment userEdgeC=new UserEquipment("UE_EC");
	UserEquipment userCenterB=new UserEquipment("UE_CB");
	
	private double snrdb;
	
	
	public ThreeUserScenario(double snrdb) {
		
		this.snrdb=snrdb;
		initBsManager();
		initConnections();
		initDistances();
	
	};
	public BaseStationManager getBSManager() {
		return bsManager;
		
	}
	private void initBsManager() {
		bsManager.addBaseStation(baseStationX);
		bsManager.addBaseStation(baseStationY);
		bsManager.setDistance(baseStationX,baseStationY,2);
		bsManager.setSNRInDB(snrdb);
	}
	
	private void initConnections() {
		baseStationX.connectUser(userCenterA, 0.2);
		baseStationX.connectUser(userEdgeC, 0.8);
		
		baseStationY.connectUser(userCenterB, 0.2);
		baseStationY.connectUser(userEdgeC, 0.8);
		
	};
	
	private void initDistances() {
		baseStationX.setDistance(userCenterA, 0.1);
		baseStationX.setDistance(userCenterB, 1.6);
		baseStationX.setDistance(userEdgeC, 0.7);
		
		baseStationY.setDistance(
				userCenterA,
					bsManager.getDistance(baseStationX, baseStationY)
					-baseStationX.getDistance(userCenterA));
		baseStationY.setDistance(
				userCenterB, 
					bsManager.getDistance(baseStationX, baseStationY)
					-baseStationX.getDistance(userCenterB));
		baseStationY.setDistance(
				userEdgeC, 
					bsManager.getDistance(baseStationX, baseStationY)
					-baseStationX.getDistance(userEdgeC));
	}

	@Override
	public BaseStationManager getBaseStationManager() {
		return bsManager;
	}
	
	@Override
	public PowerParameter getParameter() {
		
		PowerParameter p=new PowerParameter();
		p.setPower(baseStationX,userCenterA, baseStationX.getPower(userCenterA));
		p.setPower(baseStationX,userEdgeC, baseStationX.getPower(userEdgeC));
		p.setPower(baseStationY,userEdgeC, baseStationY.getPower(userEdgeC));
		p.setPower(baseStationY,userCenterB, baseStationY.getPower(userCenterB));
		
		return p;
	}
	@Override
	public void applyParameter(PowerParameter p) {
		p.normalizePerBS();
		for (SimpleEntry<BaseStation, UserEquipment> simpleEntry : p) {
			BaseStation bs = simpleEntry.getKey();
			UserEquipment ue = simpleEntry.getValue();
			
			double power = p.getPower(bs, ue);
			//warning, no recheck for normalization validation
			bs.assignPower(ue, power);
		}
	}
	
	public HashMap<UserEquipment, Double> getShannonCapacities( PowerParameter newPar)
	{
		
		
		BaseStationManager bm=this.getBaseStationManager();
		
		UserEquipment centerA=this.getUserCenterA();
		UserEquipment centerB=this.getUserCenterB();
		UserEquipment edgeC=this.getUserEdgeC();
		
		BaseStation bsX=this.getBaseStationX();
		BaseStation bsY=this.getBaseStationY();
		
		return ShannonUtil.getShannonCapacity(bm, bsX, bsY, centerA, centerB, edgeC, newPar);
		
	}
	
	public BaseStationManager getBsManager() {
		return bsManager;
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