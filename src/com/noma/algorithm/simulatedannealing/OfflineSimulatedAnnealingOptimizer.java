package com.noma.algorithm.simulatedannealing;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.experiment.Scenario;

public class OfflineSimulatedAnnealingOptimizer extends AbstractFiveGPowerOptimizer{
	
	SimulatedAnnealingRuntimeParameter parameter=SimulatedAnnealingRuntimeParameter.getDefaultParameter();
	
	public OfflineSimulatedAnnealingOptimizer(Scenario scenario,PowerParameter par,SimulatedAnnealingRuntimeParameter parameter) {
		super(scenario,par);
		this.parameter=parameter;
	}
	
	public double acceptanceProbability(double oldCost, double newCost,double temperature) {
		return Math.pow(Math.E, (newCost-oldCost)/temperature);
	}
	@Override	
	public PowerParameter execute() {
		double currentTemperature=parameter.getStartingTemperature();
		while(currentTemperature>parameter.getFrozenTemperature()) {
			
			for(int i=0;i<parameter.getNumOfSteps();i++) {
				PowerParameter oldParameter=scenario.getParameter();
				PowerParameter newParameter=randomizeParameter(oldParameter);
				newParameter.normalizePerBS();
				
				double oldCost=objectiveFunction(scenario,oldParameter);
				double newCost = objectiveFunction(scenario, newParameter);
				
				if(newCost<oldCost) {
					scenario.applyParameter(newParameter);
				}else {
					boolean acceptNewSolution=acceptanceProbability(oldCost, newCost, currentTemperature)>Math.random();
					if(acceptNewSolution) {
						scenario.applyParameter(newParameter);	
					}
				}
				currentTemperature=(double)currentTemperature*parameter.getCoolingRate();
			}
		}
		
		return scenario.getParameter();
		
	}

	
}
