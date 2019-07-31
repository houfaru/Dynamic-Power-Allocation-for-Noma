package com.noma.entity;
import java.util.ArrayList;
import java.util.HashMap;

import com.noma.helper.ComplexNumber;
import com.noma.helper.Constants;
import com.noma.helper.ShannonUtil;
/**
 * This class represents a base station and its
 * 	connected users
 * 	power allocations
 * 	distances
 * @author fajar
 *
 */
public class BaseStation {
	
	private String name;
	
	private ArrayList<UserEquipment>users=new ArrayList<>();
	private HashMap<UserEquipment,Double> allocatedPowerMap=new HashMap<>();
	private HashMap<UserEquipment,Double> distanceMap=new HashMap<>();
	
	public String getName() {
		return name;
	}
	
	public BaseStation(String name) {
		super();
		this.name = name;
	}

	public void connectUser(UserEquipment user,double power) {
		users.add(user);
		user.addConnectedBaseStation(this);
		assignPower(user,power);
	}
	
	public ArrayList<UserEquipment>getUsers(){
		return users;
	}
	
	public void assignPower(UserEquipment user,double power) {
		if(power<0) {
			throw new RuntimeException("power must be between 0 and 1");
		}
		double powerSum = getPowerSum();
		if(powerSum+power>1) {
			//System.out.println("warning needs normalization");
		}		
		allocatedPowerMap.put(user, power);
	}
	
	
	public double getPower(UserEquipment u) {
		return allocatedPowerMap.get(u);
	}
	
	public void setDistance(UserEquipment u,double distance) {
		distanceMap.put(u, distance);
	}
	
	public double getDistance(UserEquipment u) {
		return distanceMap.get(u);
	}
	
	public double getDistanceVariance(UserEquipment u) {
		return Math.pow(distanceMap.get(u), Constants.PATH_LOSS_EXP);
	}
	
	public ComplexNumber[] getChannelCoefficient(UserEquipment u) {
		return ShannonUtil.getChannelCoefficient(0, getDistance(u), Constants.CHANNEL_SIZE);
	}
	
	public double[] getChannelGain(UserEquipment u) {
		ComplexNumber[] channelCoefficient = getChannelCoefficient(u);
		return ShannonUtil.getChainnelGain(channelCoefficient);
	}
	
	public double getPowerSum() {	
		return allocatedPowerMap.values().stream().reduce((a,b)->a+b).orElse((double) 0);
	}
	
}
