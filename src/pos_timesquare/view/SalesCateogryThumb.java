/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.model.TransactionHistory;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class SalesCateogryThumb extends JPanel{
    
    JPanel jPanel118 = new JPanel();
    JPanel jPanel119 = new JPanel(){
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
        }

    };
    
    JLabel jLabel114 = new JLabel();
    JLabel jLabel116 = new JLabel();
    JLabel jLabel115 = new JLabel();
    JLabel jLabel117 = new JLabel();
    
    String typeName;
    float totalRevenue;
    Color color = new Color(85, 85, 255);
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(darkRB.isSelected()){
            g2.setColor(new Color(40, 44, 52));
        }else{
            g2.setColor(new Color(255, 255, 255));
        }
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-5, 15, 15);
    }
    
    public void setColor(Color color){
        this.color = color;
        updateGraphics();
    }
    
    public void setTypeName(String name){
        typeName = name;
        jLabel114.setText(typeName);
        
        totalRevenue = 0;
        
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();
        
//        ProductService ps = new ProductService();
        
//        transactionList.forEach(e -> {
//            ps.getProductById(WIDTH)
//        });
        
        CategoryService categoryService = new CategoryService();
        categoryService.getAllCategory().forEach(e -> {
            if(e.getType().equals(typeName)){
                transactionList.forEach(e2 -> {
                    if(e.getProduct_id() == e2.getProductId()){
                        totalRevenue += e2.getTotalPrice();
                        System.out.println("category type match!");
                    }
                });
            }
        });
        jLabel117.setText("₱"+String.valueOf(totalRevenue));
//        System.out.println(totalRevenue);
        
    }
    
    public SalesCateogryThumb(){
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 10, 1));
        this.setMaximumSize(new java.awt.Dimension(32767, 55));
        this.setOpaque(false);
        this.setPreferredSize(new java.awt.Dimension(278, 50));

        jLabel114.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel114.setText("Watch");

        jPanel119.setPreferredSize(new java.awt.Dimension(30, 35));

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pie-chart-icon-white.png"))); // NOI18N

        javax.swing.GroupLayout jPanel119Layout = new javax.swing.GroupLayout(jPanel119);
        jPanel119.setLayout(jPanel119Layout);
        jPanel119Layout.setHorizontalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel119Layout.setVerticalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel115.setText("20%");

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel117.setText("20000");

        javax.swing.GroupLayout jPanel118Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel118Layout);
        jPanel118Layout.setHorizontalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel118Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel119, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel118Layout.setVerticalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel118Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel119, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(jPanel118Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel118Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel118Layout.createSequentialGroup()
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }
}
