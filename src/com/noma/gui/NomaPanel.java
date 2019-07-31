package com.noma.gui;

import javax.swing.JPanel;

import com.noma.algorithm.RuntimeParameter;

public abstract class NomaPanel<T extends RuntimeParameter> extends JPanel {

    private static final long serialVersionUID = 1L;

    public abstract void updateView(T runtimeParameter);

    public abstract T getParameter();
    
}
