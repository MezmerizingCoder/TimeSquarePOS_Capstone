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
public class CustomerLineChart extends JPanel {
    
    float newCustomerTotal = 0, registeredCustomerTotal = 0;
    
    public CustomerLineChart() {   // the constructor will contain the panel of a certain size and the close operations 
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
        
        TimeSeries series1 = new TimeSeries("New Customer");
        TimeSeries series2 = new TimeSeries("Registered Customer");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        Date date = new Date();
        
        
        
        CustomerService cs = new CustomerService();
        
        
        
        if(jComboBox8.getSelectedIndex() == 0){
            
            HashMap<Hour, Float> customerData = new HashMap<>();
            HashMap<Hour, Float> registeredCustomerData = new HashMap<>();
            
            cs.getAllCustomer().forEach(e -> {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
                    
                    Date parsedDate = dateFormat.parse(e.getMembershipDate());
                    
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(parsedDate);
                    
                    
                    
                    
                    String time = timeformatter.format(parsedDate.getTime());
                    
                    System.out.println("Time: " + time);
                    
                    if(buttonGroup.getSelection() != null){
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int year = cal.get(Calendar.YEAR);
                        System.out.println("Year: " + year + " Mounth: " + month + "Day: " + buttonGroup.getSelection().getActionCommand());
                    
                    
                    
                        if(year == jYearChooser2.getYear() && month == jMonthChooser1.getMonth() && day == Integer.valueOf(buttonGroup.getSelection().getActionCommand()) ){
                            if(customerData.containsKey(new Hour(parsedDate))){
                                customerData.put(new Hour(parsedDate), customerData.get(new Hour(parsedDate)) + 1);
                            }else{
                                customerData.put(new Hour(parsedDate), (float)1.0);
                            }
                        }
                    }
                    
                    ReceiptService rs = new ReceiptService();
                    rs.getReceiptByCustomerId(e.getId()).forEach(e2 -> {
                        try {
                            Date parsedDate2 = dateFormat.parse(e2.getDate());
                            cal.setTime(parsedDate2);
                            
//                            if(!dateFormat2.parse(e2.getDate()).equals( dateFormat2.parse(e.getMembershipDate()))){
                                if(buttonGroup.getSelection() != null){
                                    int month = cal.get(Calendar.MONTH);
                                    int day = cal.get(Calendar.DAY_OF_MONTH);
                                    int year = cal.get(Calendar.YEAR);
                                    if(year == jYearChooser2.getYear() && month == jMonthChooser1.getMonth() && day == Integer.valueOf(buttonGroup.getSelection().getActionCommand()) ){
                                        
                                        if(registeredCustomerData.containsKey(new Hour(parsedDate2))){
                                            registeredCustomerData.put(new Hour(parsedDate2), registeredCustomerData.get(new Hour(parsedDate2)) + 1);
                                        }else{
                                            registeredCustomerData.put(new Hour(parsedDate2), (float)1.0);
                                        }
                                    }
                                }
//                            }

//                            System.out.println("Date2: " + dateFormat2.parse(e2.getDate()) + " " + dateFormat2.parse(e.getMembershipDate()));
                            System.out.println(!dateFormat2.parse(e2.getDate()).equals(dateFormat2.parse(e.getMembershipDate())));

                        } catch (ParseException ex) {
                            Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

//                    customerData.put(new Second(parsedDate), (float)1.0);

                    
                } catch (ParseException ex) {
                    Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            customerData.forEach((k, e)->{
                series1.add(k, e);
                newCustomerTotal += e;
            });
            
            registeredCustomerData.forEach((k, e)->{
                series2.add(k, e);
                registeredCustomerTotal += e;
            });
            
        }else if(jComboBox8.getSelectedIndex() == 1){
            
            HashMap<Integer, Float> customerData = new HashMap<>();
            HashMap<Integer, Float> registeredCustomerData = new HashMap<>();
        
            cs.getAllCustomer().forEach(e -> {

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate;
                    parsedDate = dateFormat.parse(e.getMembershipDate());//                Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
//                    DateFormat dateformatter = new SimpleDateFormat("MM-dd-yyyy");
//                    String date2 = dateformatter.format(new Date(timestamp.getTime()));

                    DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
                    String time = timeformatter.format(new Date(timestamp.getTime()));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(parsedDate);
            //            int month = cal.get(Calendar.MONTH);
            //            int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    System.out.println(timestamp);

                    System.out.println(parsedDate.getMonth());

                    if(year == jYearChooser2.getYear()){
                        if(customerData.containsKey(parsedDate.getMonth())){
                            customerData.put(parsedDate.getMonth(), customerData.get(parsedDate.getMonth()) + 1);
                        }else{
                            customerData.put(parsedDate.getMonth(), (float)1.0);
                        }
                    }

                    ReceiptService rs = new ReceiptService();
                    rs.getReceiptByCustomerId(e.getId()).forEach(e2 -> {
                        try {
                            Date parsedDate2 = dateFormat.parse(e2.getDate());
                            cal.setTime(parsedDate2);
//                            if(!dateFormat2.parse(e2.getDate()).equals( dateFormat2.parse(e.getMembershipDate()))){
                                if(year == jYearChooser2.getYear()){
                                    if(registeredCustomerData.containsKey(parsedDate2.getMonth())){
                                        registeredCustomerData.put(parsedDate2.getMonth(), registeredCustomerData.get(parsedDate2.getMonth()) + 1);
                                    }else{
                                        registeredCustomerData.put(parsedDate2.getMonth(), (float)1.0);
                                    }
                                }
//                            }

//                            System.out.println("Date2: " + dateFormat2.parse(e2.getDate()) + " " + dateFormat2.parse(e.getMembershipDate()));
                            System.out.println(!dateFormat2.parse(e2.getDate()).equals(dateFormat2.parse(e.getMembershipDate())));

                        } catch (ParseException ex) {
                            Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    System.out.println(year + " " + jYearChooser2.getYear());

                } catch (ParseException ex) {
                    Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            customerData.forEach((k, e)->{
                series1.add(new Month(k+1, jYearChooser2.getYear()), e);
                newCustomerTotal += e;
            });

            registeredCustomerData.forEach((k, e)->{
                series2.add(new Month(k+1, jYearChooser2.getYear()), e);
                registeredCustomerTotal += e;
                System.out.println("Registered Sale: " + k + ", " + e);
            });
            
        }else if(jComboBox8.getSelectedIndex() == 2){
            
            HashMap<Date, Float> customerData = new HashMap<>();
            HashMap<Date, Float> registeredCustomerData = new HashMap<>();
        
            cs.getAllCustomer().forEach(e -> {

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate;
                    parsedDate = dateFormat2.parse(e.getMembershipDate());//                Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
//                    DateFormat dateformatter = new SimpleDateFormat("MM-dd-yyyy");
//                    String date2 = dateformatter.format(new Date(timestamp.getTime()));

                    DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
                    String time = timeformatter.format(new Date(timestamp.getTime()));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(parsedDate);
            //            int month = cal.get(Calendar.MONTH);
            //            int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    System.out.println(timestamp);

                    System.out.println(parsedDate.getMonth());

                        if(customerData.containsKey(parsedDate)){
                            customerData.put(parsedDate, customerData.get(parsedDate) + 1);
                        }else{
                            customerData.put(parsedDate, (float)1.0);
                        }

                    ReceiptService rs = new ReceiptService();
                    rs.getReceiptByCustomerId(e.getId()).forEach(e2 -> {
                        try {
                            Date parsedDate2 = dateFormat2.parse(e2.getDate());
                            cal.setTime(parsedDate2);
//                            if(!dateFormat2.parse(e2.getDate()).equals( dateFormat2.parse(e.getMembershipDate()))){
                                
                                    if(registeredCustomerData.containsKey(parsedDate2)){
                                        registeredCustomerData.put(parsedDate2, registeredCustomerData.get(parsedDate2) + 1);
                                    }else{
                                        registeredCustomerData.put(parsedDate2, (float)1.0);
                                    }
                                
//                            }

//                            System.out.println("Date2: " + dateFormat2.parse(e2.getDate()) + " " + dateFormat2.parse(e.getMembershipDate()));
                            System.out.println(!dateFormat2.parse(e2.getDate()).equals(dateFormat2.parse(e.getMembershipDate())));

                        } catch (ParseException ex) {
                            Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    System.out.println(year + " " + jYearChooser2.getYear());

                } catch (ParseException ex) {
                    Logger.getLogger(CustomerLineChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            customerData.forEach((k, e)->{
                series1.add(new Minute(k), e);
                newCustomerTotal += e;
            });

            registeredCustomerData.forEach((k, e)->{
                series2.add(new Minute(k), e);
                registeredCustomerTotal += e;
                System.out.println("Registered Sale: " + k + ", " + e);
            });
            
        }
        
        
//        jPanel64.removeAll();
//        RingChartCustomer ringChartCustomer= new RingChartCustomer();
//        HashMap<String, Float> customers = new HashMap<>();
//        
//        customers.put("New Customer", (float)10.0);
//        customers.put("Registered Customer", (float)3.0);
        
//        ringChartCustomer.setData(customers);
//        jPanel64.add(ringChartCustomer);


        
//        series1.add(new Month(10, 2022), 3.0);
//        series1.add(new Month(11, 2022), 2.0);
        
//        series2.add(new Month(10, 2022), 5.0);
//        series2.add(new Month(11, 2022), 1.0);

        totalNewCustomer = newCustomerTotal;
        totalRegisteredCustomer = registeredCustomerTotal;
       

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
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
//        renderer.setSeriesPaint(2, new Color(255, 142, 24));

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
