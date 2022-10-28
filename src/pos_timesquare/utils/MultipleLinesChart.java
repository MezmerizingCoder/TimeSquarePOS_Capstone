/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.utils;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Acer
 */
public class MultipleLinesChart extends JPanel {
    public MultipleLinesChart() {   // the constructor will contain the panel of a certain size and the close operations 
//        super("XY Line Chart Example with JFreechart"); // calls the super class constructor

        JPanel chartPanel = createChartPanel();    
        chartPanel.setOpaque(false);
//        chartPanel.setLayout(new javax.swing.BoxLayout(chartPanel, javax.swing.BoxLayout.LINE_AXIS));
//        add(chartPanel, BorderLayout.CENTER);

//        setSize(640, 480);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        setOpaque(false);
        add(chartPanel);
    }

    private JPanel createChartPanel() { // this method will create the chart panel containin the graph 
        String chartTitle = "Objects Movement Chart";
        String xAxisLabel = "Months";
        String yAxisLabel = "Request";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(null, 
                xAxisLabel, yAxisLabel, dataset);
        chart.setBackgroundPaint(new Color(0,0,0,0));
        
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setPosition(RectangleEdge.BOTTOM); 
        chart.getLegend().setBackgroundPaint(new Color(0,0,0,0));
        chart.getLegend().setItemPaint(new Color(165, 165, 165));
        chart.setPadding(new RectangleInsets(4, 4, 4, 4));
//        chart.setBackgroundPaint(new Color(0,0,0,0));

        customizeChart(chart);

        // saves the chart as an image files
//        File imageFile = new File("XYLineChart.png");
//        int width = 640;
//        int height = 480;
//
//        try {
//            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }

        return new ChartPanel(chart);
    }

    private XYDataset createDataset() {    // this method creates the data as time seris 
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Email Requests");
        XYSeries series2 = new XYSeries("PDF Requests");
        XYSeries series3 = new XYSeries("Receipt Requests");

        series1.add(1.0, 2.0);
        series1.add(2.0, 3.0);
        series1.add(3.0, 2.5);
        series1.add(3.5, 2.8);
        series1.add(4.2, 6.0);

        series2.add(2.0, 1.0);
        series2.add(2.5, 2.4);
        series2.add(3.2, 1.2);
        series2.add(3.9, 2.8);
        series2.add(4.6, 3.0);

        series3.add(1.2, 4.0);
        series3.add(2.5, 4.4);
        series3.add(3.8, 4.2);
        series3.add(4.3, 3.8);
        series3.add(4.5, 4.0);

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, new Color(0, 144, 228));
        renderer.setSeriesPaint(1, new Color(184, 234, 194));
        renderer.setSeriesPaint(2, new Color(255, 142, 24));

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(new Color(0,0,0,0));
        plot.setOutlineStroke(new BasicStroke(1.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
//        plot.setBackgroundPaint(Color.DARK_GRAY);
        plot.setBackgroundPaint(new Color(0,0,0,0));


        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(165, 165, 165));

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(new Color(165, 165, 165));

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MultipleLinesChart().setVisible(true);
//            }
//        });
//    }
}
