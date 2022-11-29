/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.utils;

import com.formdev.flatlaf.util.StringUtils;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;
import pos_timesquare.controller.CustomerService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.controller.ReportService;
import static pos_timesquare.view.MainFrame.buttonGroup;
import static pos_timesquare.view.MainFrame.jComboBox8;
import static pos_timesquare.view.MainFrame.jMonthChooser1;
import static pos_timesquare.view.MainFrame.jPanel64;
import static pos_timesquare.view.MainFrame.jYearChooser2;
import static pos_timesquare.view.MainFrame.totalNewCustomer;
import static pos_timesquare.view.MainFrame.totalRegisteredCustomer;
import pos_timesquare.view.NotificationThumb;

/**
 *
 * @author Acer
 */
public class ReportLineChart extends JPanel {
    
    
    public ReportLineChart() {   // the constructor will contain the panel of a certain size and the close operations 
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
        String chartTitle = "";
        String xAxisLabel = "";
        String yAxisLabel = "";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createTimeSeriesChart(null, 
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
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        TimeSeries series1 = new TimeSeries("Email Requests");
        TimeSeries series2 = new TimeSeries("PDF Requests");
        TimeSeries series3 = new TimeSeries("Receipt Requests");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        Date date = new Date();
        
        
        
        ReportService reportService = new ReportService();
        
        
        HashMap<String, Float> pdfReport = new HashMap<>();
        HashMap<String, Float> emailReport = new HashMap<>();
        HashMap<String, Float> receiptReport = new HashMap<>();

        reportService.getAllReports().forEach(e -> {
            String monthYear = "";
            
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate;
                parsedDate = dateFormat.parse(e.getDate());//                Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
                java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                

                DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
                String time = timeformatter.format(new Date(timestamp.getTime()));

                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedDate);
                
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                monthYear = String.valueOf(month) + "," + String.valueOf(year);

                if(e.getType().equals("pdf")){
                    if(pdfReport.containsKey(monthYear)){
                        pdfReport.put(monthYear, pdfReport.get(monthYear) + 1);
                    }else{
                        pdfReport.put(monthYear, (float)1.0);
                    }
                }else if(e.getType().equals("email")){
                    if(emailReport.containsKey(monthYear)){
                        emailReport.put(monthYear, emailReport.get(monthYear) + 1);
                    }else{
                        emailReport.put(monthYear, (float)1.0);
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(ReportLineChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ReceiptService receiptService = new ReceiptService();
        
        receiptService.getAllReceipt().forEach(e -> {
            String monthYear = "";
            
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate;
                parsedDate = dateFormat.parse(e.getDate());//                Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
                java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                

                DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
                String time = timeformatter.format(new Date(timestamp.getTime()));

                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedDate);
                
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                monthYear = String.valueOf(month) + "," + String.valueOf(year);

                if(receiptReport.containsKey(monthYear)){
                    receiptReport.put(monthYear, receiptReport.get(monthYear) + 1);
                }else{
                    receiptReport.put(monthYear, (float)1.0);
                }
                

            } catch (ParseException ex) {
                Logger.getLogger(ReportLineChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        

        pdfReport.forEach((k, e)->{
            String reportYear = k.substring(k.indexOf(",") + 1, k.length());
            String reportMonth = k.substring(0, k.indexOf(","));
            
            series2.add(new Month(Integer.parseInt(reportMonth), Integer.parseInt(reportYear)), e);
        });
        
        emailReport.forEach((k, e)->{
            String reportYear = k.substring(k.indexOf(",") + 1, k.length());
            String reportMonth = k.substring(0, k.indexOf(","));
            
            series1.add(new Month(Integer.parseInt(reportMonth), Integer.parseInt(reportYear)), e);
        });
        
        receiptReport.forEach((k, e)->{
            String reportYear = k.substring(k.indexOf(",") + 1, k.length());
            String reportMonth = k.substring(0, k.indexOf(","));
            
            series3.add(new Month(Integer.parseInt(reportMonth), Integer.parseInt(reportYear)), e);
        });


       

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        
        dataset.setDomainIsPointsInTime(true);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
//        StackedXYAreaRenderer renderer = new StackedXYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);

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
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(new Color(165, 165, 165));

        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(new Color(165, 165, 165));
        
        
//        ValueAxis range = plot.getRangeAxis();
//        range.setVisible(false);

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
