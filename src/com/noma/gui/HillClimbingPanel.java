package com.noma.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.experiment.threeuser.HillClimbingOptimizer3UE;

public class HillClimbingPanel extends NomaAlgorithmPanel<HillClimbingRuntimeParameter> {

	private static final long serialVersionUID = 1L;

	private JTextField numOfSteps = new JTextField(2);
	private JTextField zeroThreshold = new JTextField(1);

	public HillClimbingPanel(HillClimbingRuntimeParameter hillClimbingRuntimeParameter,
			GuiExecutorThreadListener listener) {
		super(hillClimbingRuntimeParameter, listener);

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Hill Climbing"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	@Override
	public void updateView(HillClimbingRuntimeParameter parameter) {
		numOfSteps.setText(String.valueOf(parameter.getNumOfSteps()));
		zeroThreshold.setText(String.valueOf(parameter.getZeroThreshold()));
	}

	@Override
	public HillClimbingRuntimeParameter getParameter() {
		return new HillClimbingRuntimeParameter(Integer.valueOf(numOfSteps.getText()),
				Double.valueOf(zeroThreshold.getText()));
	}

	@Override
	protected Class<?> getOptimizerClass() {
		return HillClimbingOptimizer3UE.class;
	}

	@Override
	public void initComponents() {

		numOfSteps = new JTextField(2);
		zeroThreshold = new JTextField(1);

		this.add(new JLabel("Num of steps"));
		this.add(numOfSteps);
		this.add(new JLabel("zero threshold"));
		this.add(zeroThreshold);

	}
}
