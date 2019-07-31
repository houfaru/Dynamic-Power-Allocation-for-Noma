package com.noma.gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.noma.entity.ShannonCapacityData;
import com.noma.entity.UserEquipment;;

public class ChartHelper extends JPanel {
	
	private static final long serialVersionUID = 1L;
	List<ShannonCapacityData> shannonData;
	private String title;
	
    public ChartHelper(String title,List<ShannonCapacityData> shannonData) {
    	this.title=title;
    	this.shannonData=shannonData;
    }

    public ChartPanel getChart() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new Dimension(400, 200));
        return chartPanel;

        
    }

    private XYDataset createDataset() {

    	Set<UserEquipment> users = shannonData.stream().flatMap(s->s.getUserEquipment().stream()).collect(Collectors.toSet());
    	System.out.println(users.toString());
    	XYSeriesCollection dataset = new XYSeriesCollection();
    	for (UserEquipment userEquipment : users) {
    		XYSeries series1 = new XYSeries(userEquipment.getName());
    		for (ShannonCapacityData sds : shannonData) {
        		series1.add(sds.getSNRDB(),sds.get(userEquipment));
    		}
    		dataset.addSeries(series1);
		}
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "JTCOMP_VPNOMA", 
                "snrdb", 
                "Capacity", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false
        );

        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2f));
        
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(4f));        

        
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(1));
        
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }


}