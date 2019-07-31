package com.noma.experiment;

import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStationManager;

public interface Scenario {
	public BaseStationManager getBaseStationManager();
	public PowerParameter getParameter();
	public void applyParameter(PowerParameter p);
}
