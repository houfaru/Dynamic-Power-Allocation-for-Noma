package com.noma.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.entity.ShannonCapacityData;

public class Main extends JFrame {

    private static JScrollPane consoleScroller = new JScrollPane();
    private static JTextArea console = new JTextArea(10, 30);

    private static JPanel centerPanel = new JPanel();

    public Main() {
        BorderLayout l = new BorderLayout();
        this.setLayout(l);

        JPanel westPanel = new JPanel();

        this.add(westPanel, BorderLayout.WEST);

        centerPanel = new JPanel(new FlowLayout());
        this.add(centerPanel, BorderLayout.CENTER);
        westPanel.setLayout(new FlowLayout());
        westPanel.add(new SAPanel(SimulatedAnnealingRuntimeParameter.getDefaultParameter(),
                new GuiExecutorThreadListener() {

                    @Override
                    public void before() {
                        console.append("simulated annealing started\n");
                    }

                    @Override
                    public void after(Long timeElapsed, List<ShannonCapacityData> shannonList) {
                        console.append("simulated annealing finished in " + timeElapsed + "\n");
                        centerPanel.add(
                                new ChartHelper("Simulated Annealing", shannonList).getChart());
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
                }));

        westPanel.add(new HCPanel(HillClimbingRuntimeParameter.getDefaultParameter(),
                new GuiExecutorThreadListener() {

                    @Override
                    public void before() {
                        console.append("Hill Climbing started\n");
                    }

                    @Override
                    public void after(Long timeElapsed, List<ShannonCapacityData> shannonList) {
                        console.append("Hill Climbing finished in " + timeElapsed + "\n");
                        centerPanel.add(new ChartHelper("Hill Climbing", shannonList).getChart());
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
                }));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        consoleScroller = new JScrollPane(console);
        consoleScroller.setPreferredSize(new Dimension(500, 110));

        southPanel.add(consoleScroller);
        this.add(southPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 600));
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        Main m = new Main();
        m.setVisible(true);
        m.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
