package com.noma.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.noma.algorithm.AbstractFiveGPowerOptimizer;
import com.noma.algorithm.RuntimeParameter;
import com.noma.experiment.Runner;

public abstract class NomaAlgorithmPanel<T extends RuntimeParameter, R extends AbstractFiveGPowerOptimizer<T>>
        extends JPanel {

    private static final long serialVersionUID = 1L;

    protected JButton executeButton = new JButton("execute");

    public NomaAlgorithmPanel(Class<?> clazz, GuiExecutorThreadListener listener, T parameter) {
        initInputElements();
        updateView(parameter);
        this.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                listener.before();
                Thread normalThread =
                        new Thread(Runner.getOptimizerThreadRunnable(clazz, listener::after, parameter));
                normalThread.start();

            }
        });

    }

    public abstract void updateView(T runtimeParameter);

    public abstract T getParameter();

    public abstract void initInputElements();

}
