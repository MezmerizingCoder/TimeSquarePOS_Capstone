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
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import static pos_timesquare.view.MainFrame.darkRB;

public class HistogramPanel extends JPanel
{
    private int histogramHeight = 200;
    private int barWidth = 50;
    private int barGap = 10;

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

        int maxValue = 0;

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
            this.height = height;
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

    public static JPanel createAndShowGUI()
    {
        HistogramPanel panel = new HistogramPanel();
        panel.setOpaque(false);
        panel.addHistogramColumn("January", 350, new Color(0, 34, 171));
        panel.addHistogramColumn("February", 690, new Color(0, 34, 171));
        panel.addHistogramColumn("March", 510, new Color(0, 34, 171));
        panel.addHistogramColumn("April", 570, new Color(0, 34, 171));
        panel.addHistogramColumn("June", 180, new Color(0, 34, 171));
        panel.addHistogramColumn("July", 504, new Color(0, 34, 171));
        panel.addHistogramColumn("August", 504, new Color(0, 34, 171));
        panel.addHistogramColumn("September", 504, new Color(0, 34, 171));
        panel.addHistogramColumn("October", 504, new Color(0, 34, 171));
        panel.addHistogramColumn("November", 504, new Color(0, 34, 171));
        panel.addHistogramColumn("December", 504, new Color(0, 34, 171));


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