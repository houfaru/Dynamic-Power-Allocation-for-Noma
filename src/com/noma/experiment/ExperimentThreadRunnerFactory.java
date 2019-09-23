package com.noma.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.RuntimeParameter;
import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.entity.ShannonCapacityData;
import com.noma.experiment.threeuser.ThreeUserBaseLineOptimizer;
import com.noma.experiment.threeuser.HillClimbingOptimizer3UE;
import com.noma.experiment.threeuser.OfflineSimulatedAnnealingOptimizer3UE;
import com.noma.experiment.threeuser.ThreeUserScenario;

public class ExperimentThreadRunnerFactory {

	private static <R extends RuntimeParameter> AbstractFiveGPowerOptimizer<?> getOptimizer(Class<?> clazz,
			Scenario scenario, R r) {
		if (clazz.equals(HillClimbingOptimizer3UE.class)) {
			return new HillClimbingOptimizer3UE(scenario, scenario.getParameter(), (HillClimbingRuntimeParameter) r);
		}
		if (clazz.equals(OfflineSimulatedAnnealingOptimizer3UE.class)) {
			return new OfflineSimulatedAnnealingOptimizer3UE(scenario, scenario.getParameter(),
					(SimulatedAnnealingRuntimeParameter) r);
		}
		if (clazz.equals(ThreeUserBaseLineOptimizer.class)) {
			return new ThreeUserBaseLineOptimizer(scenario);
		}
		throw new RuntimeException("Currently unsupported optimization");
	}

	public static <T extends RuntimeParameter> Runnable getOptimizerThreadRunnable(Class<?> clazz,
			BiConsumer<Long, List<ShannonCapacityData>> callBack, T parameter) {
		return new Runnable() {

			@Override
			public void run() {
				List<ShannonCapacityData> shannonData = new ArrayList<ShannonCapacityData>();
				long startTime = System.currentTimeMillis();
				for (int i = 5; i <= 30; i += 1) {

					ThreeUserScenario c = new ThreeUserScenario(i);

					AbstractFiveGPowerOptimizer<?> sa = getOptimizer(clazz, c, parameter);
					PowerParameter powers = sa.execute();
					ShannonCapacityData newMap = new ShannonCapacityData(i);
					newMap.putAll(c.getShannonCapacities(powers));
					shannonData.add(newMap);
				}
				callBack.accept(System.currentTimeMillis() - startTime, shannonData);
			}
		};

	}

}
