package com.noma.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.experiment.threeuser.OfflineSimulatedAnnealingOptimizer3UE;

/**
 * A Panel for SimulatedAnnealing
 *
 */
public class SimulatedAnnealingPanel extends NomaAlgorithmPanel<SimulatedAnnealingRuntimeParameter> {

	private static final long serialVersionUID = 1L;

	private JTextField numOfSteps;
	private JTextField startingTemperature;
	private JTextField frozenTemperature;
	private JTextField coolingRate;

	public SimulatedAnnealingPanel(SimulatedAnnealingRuntimeParameter saParameter, GuiExecutorThreadListener listener) {
		super(saParameter, listener);

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Simulated Annealing"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	@Override
	public void updateView(SimulatedAnnealingRuntimeParameter parameter) {
		numOfSteps.setText(String.valueOf(parameter.getNumOfSteps()));
		startingTemperature.setText(String.valueOf(parameter.getStartingTemperature()));
		frozenTemperature.setText(String.valueOf(parameter.getFrozenTemperature()));
		coolingRate.setText(String.valueOf(parameter.getCoolingRate()));
	}

	@Override
	public SimulatedAnnealingRuntimeParameter getParameter() {
		return new SimulatedAnnealingRuntimeParameter(Integer.valueOf(numOfSteps.getText()),
				Double.valueOf(startingTemperature.getText()), Double.valueOf(frozenTemperature.getText()),
				Double.valueOf(coolingRate.getText()));
	}

	@Override
	protected Class<?> getOptimizerClass() {
		return OfflineSimulatedAnnealingOptimizer3UE.class;
	}

	@Override
	public void initComponents() {

		numOfSteps = new JTextField(2);
		startingTemperature = new JTextField(1);
		frozenTemperature = new JTextField("0.05");
		coolingRate = new JTextField("0.7");

		this.add(new JLabel("Num of steps"));
		this.add(numOfSteps);
		this.add(new JLabel("Starting temperature"));
		this.add(startingTemperature);
		this.add(new JLabel("Frozen Temperature"));
		this.add(frozenTemperature);
		this.add(new JLabel("Cooling rate"));
		this.add(coolingRate);
	}
}
