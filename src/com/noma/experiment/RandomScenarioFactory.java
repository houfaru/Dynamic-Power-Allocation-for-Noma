package com.noma.experiment;

import java.util.Random;
import java.util.stream.IntStream;

import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;

public class RandomScenarioFactory implements ScenarioFactory {

	private final int numOfUsers;
	private final int numOfBs;

	BaseStationManager bsManager = new BaseStationManager();

	public RandomScenarioFactory(int numOfUsers, int numOfBs) {
		super();
		this.numOfUsers = numOfUsers;
		this.numOfBs = numOfBs;
	}

	private void initBaseStations(BaseStationManager baseStationManager) {
		IntStream.of(numOfBs).forEach(i -> bsManager.addBaseStation(new BaseStation("BS_" + i)));
	}

	private void initUsers() {
		IntStream.of(numOfUsers)
				.forEach(i -> getRandomBaseStation().connectUser(new UserEquipment("UE_" + i), Math.random()));
		for (BaseStation baseStation : bsManager.getBaseStations()) {
			baseStation.normalize();
		}
	}

	private BaseStation getRandomBaseStation() {
		Random r = new Random();
		
		return bsManager.getBaseStations().get(r.nextInt(bsManager.getBaseStations().size()));
	}

	@Override
	public Scenario generateScenario() {
		bsManager = new BaseStationManager();
		initBaseStations(bsManager);
		initUsers();
		return new Scenario() {

			@Override
			public BaseStationManager getBaseStationManager() {
				return bsManager;
			}

		};
	}

}
