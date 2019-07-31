package com.noma.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.PowerParameter;
import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.entity.ShannonCapacityData;
import com.noma.experiment.threeuser.ThreeUserBaseLineOptimizer;
import com.noma.experiment.threeuser.HillClimbingOptimizer3UE;
import com.noma.experiment.threeuser.OfflineSimulatedAnnealingOptimizer3UE;
import com.noma.experiment.threeuser.ThreeUserScenario;

public class Runner {

    public static Runnable getSATask(BiConsumer<Long, List<ShannonCapacityData>> callBack,
            SimulatedAnnealingRuntimeParameter saParameter) {
        return new Runnable() {

            @Override
            public void run() {
                List<ShannonCapacityData> shannonData = new ArrayList<ShannonCapacityData>();
                long startTime = System.currentTimeMillis();
                for (int i = 5; i <= 30; i += 1) {
                    ThreeUserScenario c = new ThreeUserScenario(i);

                    AbstractFiveGPowerOptimizer sa = new OfflineSimulatedAnnealingOptimizer3UE(c,
                            c.getParameter(), saParameter);
                    PowerParameter powers = sa.execute();
                    ShannonCapacityData newMap = new ShannonCapacityData(i);
                    newMap.putAll(c.getShannonCapacities(powers));
                    shannonData.add(newMap);
                }
                callBack.accept(System.currentTimeMillis() - startTime, shannonData);
            }
        };
    }

    public static Runnable getHCRunnable(BiConsumer<Long, List<ShannonCapacityData>> callBack,
            HillClimbingRuntimeParameter hcParameter) {
        return new Runnable() {

            @Override
            public void run() {
                List<ShannonCapacityData> shannonData = new ArrayList<ShannonCapacityData>();
                long startTime = System.currentTimeMillis();
                for (int i = 5; i <= 30; i += 1) {

                    ThreeUserScenario c = new ThreeUserScenario(i);

                    AbstractFiveGPowerOptimizer sa =
                            new HillClimbingOptimizer3UE(c, c.getParameter(), hcParameter);
                    PowerParameter powers = sa.execute();
                    ShannonCapacityData newMap = new ShannonCapacityData(i);
                    newMap.putAll(c.getShannonCapacities(powers));
                    shannonData.add(newMap);
                }
                callBack.accept(System.currentTimeMillis() - startTime, shannonData);
            }
        };


    }

    public static void runNormal(BiConsumer<Long, List<ShannonCapacityData>> callBack,
            PowerParameter powerParameter) {
        List<ShannonCapacityData> shannonData = new ArrayList<ShannonCapacityData>();
        long startTime = System.currentTimeMillis();
        for (int i = 5; i <= 30; i += 1) {

            ThreeUserScenario c = new ThreeUserScenario(i);

            AbstractFiveGPowerOptimizer sa = new ThreeUserBaseLineOptimizer(c);
            PowerParameter powers = sa.execute();
            ShannonCapacityData newMap = new ShannonCapacityData(i);
            newMap.putAll(c.getShannonCapacities(powers));
            shannonData.add(newMap);
        }
        callBack.accept(System.currentTimeMillis() - startTime, shannonData);

    }
}
