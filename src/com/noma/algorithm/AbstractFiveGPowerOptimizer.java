package com.noma.algorithm;

import java.util.Iterator;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.noma.entity.BaseStation;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;

public abstract class AbstractFiveGPowerOptimizer<T extends RuntimeParameter> implements FiveGPowerOptimizer{
	
	protected final Scenario scenario;
	protected final PowerParameter initialParameter;
	protected final T runtimeParameter;
	
	public AbstractFiveGPowerOptimizer(Scenario scenario,PowerParameter parameter, T runtimeParameter) {
		this.scenario=scenario;
		this.initialParameter=parameter;
		this.runtimeParameter=runtimeParameter;
	}
	
	@Override
	public PowerParameter getInitialParameter() {
		return initialParameter;
	}
	
	public PowerParameter randomizeParameter(PowerParameter oldParameter) {
		PowerParameter newParameter=new PowerParameter(oldParameter);
		
		double nextDouble = (new Random()).nextDouble()-0.5;
		nextDouble/=10;
		Iterator<SimpleEntry<BaseStation, UserEquipment>> iterator = newParameter.iterator();
		while(iterator.hasNext()) {
			Entry<BaseStation, UserEquipment> entry= iterator.next();
			newParameter.addPower(entry.getKey(), entry.getValue(), nextDouble);
		}
		newParameter.normalizePerBS();
		return newParameter;
	}
	
	
}
