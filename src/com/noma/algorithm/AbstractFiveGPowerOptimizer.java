package com.noma.algorithm;

import java.util.Iterator;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.noma.entity.BaseStation;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;

public abstract class AbstractFiveGPowerOptimizer implements FiveGPowerOptimizer{
	
	protected Scenario scenario;
	protected PowerParameter initialParameter;
	
	public AbstractFiveGPowerOptimizer(Scenario scenario,PowerParameter parameter) {
		this.scenario=scenario;
		this.initialParameter=parameter;
	}
	
	public void setParameter(PowerParameter parameter) {
		this.initialParameter=parameter;
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
		
		return newParameter;
	}
	
	
}
