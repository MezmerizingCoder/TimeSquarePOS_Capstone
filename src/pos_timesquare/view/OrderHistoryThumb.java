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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import pos_timesquare.controller.ProductService;
import pos_timesquare.model.Product;
import pos_timesquare.model.TransactionHistory;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jLabel206;
import static pos_timesquare.view.MainFrame.jPanel15;
import static pos_timesquare.view.MainFrame.jPanel181;
import static pos_timesquare.view.MainFrame.orderHistorySelectedTable;
import static pos_timesquare.view.MainFrame.selectedOrderHistory;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class OrderHistoryThumb extends JPanel{
    
    JPanel jPanel176 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(new Color(38, 41, 48));
                g2.fillRoundRect(0, 2, this.getWidth(), this.getHeight()-2, 20, 20);
                g2.setColor(new Color(58, 61, 68));
                g2.drawRoundRect(0, 2, this.getWidth()-1, this.getHeight()-3, 20, 20);
            }else{
                g2.setColor(this.getBackground());
                g2.fillRoundRect(0, 2, this.getWidth(), this.getHeight()-3, 20, 20);
                g2.setColor(new Color(225, 225, 225));
                g2.drawRoundRect(0, 2, this.getWidth()-1, this.getHeight()-3, 20, 20);
            }

        }

    };
    
    JPanel jPanel177 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            
            if(darkRB.isSelected()){
                if(isSold){
                    g2.setColor(new Color(0, 148, 255, 20));
                }else{
                    g2.setColor(new Color(210, 210, 210, 20));
                }

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), this.getHeight(), this.getHeight());
    //            g2.setColor(new Color(58, 61, 68));
    //            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 20, 20);
            }else{
                if(isSold){
                    g2.setColor(new Color(0, 148, 255, 20));
                }else{
                    g2.setColor(new Color(0, 0, 0, 20));
                }
    //            g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-1, this.getHeight(), this.getHeight());
    //            g2.setColor(new Color(225, 225, 225));
    //            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 20, 20);
            }

        }

    };
    
    JLabel jLabel200 = new JLabel();
    JLabel jLabel201 = new JLabel();
    JLabel jLabel202 = new JLabel();
    JLabel jLabel203 = new JLabel();
    JLabel jLabel204 = new JLabel();
    
    JCheckBox jCheckBox5 = new JCheckBox();
    
    boolean isSold = true;
    
    TransactionHistory transactionHistory = new TransactionHistory();
    
    public void setTransactionHistory(TransactionHistory transactionHistory){
        this.transactionHistory = transactionHistory;
        
        ProductService ps = new ProductService();
        Product product = ps.getProductById(transactionHistory.getProductId());
        
        if(transactionHistory.getVariantId() == null){
             jLabel200.setText(product.getName());
        }else{
            jLabel200.setText(product.getName() + " (" + transactionHistory.getVariantId() + ")");
        }
        
        
        jLabel203.setText(String.valueOf(transactionHistory.getOrders()) + " item/s");
        jLabel202.setText("Total Price: " + String.valueOf(transactionHistory.getTotalPrice()));
        jLabel201.setText(transactionHistory.getTransactionDate().toString());
        jLabel204.setText(transactionHistory.getStatus());
        if(transactionHistory.getStatus().equals("Sold")){
            isSold=true;
            repaint();
        }else if(transactionHistory.getStatus().equals("Refund")){
            isSold=false;
            repaint();
        }
        
        selectedOrderHistory.forEach((k,e)->{
            if(k == transactionHistory.getId()){
                jCheckBox5.setSelected(true);
            }
        });
        
        
    }
    
    public TransactionHistory getTransactionHistory(){
        return this.transactionHistory;
    }
    
    public OrderHistoryThumb(){
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel176.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel176.setPreferredSize(new java.awt.Dimension(423, 60));

        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(jCheckBox5.isSelected()){
                    selectedOrderHistory.put(getTransactionHistory().getId(), getTransactionHistory());
                    if(selectedOrderHistory.size() > 0){
                        jPanel181.add(jPanel15);
                    }
                    selectedOrderHistory.forEach((k,e)->{
                        System.out.println("Product: "+e.getId());
                        
                        if(k == getTransactionHistory().getId()){
                            ProductService ps = new ProductService();
                            Product product = ps.getProductById(transactionHistory.getProductId());

                            DefaultTableModel model = (DefaultTableModel) orderHistorySelectedTable.getModel();
                            List<String> list = new ArrayList<String>();

                            list.add(String.valueOf(getTransactionHistory().getId()));
                            list.add(product.getName());
                            list.add(String.valueOf(getTransactionHistory().getOrders()));
                            list.add(getTransactionHistory().getTransactionDate().toString());
                            list.add(String.valueOf(getTransactionHistory().getTotalPrice()));
    //                        list.add(getTransactionHistory());

                            model.addRow(list.toArray());
                            orderHistorySelectedTable.setModel(model);
                        }
                    });
                    jLabel206.setText(selectedOrderHistory.size() + " Selected");
                }else{
                    selectedOrderHistory.remove(getTransactionHistory().getId());
                    
                    if(selectedOrderHistory.size() == 0){
                        jPanel181.removeAll();
                    }
                    
                    System.out.println("Deleted");
                    DefaultTableModel model = (DefaultTableModel) orderHistorySelectedTable.getModel();
                    
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (((String)model.getValueAt(i, 0)).equals(String.valueOf(getTransactionHistory().getId()))) {
                            model.removeRow(i);
                            orderHistorySelectedTable.setModel(model);
                        }//end of if block
                    }
                    
                    
                }
                updateGraphics();
            }
        });

        jLabel200.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel200.setText("Product Name");

        jLabel201.setText("10-22-22");

        jLabel202.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel202.setText("Total Price: 20000");

        jPanel177.setOpaque(false);
        jPanel177.setPreferredSize(new java.awt.Dimension(107, 30));

        jLabel204.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel204.setText("Refund");
        jLabel204.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel177Layout = new javax.swing.GroupLayout(jPanel177);
        jPanel177.setLayout(jPanel177Layout);
        jPanel177Layout.setHorizontalGroup(
            jPanel177Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel204, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
        );
        jPanel177Layout.setVerticalGroup(
            jPanel177Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel204, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel203.setText("2 item/s");

        javax.swing.GroupLayout jPanel176Layout = new javax.swing.GroupLayout(jPanel176);
        jPanel176.setLayout(jPanel176Layout);
        jPanel176Layout.setHorizontalGroup(
            jPanel176Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel176Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel176Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel200, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jLabel201, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel177, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel203, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel202, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel176Layout.setVerticalGroup(
            jPanel176Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel203, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel202, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel176Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel176Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel177, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel176Layout.createSequentialGroup()
                        .addComponent(jLabel200, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel201, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        this.add(jPanel176);
    }
}
