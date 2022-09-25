/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pos_timesquare.model.Product;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.tabPanel;
import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
import static pos_timesquare.view.MainFrame.viewProductName;
import static pos_timesquare.view.MainFrame.viewProductPanel;

/**
 *
 * @author Acer
 */
public class ProductThumb extends JPanel {
    
    private JPanel jPanel40 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
        }

    };
    
    private JPanel jPanel44 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if(darkRB.isSelected()){
                g2.setColor(new Color(33, 37, 43));
            }else{
                g2.setColor(new Color(235,235,235));
            }
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
        }

    };
    private JLabel jLabel47 = new JLabel();
    private JLabel jLabel48 = new JLabel();
    private JLabel jLabel49 = new JLabel();
    private JLabel jLabel50 = new JLabel();
    private JLabel jLabel51 = new JLabel();
    private JLabel jLabel55 = new JLabel();
    

    private String productName;
    private int productStocks;
    
    private Product productDetails;

    public Product getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Product productDetails) {
        this.productDetails = productDetails;
        jLabel47.setText(productDetails.getName());
        jLabel50.setText(String.valueOf(productDetails.getStocks()));
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        jLabel47.setText(productName);
    }

    public int getProductStocks() {
        return productStocks;
    }

    public void setProductStocks(int productStocks) {
        this.productStocks = productStocks;
        jLabel50.setText(String.valueOf(productStocks));
    }
    
    

    
    public ProductThumb(){
        jPanel40.setPreferredSize(new java.awt.Dimension(220, 95));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setText("Rolex xxx");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel49.setText("In Stock:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel50.setText("20");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel49)
                .addGap(0, 0, 0)
                .addComponent(jLabel50)
                .addGap(7, 7, 7))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/favorite-icon-gray.png"))); // NOI18N

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pencil-icon-gray.png"))); // NOI18N

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(138, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addGap(5, 5, 5)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                
                tabPanel.add(ticketMainPanelHeader);
                
                blurBGPanel.setVisible(true);
                popupPanel.setVisible(true);
                popupContentPanel.removeAll();
                popupContentPanel.add(viewProductPanel);
                viewProductName.setText(productDetails.getName());
                
                JFrame frame = (JFrame)getTopLevelAncestor();

                popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
                
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        while(popupPanel.getX() > 0){
                            popupPanel.setBounds(popupPanel.getX() - 15, 0, frame.getWidth(), frame.getHeight());
                            try {
                                Thread.sleep((long) 1);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(popupPanel.getX() < 0){
                            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                        }
                    }
                });
                t.start();
                if(popupPanel.getX() <= 0) t.stop();
                
                if(darkRB.isSelected()){
                    try {
                        System.out.println("Dark");
//                        FlatAtomOneDarkContrastIJTheme.install();
                        UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
                        FlatAtomOneDarkContrastIJTheme.updateUI();
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else {
                    try {
                        //                        FlatMaterialLighterIJTheme.install();
                        UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                        FlatMaterialLighterIJTheme.updateUI();
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                refresh();
                repaint();
                revalidate();
            }
        });
        
        setLayout(new BorderLayout());
        add(jPanel40);
        
        
    }
    
}
