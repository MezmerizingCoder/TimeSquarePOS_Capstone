/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import static pos_timesquare.view.MainFrame.archiveMainMenu;
import static pos_timesquare.view.MainFrame.customerMainMenu;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.dashboardMainMenu;
import static pos_timesquare.view.MainFrame.personnelMainMenu;
import static pos_timesquare.view.MainFrame.productMainMenu;
import static pos_timesquare.view.MainFrame.salesMainMenu;
import static pos_timesquare.view.MainFrame.selectedMenu;
import static pos_timesquare.view.MainFrame.ticketMainMenu;

/**
 *
 * @author Acer
 */
public class MainMenuThumb extends JPanel{

//    boolean hover = false;
    public boolean selected = false;
    JPanel thisPanel = this;
    
    MainMenuThumb(){
        
        selectedMenu = dashboardMainMenu;
        
        putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
        setBackground(new Color(238,238,238));
        setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
//                hover = true;
//                repaint();
//                revalidate();
                if(thisPanel != selectedMenu){
                    if(darkRB.isSelected()){
                        setBackground( new Color(48, 49, 65) );
                    }else{
                        setBackground( new Color(233, 233, 233) );
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                hover = false;
//                repaint();
//                revalidate();
                if(thisPanel != selectedMenu){
                    
                    if(darkRB.isSelected()){
                        setBackground(new Color(33, 37, 43));
                        setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
                    }else{
                        setBackground(new Color(238,238,238));
                        setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
                    }
                    
//                    System.out.println("is Not Selected");
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                
                resetStyle(ticketMainMenu);
                resetStyle(salesMainMenu);
                resetStyle(productMainMenu);
                resetStyle(dashboardMainMenu);
                resetStyle(personnelMainMenu);
                resetStyle(customerMainMenu);
                resetStyle(archiveMainMenu);
                selectedMenu = thisPanel;
                
//                selected = true;
                setBackground(new Color(101,55,255));
//                Component[] child = getComponents();
//                child[0].setForeground(Color.WHITE);
//                System.out.println(child[0]);
            }
            
        });
        
    }
    
    public void resetStyle(JPanel comp){
        if(darkRB.isSelected()){
            comp.setBackground(new Color(33, 37, 43));
            comp.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
        }else{
            comp.setBackground(new Color(238,238,238));
            comp.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
//            Component[] child = comp.getComponents();
//            child[0].setBackground(Color.gray);
//            System.out.println(child[0]);
        }
//        revalidate();
//        repaint();
//        selected = false;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
//        if(hover){
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(48, 48, 48));
//            }else{
//                g2.setColor(new Color(233, 233, 233));
//            }
//            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
//            
////            g2.setColor(new Color(101,55,255));
////            g2.setStroke(new BasicStroke(1));
////            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(63, 63, 63));
//
//            }else{
//                g2.setColor(new Color(203, 203, 203));
//            }
//            g2.setStroke(new BasicStroke(1));
//            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//            
//        }else{
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(63, 63, 63));
//
//            }else{
//                g2.setColor(new Color(203, 203, 203));
//            }
//            g2.setStroke(new BasicStroke(1));
//            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//        }
        if(thisPanel != selectedMenu){
            if(darkRB.isSelected()){
                g2.setColor(new Color(63, 63, 63));

            }else{
                g2.setColor(new Color(203, 203, 203));
            }
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
        }
    }
    
    
}
