package com.noma.algorithm;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.noma.entity.BaseStation;
import com.noma.entity.UserEquipment;

/**
 * This class represents power assignment for BaseStations to UserEquipments
 * 
 * Note that power allocation should be normalized.
 * 
 * i.e. for each BaseStation, the sum of allocated power for its connected User Equipments = 1
 *
 */
public class PowerParameter implements Iterable<SimpleEntry<BaseStation, UserEquipment>> {

    HashMap<SimpleEntry<BaseStation, UserEquipment>, Double> baseStationUEToPowerMap =
            new HashMap<>();

    public PowerParameter() {}

    /**
     * Copy constructor
     * 
     * @param parameter
     */
    public PowerParameter(PowerParameter parameter) {
        for (SimpleEntry<BaseStation, UserEquipment> entry : parameter.baseStationUEToPowerMap
                .keySet()) {
            baseStationUEToPowerMap.put(entry, parameter.baseStationUEToPowerMap.get(entry));
        }
    }

    public void setPower(BaseStation bs, UserEquipment user, double power) {
        SimpleEntry<BaseStation, UserEquipment> key = new SimpleEntry<>(bs, user);
        baseStationUEToPowerMap.put(key, power);
    }

    public void addPower(BaseStation bs, UserEquipment user, double powerChange) {
        SimpleEntry<BaseStation, UserEquipment> key = new SimpleEntry<>(bs, user);
        Double oldPower = baseStationUEToPowerMap.get(key);
        baseStationUEToPowerMap.put(key, Math.abs(oldPower + powerChange));
    }

    public double getPower(BaseStation bs, UserEquipment user) {
        SimpleEntry<BaseStation, UserEquipment> key = new SimpleEntry<>(bs, user);
        return baseStationUEToPowerMap.get(key);
    }

    @Override
    public Iterator<SimpleEntry<BaseStation, UserEquipment>> iterator() {
        return baseStationUEToPowerMap.keySet().iterator();
    }

    public Set<BaseStation> getBaseStations() {
        return baseStationUEToPowerMap.keySet().stream().map(e -> e.getKey())
                .collect(Collectors.toSet());
    }

    public HashMap<BaseStation, Double> getPowerSumPerBaseStations() {
        HashMap<BaseStation, Double> map = new HashMap<>();
        for (SimpleEntry<BaseStation, UserEquipment> entry : baseStationUEToPowerMap.keySet()) {
            map.putIfAbsent(entry.getKey(), 0d);
            map.put(entry.getKey(), map.get(entry.getKey()) + baseStationUEToPowerMap.get(entry));
        }
        return map;

    }

    public void normalizePerBS() {
        HashMap<BaseStation, Double> powerSumPerBs = getPowerSumPerBaseStations();
        for (SimpleEntry<BaseStation, UserEquipment> entry : baseStationUEToPowerMap.keySet()) {
            BaseStation baseStation = entry.getKey();
            double powerSum = powerSumPerBs.get(baseStation);
            baseStationUEToPowerMap.put(entry, baseStationUEToPowerMap.get(entry) / powerSum);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("-----\n");
        for (Entry<SimpleEntry<BaseStation, UserEquipment>, Double> e : baseStationUEToPowerMap
                .entrySet()) {
            Double value = e.getValue();
            sb.append(e.getKey().getKey().getName() + " " + e.getKey().getValue().getName() + " "
                    + value + "\n");
        }
        return sb.toString();
    }
}
