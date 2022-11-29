/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.utils;

/**
 *
 * @author Acer
 */
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.model.TransactionHistory;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jYearChooser1;

public class HistogramPanel extends JPanel
{
    private int histogramHeight = 200;
    private int barWidth = 70;
    private int barGap = 6;

    private JPanel barPanel;
    private JPanel labelPanel;

    private List<Bar> bars = new ArrayList<Bar>();

    public HistogramPanel()
    {
        setOpaque(false);
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.GRAY);
        Border inner = new EmptyBorder(10, 10, 0, 10);
//        Border compound = new CompoundBorder(outer, inner);
//        barPanel.setBorder( compound );

        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }
    
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g.create();
//
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(darkRB.isSelected()){
//            g2.setColor(new Color(63, 63, 63));
//
//        }else{
//            g2.setColor(new Color(203, 203, 203));
//        }
//        g2.setStroke(new BasicStroke(1));
//        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
//    }

    public void addHistogramColumn(String label, int value, Color color)
    {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }

    public void layoutHistogram()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 1;

        for (Bar bar: bars)
            maxValue = Math.max(maxValue, bar.getValue());

        for (Bar bar: bars)
        {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
            label.setIcon( icon );
            barPanel.add( label );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
        }
    }

    private class Bar
    {
        private String label;
        private int value;
        private Color color;

        public Bar(String label, int value, Color color)
        {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel()
        {
            return label;
        }

        public int getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

    private class ColorIcon implements Icon
    {
        private int shadow = 3;

        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height)
        {
            this.color = color;
            this.width = width;
//            this.height = height;
            this.height = height + 2;

        }

        public int getIconWidth()
        {
            return width;
        }

        public int getIconHeight()
        {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(new Color(101, 55, 255));
            }else{
                g2.setColor(new Color(0, 34, 171));

            }
            g2.fillRoundRect(x, y, width - shadow, height, 15, 15);
            g2.fillRoundRect(x, y+5, width - shadow, height-5, 0, 0);
//            g.setColor(Color.GRAY);
//            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }

    public static JPanel createAndShowGUI(int type){
        TransactionHistoryService ths = new TransactionHistoryService();
        List<TransactionHistory> transactions = ths.getAllTransactionHistoryDetails();
        
        
        
        
        HistogramPanel panel = new HistogramPanel();
        panel.setOpaque(false);
//        panel.addHistogramColumn("January", 0, new Color(0, 34, 171));
//        panel.addHistogramColumn("February", 0, new Color(0, 34, 171));
//        panel.addHistogramColumn("March", 510, new Color(0, 34, 171));
//        panel.addHistogramColumn("April", 570, new Color(0, 34, 171));
//        panel.addHistogramColumn("June", 180, new Color(0, 34, 171));
//        panel.addHistogramColumn("July", 504, new Color(0, 34, 171));
//        panel.addHistogramColumn("August", 504, new Color(0, 34, 171));
//        panel.addHistogramColumn("September", 504, new Color(0, 34, 171));
//        panel.addHistogramColumn("October", 504, new Color(0, 34, 171));
//        panel.addHistogramColumn("November", 504, new Color(0, 34, 171));
//        panel.addHistogramColumn("December", 504, new Color(0, 34, 171));


        if(type == 1){
            HashMap<String, Integer> sales = new HashMap<>();

            transactions.forEach(e -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(e.getTransactionDate());
        //            int month = cal.get(Calendar.MONTH);
        //            int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                if(jYearChooser1.getYear() == year){
                    System.out.println("Month: " + e.getTransactionDate().getMonth());
                    String month = new DateFormatSymbols().getMonths()[e.getTransactionDate().getMonth()];
                    if(sales.isEmpty()){
                        sales.put(month, e.getOrders());
                    }else{
                        if(sales.get(month) != null){
                            sales.put(month, sales.get(month) + e.getOrders());
                        }else{
                            sales.put(month, e.getOrders());
                        }
                    }
                }
                System.out.println("Current year: " + jYearChooser1.getYear());
                System.out.println("Transaction year: " + year);
            });

            for(int i = 0; i < 12; i++){
                String month = new DateFormatSymbols().getMonths()[i];
                boolean exist = false;
                for (String j : sales.keySet()) {
                    if(j.equals(month)){
                        exist = true;
                    }
                }
                if(exist){
                    panel.addHistogramColumn(month, sales.get(month), new Color(0, 34, 171));
                }else{
                    panel.addHistogramColumn(month, 0, new Color(0, 34, 171));
                }
            }
        }else if(type == 0){
            HashMap<String, Integer> sales = new HashMap<>();

            transactions.forEach(e -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(e.getTransactionDate());
        //            int month = cal.get(Calendar.MONTH);
        //            int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                if(jYearChooser1.getYear() == year){
                    System.out.println("Month: " + e.getTransactionDate().getMonth());
                    String month = new DateFormatSymbols().getMonths()[e.getTransactionDate().getMonth()];
                    if(sales.isEmpty()){
                        sales.put(month, Math.round(e.getTotalPrice()));
                    }else{
                        if(sales.get(month) != null){
                            sales.put(month, sales.get(month) + Math.round(e.getTotalPrice()));
                        }else{
                            sales.put(month, Math.round(e.getTotalPrice()));
                        }
                    }
                }
                System.out.println("Current year: " + jYearChooser1.getYear());
                System.out.println("Transaction year: " + year);
            });

            for(int i = 0; i < 12; i++){
                String month = new DateFormatSymbols().getMonths()[i];
                boolean exist = false;
                for (String j : sales.keySet()) {
                    if(j.equals(month)){
                        exist = true;
                    }
                }
                if(exist){
                    panel.addHistogramColumn(month, sales.get(month), new Color(0, 34, 171));
                }else{
                    panel.addHistogramColumn(month, 0, new Color(0, 34, 171));
                }
            }
        }
//        for (String i : sales.keySet()) {
//            panel.addHistogramColumn(String.valueOf(i), sales.get(i), new Color(0, 34, 171));
////            System.out.println(new DateFormatSymbols().getMonths()[i]);
//            
//        }
        
        panel.layoutHistogram();
        
        return panel;
    }

//    public static void main(String[] args)
//    {
//        EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                createAndShowGUI();
//            }
//        });
//    }
}