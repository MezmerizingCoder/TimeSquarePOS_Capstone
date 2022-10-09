/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import pos_timesquare.model.Variants;
import static pos_timesquare.view.MainFrame.productVariants;

/**
 *
 * @author Acer
 */
public class EditProductTableRow extends JPanel{
    
    JPanel jPanel52 = new JPanel();
    JPanel jPanel73 = new JPanel();
    JPanel jPanel74 = new JPanel();
    JPanel jPanel77 = new JPanel();
    JPanel jPanel78 = new JPanel();
    JPanel jPanel79 = new JPanel();
    
    JLabel jLabel76 = new JLabel();
    
    JTextField jTextField2 = new JTextField();
    JTextField jTextField4 = new JTextField();

    
    JCheckBox jCheckBox2 = new JCheckBox();
    
    JSpinner jSpinner3 = new JSpinner();
    
    String variantName;
    float variantPrice;
    int variantStocks;
    int variantBarcode;
    int variantId;

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }
    
    
    public float getVariantPrice() {
        return variantPrice;
    }

    public void setVariantPrice(float variantPrice) {
        this.variantPrice = variantPrice;
        jTextField2.setText(String.valueOf(variantPrice));
    }

    public int getVariantStocks() {
        return variantStocks;
    }

    public void setVariantStocks(int variantStocks) {
        this.variantStocks = variantStocks;
        jSpinner3.setValue(variantStocks);
    }

    public int getVariantBarcode() {
        return variantBarcode;
    }

    public void setVariantBarcode(int variantBarcode) {
        this.variantBarcode = variantBarcode;
        jTextField4.setText(String.valueOf(variantBarcode));
    }
    

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
        jLabel76.setText(variantName);
    }
    
    
    public EditProductTableRow(){
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel73.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel73.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel73.setPreferredSize(new java.awt.Dimension(141, 35));

        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel74.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel76.setText("Black / Leather");

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel77.setPreferredSize(new java.awt.Dimension(150, 14));

//        jTextField2.setText("Hello");

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2)
        );

        jPanel78.setPreferredSize(new java.awt.Dimension(150, 14));

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSpinner3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSpinner3)
        );

        jPanel79.setPreferredSize(new java.awt.Dimension(150, 14));

//        jTextField4.setText("Hello");

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField4)
        );

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel78, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );
        
        
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            public void removeUpdate(DocumentEvent e) {
              warn();
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }

            public void warn(){
                Variants variant = new Variants();
                for(int i = 0; i < productVariants.size(); i++){
//                    if(productVariants.get(i).getId() == variantId){
                    if(productVariants.get(i).getName().equals(variantName)){
                        variant = productVariants.get(i);
                        variant.setPrice(Float.parseFloat(jTextField2.getText()));
                        productVariants.set(i, variant);
                    }
                }
                productVariants.forEach(e -> {
                    System.out.println(e.getPrice());
                });
            }

        });
        
        jTextField4.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            public void removeUpdate(DocumentEvent e) {
              warn();
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }

            public void warn(){
                Variants variant = new Variants();
                for(int i = 0; i < productVariants.size(); i++){
//                    if(productVariants.get(i).getId() == variantId){
                    if(productVariants.get(i).getName().equals(variantName)){
                        variant = productVariants.get(i);
                        variant.setBarcode(Integer.parseInt(jTextField4.getText()));
                        productVariants.set(i, variant);
                    }
                }
                productVariants.forEach(e -> {
                    System.out.println(e.getBarcode());
                });
            }

        });
        
        jSpinner3.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ex) {
                Variants variant = new Variants();
                for(int i = 0; i < productVariants.size(); i++){
//                    if(productVariants.get(i).getId() == variantId){
                    if(productVariants.get(i).getName().equals(variantName)){
                        variant = productVariants.get(i);
                        variant.setStocks((int) jSpinner3.getValue());
                        productVariants.set(i, variant);
                    }
                }
                productVariants.forEach(e -> {
                    System.out.println(e.getStocks());
                });
            }
        });
        

        this.add(jPanel73);
    }
}
