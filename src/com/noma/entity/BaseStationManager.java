package com.noma.entity;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this class manages the base stations and their pairwise distances and keeps
 * track of signal to noise ratio
 *
 */
public class BaseStationManager {

	private double snrDB;
	private List<BaseStation> stations = new ArrayList<>();
	private HashMap<SimpleEntry<BaseStation, BaseStation>, Double> distances = new HashMap<>();

	public void addBaseStation(BaseStation bs) {
		stations.add(bs);
	}

	public BaseStation getBaseStation(int i) {
		return stations.get(i);
	}

	public double getDistance(BaseStation b1, BaseStation b2) {
		SimpleEntry<BaseStation, BaseStation> entry = new SimpleEntry<BaseStation, BaseStation>(b1, b2);
		return distances.get(entry);
	}

	public void setDistance(BaseStation b1, BaseStation b2, double distance) {
		SimpleEntry<BaseStation, BaseStation> entry = new SimpleEntry<BaseStation, BaseStation>(b1, b2);
		distances.put(entry, distance);
	}

	public double getSnrDB() {
		return snrDB;
	}

	public void setSNRInDB(double snrDB) {
		this.snrDB = snrDB;
	}

	public double gettxSNR() {
		return Math.pow(10, snrDB / 10);
	}

	public List<UserEquipment> getUsers() {
		return stations.stream().flatMap(s -> s.getUsers().stream()).collect(Collectors.toList());
	}

	public List<BaseStation> getBaseStations() {
		return stations;
	}

	@Override
	public String toString() {
		String res = "";
		res += getBaseStations() + "\n";
		res += distances.toString();
		return res;
	}
}
