package com.noma.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.experiment.Runner;
import com.noma.experiment.threeuser.HillClimbingOptimizer3UE;

public class HillClimbingPanel extends NomaAlgorithmPanel<HillClimbingRuntimeParameter> {


    private static final long serialVersionUID = 1L;

    private JButton executeButton = new JButton("execute");

    private JTextField numOfSteps = new JTextField(2);
    private JTextField zeroThreshold = new JTextField(1);

    private Thread hillClimbingThread;

    public HillClimbingPanel(HillClimbingRuntimeParameter hillClimbingRuntimeParameter,
            GuiExecutorThreadListener listener) {

        hillClimbingThread = new Thread(
                Runner.getOptimizerThreadRunnable(HillClimbingOptimizer3UE.class,
                        listener::after, hillClimbingRuntimeParameter));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "Hill Climbing"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Num of steps"));
        this.add(numOfSteps);
        this.add(new JLabel("zero threshold"));
        this.add(zeroThreshold);

        this.add(executeButton);
        updateView(hillClimbingRuntimeParameter);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!hillClimbingThread.isAlive()) {
                    listener.before();
                    hillClimbingThread = new Thread(Runner.getOptimizerThreadRunnable(
                            HillClimbingOptimizer3UE.class, listener::after,
                            hillClimbingRuntimeParameter));
                    hillClimbingThread.start();
                }

            }
        });
        updateView(hillClimbingRuntimeParameter);
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
}
