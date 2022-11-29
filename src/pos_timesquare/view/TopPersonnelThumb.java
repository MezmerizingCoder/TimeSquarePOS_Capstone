/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static pos_timesquare.view.MainFrame.darkRB;

/**
 *
 * @author Acer
 */
public class TopPersonnelThumb extends JPanel{
    
    JPanel jPanel217 = new JPanel();
    JLabel jLabel251 = new JLabel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//            if(darkRB.isSelected()){
//                g2.setColor(new Color(38, 41, 48));
//            }else{
//                g2.setColor(new Color(255, 255, 255));
//            }
            g2.setColor(new Color(255, 255, 255));
            for(int i = 24; i >= 0; i--){
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
            }
//            g2.setColor(Color.GRAY);
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(58, 61, 68));
//            }else{
//                g2.setColor(new Color(225, 225, 225));
//            }
            g2.setColor(new Color(225, 225, 225));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            
//            if(isHover){
//                g2.setColor(new Color(0,144,228));
//                g2.setStroke(new BasicStroke(1));
//                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//            }
        }
    };
    JLabel jLabel252 = new JLabel();
    JLabel jLabel253 = new JLabel();
    
    String image;
    String name;
    String performance;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        if(image != null && !image.equals("")){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(getClass().getResource(image));
                    } catch (IOException ex) {

                    }
                    Image scaledImage;
                    if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                        scaledImage = bufferedImage.getScaledInstance(45, -1, Image.SCALE_SMOOTH);
                    }else{
                        scaledImage = bufferedImage.getScaledInstance(-1, 45, Image.SCALE_SMOOTH);
                    }

                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    jLabel251.setIcon(imageIcon);
                    
//                    updateGraphics();
//                    revalidate();
//                    repaint();
                }
            });
            t.start();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        jLabel252.setText(name);
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
        jLabel253.setText(performance + "%");
    }
    
    
    

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(darkRB.isSelected()){
//            g2.setColor(new Color(38, 41, 48));
//        }else{
//            g2.setColor(new Color(245, 245, 245));
//        }
        g2.setColor(new Color(255, 255, 255));
        g2.fillRoundRect(0, 10, this.getWidth()-1, this.getHeight()-16, 55, 55);
    }
    
    public TopPersonnelThumb(){
        this.setOpaque(false);
        this.setPreferredSize(new java.awt.Dimension(150, 161));
        this.setMaximumSize(new java.awt.Dimension(150, 161));
        this.setMinimumSize(new java.awt.Dimension(150, 161));

        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
//        jLabel251.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel251.setPreferredSize(new java.awt.Dimension(46, 46));

        jLabel252.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel252.setForeground(new java.awt.Color(51, 51, 51));
        jLabel252.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel252.setText("Employee Name");

        jLabel253.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel253.setForeground(new java.awt.Color(51, 51, 51));
        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel253.setText("40%");

        javax.swing.GroupLayout jPanel217Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel217Layout);
        jPanel217Layout.setHorizontalGroup(
            jPanel217Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel252, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
            .addComponent(jLabel253, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel217Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel251, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel217Layout.setVerticalGroup(
            jPanel217Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel217Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel251, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel252)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel253)
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }
}
