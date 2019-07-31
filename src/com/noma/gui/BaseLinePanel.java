package com.noma.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import com.noma.algorithm.RuntimeParameter;
import com.noma.experiment.Runner;

/**
 * A Panel for SimulatedAnnealing taking the parameters
 *
 */
public class BaseLinePanel extends NomaPanel<RuntimeParameter> {

    private static final long serialVersionUID = 1L;

    private JButton executeButton = new JButton("execute");


    public BaseLinePanel(GuiExecutorThreadListener listener) {


        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "BaseLine"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                listener.before();
                Runner.runNormal(listener::after);
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
