package com.noma.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.ui.tabbedui.VerticalLayout;

import com.noma.algorithm.hillclimbing.HillClimbingRuntimeParameter;
import com.noma.algorithm.simulatedannealing.SimulatedAnnealingRuntimeParameter;
import com.noma.entity.ShannonCapacityData;

public class Main extends JFrame {

    private static JScrollPane consoleScroller = new JScrollPane();
    private static JTextArea console = new JTextArea(10, 30);

    private static JScrollPane centerPanelScroller = new JScrollPane();
    private static JPanel centerPanel = new JPanel();



    public Main() {

        this.setLayout(new BorderLayout());

        JPanel algorithmsPanel = new JPanel();
        this.add(algorithmsPanel, BorderLayout.WEST);
        this.add(initEastControlPanel(), BorderLayout.EAST);

        centerPanel = new JPanel(new VerticalLayout());
        centerPanelScroller = new JScrollPane(centerPanel);
        this.add(centerPanelScroller, BorderLayout.CENTER);
        algorithmsPanel.setLayout(new VerticalLayout());
        algorithmsPanel.add(new BaseLinePanel(new GuiExecutorThreadListener() {

            @Override
            public void before() {
                console.append("BaseLine Optimizer started\n");
            }

            @Override
            public void after(Long timeElapsed, List<ShannonCapacityData> shannonList) {
                console.append("BaseLine optimizer finished in " + timeElapsed + " ms\n");
                centerPanel.add(new ChartHelper("BaseLine", shannonList).getChart());
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        }));
        algorithmsPanel.add(new SimulatedAnnealingPanel(
                SimulatedAnnealingRuntimeParameter.getDefaultParameter(),
                new GuiExecutorThreadListener() {

                    @Override
                    public void before() {
                        console.append("simulated annealing started\n");
                    }

                    @Override
                    public void after(Long timeElapsed, List<ShannonCapacityData> shannonList) {
                        console.append("simulated annealing finished in " + timeElapsed + " ms\n");
                        centerPanel.add(new ChartHelper(
                                "Simulated Annealing " + SimulatedAnnealingRuntimeParameter
                                        .getDefaultParameter().toString(),
                                shannonList).getChart());
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
                }));

        algorithmsPanel
                .add(new HillClimbingPanel(HillClimbingRuntimeParameter.getDefaultParameter(),
                        new GuiExecutorThreadListener() {

                            @Override
                            public void before() {
                                console.append("Hill Climbing started\n");
                            }

                            @Override
                            public void after(Long timeElapsed,
                                    List<ShannonCapacityData> shannonList) {
                                console.append(
                                        "Hill Climbing finished in " + timeElapsed + " ms\n");
                                centerPanel
                                        .add(new ChartHelper(
                                                "Hill Climbing " + HillClimbingRuntimeParameter
                                                        .getDefaultParameter().toString(),
                                                shannonList).getChart());
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

    private JPanel initEastControlPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new VerticalLayout());
        JButton clearButton = new JButton("clear result");
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                centerPanel.revalidate();
                centerPanel.repaint();
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
