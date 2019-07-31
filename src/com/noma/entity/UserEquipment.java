package com.noma.entity;

import java.util.HashSet;
import java.util.Set;

import com.noma.helper.Constants;
/**
 * a user equipment may represent a phone, a laptop, or anything which may connect to base stations
 * @author fajar
 *
 */
public class UserEquipment {
	
	private String name;
	private double bandwidth=Constants.DEFAULT_BANDWIDTH;
	private Set<BaseStation>connectedBS=new HashSet<>();

	public String getName() {
		return name;
	}	
	
	public UserEquipment(String name) {
		super();
		this.name = name;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public void addConnectedBaseStation(BaseStation bs) {
		connectedBS.add(bs);
	}

	public Set<BaseStation> getConnectedBS() {
		return connectedBS;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserEquipment) {
			return ((UserEquipment) obj).getName().equals(this.getName());
		}
		return false;
	}
}
