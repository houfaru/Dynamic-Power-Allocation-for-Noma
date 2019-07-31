package com.noma.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.experiment.Runner;

/**
 * A Panel for SimulatedAnnealing taking the parameters
 *
 */
public class SAPanel extends NomaPanel<SimulatedAnnealingRuntimeParameter> {

    private static final long serialVersionUID = 1L;

    private JButton executeButton = new JButton("execute");

    private JTextField numOfSteps = new JTextField(2);
    private JTextField startingTemperature = new JTextField(1);
    private JTextField frozenTemperature = new JTextField("0.05");
    private JTextField coolingRate = new JTextField("0.7");

    private Thread simulatedAnnealingThread;

    public SAPanel(SimulatedAnnealingRuntimeParameter saParameter,
            GuiExecutorThreadListener listener) {

        simulatedAnnealingThread = new Thread(Runner.getSATask(listener::after, saParameter));

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "Simulated Annealing"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Num of steps"));
        this.add(numOfSteps);
        this.add(new JLabel("Starting temperature"));
        this.add(startingTemperature);
        this.add(new JLabel("Frozen Temperature"));
        this.add(frozenTemperature);
        this.add(new JLabel("Cooling rate"));
        this.add(coolingRate);
        this.add(executeButton);
        updateView(saParameter);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!simulatedAnnealingThread.isAlive()) {
                    listener.before();
                    simulatedAnnealingThread = new Thread(Runner.getSATask(listener::after, saParameter));
                    simulatedAnnealingThread.start();
                }

            }
        });
        updateView(saParameter);
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
                Double.valueOf(startingTemperature.getText()),
                Double.valueOf(frozenTemperature.getText()), Double.valueOf(coolingRate.getText()));
    }
}
