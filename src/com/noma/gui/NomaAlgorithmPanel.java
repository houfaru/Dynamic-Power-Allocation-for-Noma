package com.noma.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.noma.algorithm.RuntimeParameter;
import com.noma.experiment.ExperimentThreadRunnerFactory;

public abstract class NomaAlgorithmPanel<T extends RuntimeParameter> extends JPanel {

	private static final long serialVersionUID = 1L;
	private Thread thread;
	private JButton executeButton = new JButton("execute");

	public NomaAlgorithmPanel(T parameter, GuiExecutorThreadListener listener) {
		initComponents();
		this.add(executeButton);
		updateView(parameter);
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.before();
				thread = new Thread(ExperimentThreadRunnerFactory.getOptimizerThreadRunnable(getOptimizerClass(),
						listener::after, parameter));
				thread.start();
			}
		});
		updateView(parameter);
	}

	protected abstract Class<?> getOptimizerClass();

	public abstract void updateView(T runtimeParameter);

	public abstract T getParameter();

	public abstract void initComponents();

}
