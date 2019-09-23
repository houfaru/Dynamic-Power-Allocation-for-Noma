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
public class BaseLinePanel
        extends NomaAlgorithmPanel<RuntimeParameter, ThreeUserBaseLineOptimizer> {

    private static final long serialVersionUID = 1L;

    public BaseLinePanel(GuiExecutorThreadListener listener) {
        super(ThreeUserBaseLineOptimizer.class, listener, null);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "BaseLine"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    }

    @Override
    public void initInputElements() {
        // do nothing
    }

    @Override
    public void updateView(RuntimeParameter runtimeParameter) {
        // do nothing
    }

    @Override
    public RuntimeParameter getParameter() {
        // do nothing
        return null;
    }



}
