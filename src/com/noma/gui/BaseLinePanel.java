package com.noma.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import com.noma.algorithm.RuntimeParameter;
import com.noma.experiment.Runner;
import com.noma.experiment.threeuser.ThreeUserBaseLineOptimizer;

/**
 * A Panel for uniform power allocation
 *
 */
public class BaseLinePanel extends NomaAlgorithmPanel<RuntimeParameter> {

    private static final long serialVersionUID = 1L;

    private JButton executeButton = new JButton("execute");

    Thread normalThread;

    public BaseLinePanel(GuiExecutorThreadListener listener) {
        normalThread =
                new Thread(Runner.getOptimizerThreadRunnable(ThreeUserBaseLineOptimizer.class,
                        listener::after, null));

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "BaseLine"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!normalThread.isAlive()) {
                    listener.before();
                    normalThread = new Thread(Runner.getOptimizerThreadRunnable(
                            ThreeUserBaseLineOptimizer.class, listener::after, null));
                    normalThread.start();
                }
            }
        });
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
}
