package com.noma.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.jfree.ui.tabbedui.VerticalLayout;

import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;

public class Main extends JFrame {

    private static JScrollPane consoleScroller = new JScrollPane();
    private static JTextArea console = new JTextArea(10, 30);

    private static JScrollPane capacityPanelScroller = new JScrollPane();
    private static JPanel capacityPanel = new JPanel();
    private static JPanel fairnessPanel = new JPanel();
    private static JTabbedPane centerTabs = new JTabbedPane();

    public Main() {

        this.setLayout(new BorderLayout());

        JPanel algorithmsPanel = new JPanel();
        this.add(algorithmsPanel, BorderLayout.WEST);
        this.add(initEastControlPanel(), BorderLayout.EAST);

        capacityPanel = new JPanel(new VerticalLayout());
        capacityPanelScroller = new JScrollPane(capacityPanel);
        centerTabs.add("Capacities", capacityPanelScroller);
        centerTabs.add("Fairness", fairnessPanel);

        this.add(centerTabs, BorderLayout.CENTER);

        algorithmsPanel.setLayout(new VerticalLayout());
        algorithmsPanel.add(
                new BaseLinePanel(new ConsoleWriterListener("Base Line", console, capacityPanel)));
        algorithmsPanel.add(new SimulatedAnnealingPanel(
                SimulatedAnnealingRuntimeParameter.getDefaultParameter(),
                new ConsoleWriterListener("Simulated Annealing "
                        + SimulatedAnnealingRuntimeParameter.getDefaultParameter().toString(),
                        console, capacityPanel)));

        algorithmsPanel.add(new HillClimbingPanel(
                HillClimbingRuntimeParameter.getDefaultParameter(),
                new ConsoleWriterListener(
                        "Hill Climbing "
                                + HillClimbingRuntimeParameter.getDefaultParameter().toString(),
                        console, capacityPanel)));



        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        consoleScroller = new JScrollPane(console);
        consoleScroller.setPreferredSize(new Dimension(500, 110));

        southPanel.add(consoleScroller);
        this.add(southPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 600));
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private JPanel initEastControlPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new VerticalLayout());
        JButton clearButton = new JButton("clear result");
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                capacityPanel.removeAll();
                capacityPanel.revalidate();
                capacityPanel.repaint();
            }
        });
        jPanel.add(clearButton);
        return jPanel;

    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
        main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
