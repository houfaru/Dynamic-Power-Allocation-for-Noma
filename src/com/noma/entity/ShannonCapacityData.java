package com.noma.entity;

import java.util.HashMap;
import java.util.Set;

public class ShannonCapacityData extends HashMap<UserEquipment,Double>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int snrdb;
	public ShannonCapacityData(int snrdb) {
		super();
		this.snrdb=snrdb;
	}
	public double getCapacity(UserEquipment e) {
		return this.get(e);
	}
	public Set<UserEquipment> getUserEquipment() {
		return this.keySet();
	}
	public int getSNRDB() {
		return snrdb;
	}
}
