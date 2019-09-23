package com.noma.gui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.noma.entity.ShannonCapacityData;

public class ConsoleWriterListener implements GuiExecutorThreadListener {
    private final JTextArea console;
    private final JPanel centerPanel;
    private final String algorithmName;

    public ConsoleWriterListener(String algorithmName, JTextArea console, JPanel centerPanel) {
        super();
        this.console = console;
        this.centerPanel = centerPanel;
        this.algorithmName = algorithmName;
    }

    @Override
    public void before() {
        console.append(algorithmName + " Optimizer started\n");
    }

    @Override
    public void after(Long timeElapsed, List<ShannonCapacityData> shannonList) {
        console.append(algorithmName + " optimizer finished in " + timeElapsed + " ms\n");
        centerPanel.add(new ChartHelper(algorithmName, shannonList).getChart());
        centerPanel.revalidate();
        centerPanel.repaint();
    }

}
