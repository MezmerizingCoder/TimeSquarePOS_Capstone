/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pos_timesquare.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import pos_timesquare.model.Customer;
import static pos_timesquare.view.MainFrame.darkRB;

/**
 *
 * @author Acer
 */
public class CheckoutCustomerThumb extends javax.swing.JPanel {

    /**
     * Creates new form CheckoutCustomerThumb
     */
    
    Customer customerData = new Customer();

    public Customer getCustomerData() {
        return customerData;
    }

    public void setCustomerData(Customer customerData) {
        this.customerData = customerData;
        jLabel3.setText(customerData.getName());
        jLabel4.setText(customerData.getContactNum());
        
        if(customerData.getImage() != null && !customerData.getImage().equals("")){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    BufferedImage bufferedImage = null;
                    try {
                        String cwd = System.getProperty("user.dir");
                        bufferedImage = ImageIO.read(new File(cwd + customerData.getImage()));
//                        bufferedImage = ImageIO.read(getClass().getResource(customerData.getImage()));
                    } catch (IOException ex) {

                    }
                    Image scaledImage;
                    if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                        scaledImage = bufferedImage.getScaledInstance(45, -1, Image.SCALE_SMOOTH);
                    }else{
                        scaledImage = bufferedImage.getScaledInstance(-1, 45, Image.SCALE_SMOOTH);
                    }

                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    jLabel2.setIcon(imageIcon);
                    
//                    updateGraphics();
//                    revalidate();
//                    repaint();
                }
            });
            t.start();
        }
    }
    
    
    public CheckoutCustomerThumb() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new JLabel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(this.getBackground());
                }else{
                    g2.setColor(new Color(255, 255, 255));
                }
                for(int i = 45; i >= 0; i--){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }
                //            g2.setColor(Color.GRAY);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 45, 45);

                //            if(isHover){
                    //                g2.setColor(new Color(0,144,228));
                    //                g2.setStroke(new BasicStroke(1));
                    //                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
                    //            }
            }
        };
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(32767, 57));
        setMinimumSize(new java.awt.Dimension(100, 57));
        setOpaque(false);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(45, 45));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setText("Contact Number");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
