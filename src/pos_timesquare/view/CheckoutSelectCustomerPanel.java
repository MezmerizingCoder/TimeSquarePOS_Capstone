/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pos_timesquare.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import pos_timesquare.controller.CustomerService;
import pos_timesquare.model.Customer;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.checkoutCustomer;
import static pos_timesquare.view.MainFrame.serviceCustomer;
import static pos_timesquare.view.MainFrame.checkoutCustomerSelectedPanel;
import static pos_timesquare.view.MainFrame.glassPanel;
import static pos_timesquare.view.MainFrame.jTextField1;
import static pos_timesquare.view.MainFrame.selectCustomerPopupPanel;

/**
 *
 * @author Acer
 */
public class CheckoutSelectCustomerPanel extends javax.swing.JPanel {

    /**
     * Creates new form CheckoutSelectCustomerPanel
     */
//    boolean isExited = true;
//    boolean isHover = false;
    boolean forProductCheckout = true;
    boolean forEditService = false;
    JTextField comp;
    JLabel comp2;
    
    ServiceThumb2 compCustomer;
    
    
    public void setServiceTicketComp(ServiceThumb2 customer){
        compCustomer = customer;
    }
    
    public void setForEditService(boolean value){
        forEditService = value;
    }
    public void isProductCheckout(boolean value){
        forProductCheckout = value;
    }
    
    public void setCallingComponent(JTextField comp, JLabel comp2){
        this.comp = comp;
        this.comp2 = comp2;
    }
    
    public CheckoutSelectCustomerPanel() {
        initComponents();
        
        CustomerService cs = new CustomerService();
        jPanel1.removeAll();
        cs.getAllCustomer().forEach(e -> {
            if(e.getStatus().equals("Active")){
                if(!e.getName().equals("") && !e.getContactNum().equals("")){
                    CheckoutCustomerThumb checkoutCustomerThumb = new CheckoutCustomerThumb();
                    checkoutCustomerThumb.setCustomerData(e);
                    checkoutCustomerThumb.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseClicked(MouseEvent evt) {
                            if(forProductCheckout){
                                checkoutCustomer = e;
                                checkoutCustomerSelectedPanel.removeAll();
                                checkoutCustomerSelectedPanel.add(checkoutCustomerThumb);
                                removeThumb();
                            }else{
                                if(forEditService){
//                                    comp.setText(e.getName());
//                                    comp2.setText(e.getName());
                                    compCustomer.setCustomer(e); 
                                    removeThumb();
                                    selectCustomerPopupPanel.setVisible(false);
                                    glassPanel.setVisible(false);
                                }else{
                                    serviceCustomer = e;
                                    jTextField1.setText(e.getName());
                                    removeThumb();
                                    selectCustomerPopupPanel.setVisible(false);
                                    glassPanel.setVisible(false);
                                }
                            }
                        }

        //                @Override
        //                public void mouseExited(MouseEvent e) {
        //                    isHover = false;
        //                }
        //
        //                @Override
        //                public void mouseEntered(MouseEvent e) {
        //                    isHover = true;
        //                }


                    });
                    jPanel1.add(checkoutCustomerThumb);
                }
            }
        });
        
        revalidate();
        repaint();
    }
    
    public void removeThumb(){
        this.setVisible(false);
        revalidate();
        repaint();
        this.getParent().remove(this);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(darkRB.isSelected()){
//            g2.setColor(new Color(38, 41, 48));
//        }else{
//            g2.setColor(new Color(255, 255, 255));
//        }
        g2.setColor(this.getBackground());
        g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

        if(darkRB.isSelected()){
            g2.setColor(new Color(55, 55, 65));
        }else{
            g2.setColor(new Color(225, 225, 225));
        }
        
        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerThumb = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setPreferredSize(new java.awt.Dimension(45, 45));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setText("Contact Number");

        javax.swing.GroupLayout customerThumbLayout = new javax.swing.GroupLayout(customerThumb);
        customerThumb.setLayout(customerThumbLayout);
        customerThumbLayout.setHorizontalGroup(
            customerThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerThumbLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        customerThumbLayout.setVerticalGroup(
            customerThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerThumbLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerThumbLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(271, 223));
        setOpaque(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setOpaque(false);

        jPanel1.setMinimumSize(new java.awt.Dimension(200, 0));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Customer List");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
//        this.setVisible(false);
//        revalidate();
//        repaint();
//        this.getParent().remove(this);
//        isExited = true;
//        if(isExited && isHover == false){
//            removeThumb();
//        }
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
//        isExited = false;
    }//GEN-LAST:event_formMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel customerThumb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
