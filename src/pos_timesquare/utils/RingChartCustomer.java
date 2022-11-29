/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.utils;

import java.awt.Color;
import java.awt.Dimension;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;
import static pos_timesquare.view.MainFrame.jPanel117;
import static pos_timesquare.view.MainFrame.jPanel98;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.updateGraphics;
import pos_timesquare.view.SalesCateogryThumb;

/**
 *
 * @author Acer
 */
public class RingChartCustomer extends JPanel {
    DefaultPieDataset dataset = new DefaultPieDataset();
    JFreeChart chart = ChartFactory.createRingChart("", dataset, false, true, false);
    RingPlot pie = (RingPlot) chart.getPlot();
    HashMap<String, Float> data;
    float total = 0;
    
    public void setData(HashMap<String, Float> data){
        dataset.clear();
        this.data = data;
//        SalesCateogryThumb sct = new SalesCateogryThumb();
        jPanel98.removeAll();
        
        
        total = 0;
        data.forEach((k, e)->{
            total += (float) e;
        });
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        data.forEach((k, e)->{
            dataset.setValue(k, e);
            
//            new Color((int)(Math.random() * 0x1000000));
            SalesCateogryThumb sct = new SalesCateogryThumb();
            sct.removePrice();
            sct.setTypeName(k);
            sct.setPercentage( String.valueOf(df.format(((float)e / (float)total) * 100)) + "%");
            if(k.equals("New Customer")){
                pie.setSectionPaint(k, new Color(0, 144, 228));
            }else{
                pie.setSectionPaint(k, new Color(184, 234, 194));
            }
            sct.setColor((Color) pie.getSectionPaint(k));
            System.out.println("Ring color: " + pie.getSectionPaint(k));
            jPanel98.add(sct);
        });
        
        
//        setThumb();
//        pie.setSectionPaint("watch", new Color(120, 0, 120));
//        System.out.println(pie.getSectionPaint("watch"));
//        sct.setTypeName(e);
//        jPanel117.add(sct);
        
    }
    
    public void setThumb(){
        jPanel98.removeAll();
        data.forEach((k, e)->{
            SalesCateogryThumb sct = new SalesCateogryThumb();
            sct.setTypeName(k);
            sct.setColor((Color) pie.getSectionPaint(k));
            System.out.println("Ring color"+k+": " + pie.getSectionPaint(k));
            jPanel98.add(sct);
        });
    }
    
    public RingChartCustomer(){
        
//        dataset.setValue("Watch", new Integer(5));
//        dataset.setValue("Clock", new Integer(20));
        
        
        chart.setBackgroundPaint(new Color(0,0,0,0));
//        chart.getLegend().setFrame(BlockBorder.NONE);
//        chart.getLegend().setPosition(RectangleEdge.BOTTOM); 
//        chart.setBackgroundPaint(java.awt.Color.white);
//        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
//                CustomDonutPlot pie = (CustomDonutPlot) chart.getPlot();


       
        pie.setBackgroundPaint(new Color(0,0,0,0));
//        pie.setBackgroundAlpha(0);
        pie.setOutlineVisible(false);
        pie.setShadowPaint(null);
        pie.setSimpleLabels(true);
        pie.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        pie.setSimpleLabelOffset(new RectangleInsets(
            UnitType.RELATIVE, 0.09, 0.09, 0.09, 0.09));
        pie.setLabelBackgroundPaint(null);
        pie.setLabelOutlinePaint(null);
        pie.setLabelShadowPaint(null);
        pie.setSectionDepth(0.50);
        pie.setSectionOutlinesVisible(false);
        pie.setSeparatorsVisible(false);
        
        pie.setIgnoreZeroValues(false);
        
        
        
       
        ((RingPlot) chart.getPlot()).setLabelGenerator(null);

        
        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
//        setPreferredSize(new Dimension(300, 300));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(100, 100));
        chartPanel.setOpaque(false);
        add(chartPanel);
        
    }
}
