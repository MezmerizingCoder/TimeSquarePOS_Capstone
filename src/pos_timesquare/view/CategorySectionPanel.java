/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Acer
 */
public class CategorySectionPanel extends JPanel{
    
    JPanel jPanel31 = new JPanel();
    JPanel jPanel17 = new JPanel();
    JPanel jPanel38 = new JPanel();
    JPanel jPanel39 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
        }

    };
    JPanel jPanel18 = new JPanel();
    JPanel jPanel52 = new JPanel();
    JLabel jLabel30 = new JLabel();
    JLabel jLabel23 = new JLabel();
    JLabel jLabel31 = new JLabel();
    JLabel jPanel41 = new JLabel();
    JLabel jLabel51 = new JLabel();
    JLabel jLabel55 = new JLabel();
    
    public void setCategoryType(String title){
        jLabel30.setText(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase());
    }
    
    public void addThumb(Component comp){
        jPanel38.add(comp);
    }
    
    public CategorySectionPanel(){
//        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.Y_AXIS));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel30.setText("Watch");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel31.add(jPanel17);

        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.Y_AXIS));

//        jPanel39.setMaximumSize(new java.awt.Dimension(32767, 50));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Something");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel31.setText("22 items");

//        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
//        jPanel39.setLayout(jPanel39Layout);
//        jPanel39Layout.setHorizontalGroup(
//            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel39Layout.createSequentialGroup()
//                .addGap(12, 12, 12)
//                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addGap(12, 12, 12))
//        );
//        jPanel39Layout.setVerticalGroup(
//            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel39Layout.createSequentialGroup()
//                .addGap(8, 8, 8)
//                .addComponent(jLabel23)
//                .addGap(0, 0, 0)
//                .addComponent(jLabel31)
//                .addGap(8, 8, 8))
//        );

//        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
//        jPanel18.setLayout(jPanel18Layout);
//        jPanel18Layout.setHorizontalGroup(
//            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        jPanel18Layout.setVerticalGroup(
//            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel18Layout.createSequentialGroup()
//                .addGap(3, 3, 3)
//                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(3, 3, 3))
//        );
//
//        jPanel38.add(jPanel18);

//        jPanel38.add(new JLabel("afasf"));
        jLabel30.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jPanel31.add(jPanel38);
        
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
//        setLayout(new BorderLayout());
        add(jPanel31);
    }
}
