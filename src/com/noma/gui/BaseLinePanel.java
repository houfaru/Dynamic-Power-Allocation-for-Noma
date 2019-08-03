package com.noma.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import com.noma.algorithm.RuntimeParameter;
import com.noma.experiment.threeuser.ThreeUserBaseLineOptimizer;

/**
 * A Panel for uniform power allocation
 *
 */
public class BaseLinePanel extends NomaAlgorithmPanel<RuntimeParameter> {

	private static final long serialVersionUID = 1L;

	Thread normalThread;

	public BaseLinePanel(GuiExecutorThreadListener listener) {
		super(null, listener);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "BaseLine"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	@Override
	public void updateView(RuntimeParameter runtimeParameter) {
		// nothing to do

	}

	@Override
	public RuntimeParameter getParameter() {
		// nothing to do
		return null;
	}

	@Override
	protected Class<?> getOptimizerClass() {
		return ThreeUserBaseLineOptimizer.class;
	}

	@Override
	public void initComponents() {
		// nothing to do

	}
}
