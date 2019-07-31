package com.noma.algorithm.hillclimbing;

import com.noma.algorithm.RuntimeParameter;

public class HillClimbingRuntimeParameter implements RuntimeParameter {
	private int numOfSteps;
	private double zeroThreshold;
	public static HillClimbingRuntimeParameter getDefaultParameter() {
		return new HillClimbingRuntimeParameter(2, 0.4);
	}
	
	
	public HillClimbingRuntimeParameter(int numOfSteps, double zeroThreshold) {
		this.numOfSteps = numOfSteps;
		this.zeroThreshold = zeroThreshold;
	}
	public int getNumOfSteps() {
		return numOfSteps;
	}
	public double getZeroThreshold() {
		return zeroThreshold;
	}
	
}
