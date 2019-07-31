package com.noma.experiment.threeuser;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.hillclimbing.HillClimbingOptimizer;
import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;
import com.noma.experiment.Scenario;
import com.noma.helper.ShannonUtil;

public class HillClimbingOptimizer3UE extends HillClimbingOptimizer{

	public HillClimbingOptimizer3UE(Scenario c, PowerParameter par, HillClimbingRuntimeParameter parameter) {
		super(c, par,parameter);
	}

	
	@Override
	public double objectiveFunction(Scenario c, PowerParameter newPar) {
		assert (c instanceof ThreeUserScenario);
		ThreeUserScenario threeUserC=(ThreeUserScenario)c;
		
		BaseStationManager bm=threeUserC.getBaseStationManager();
		
		UserEquipment centerB=threeUserC.getUserCenterB();
		UserEquipment centerA=threeUserC.getUserCenterA();
		UserEquipment edgeC=threeUserC.getUserEdgeC();
		
		BaseStation bsX=threeUserC.getBaseStationX();
		BaseStation bsY=threeUserC.getBaseStationY();		
	
		HashMap<UserEquipment, Double> shannonCapacity = ShannonUtil.getShannonCapacity(bm, bsX, bsY, centerA, centerB, edgeC, newPar);
		double[] values = ArrayUtils.toPrimitive(shannonCapacity.values().stream().toArray(Double[]::new));
		StandardDeviation sd=new StandardDeviation();
		double std = sd.evaluate(values);
		return std;
	}
	
}
