/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Variants;
import static pos_timesquare.view.MainFrame.jLabel12;
import static pos_timesquare.view.MainFrame.jLabel65;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.viewProductPrice;
import static pos_timesquare.view.MainFrame.viewSelectedVariant;
import static pos_timesquare.view.MainFrame.viewProductSelectedVariants;
import static pos_timesquare.view.MainFrame.selectedProductPrice;
/**
 *
 * @author Acer
 */
public class ViewProductOptionPanel extends JPanel{
    
    JPanel jPanel12 = new JPanel();
    JPanel jPanel51 = new JPanel();
    JPanel jPanel27 = new JPanel();
    
    JLabel jLabel48 = new JLabel();

    
    JToggleButton jToggleButton2 = new JToggleButton();
    JToggleButton jToggleButton3 = new JToggleButton();

    ButtonGroup buttonGroup = new ButtonGroup();
    List<String> tempVariants = new ArrayList<>();
    
    boolean firstElementSelected = false;

    public ViewProductOptionPanel(String type, List<Variants>value){
        
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel27.setLayout(flowLayout1);
        jLabel65.setText(selectedProduct.getName());

//        jToggleButton2.setText("Red");
//        jToggleButton2.setMaximumSize(new java.awt.Dimension(150, 23));
//        jToggleButton2.setMinimumSize(new java.awt.Dimension(70, 23));
//        jToggleButton2.setPreferredSize(new java.awt.Dimension(80, 40));
//        jPanel27.add(jToggleButton2);
//
//        jToggleButton3.setText("afsdfdfdfdf");
//        jToggleButton3.setMaximumSize(new java.awt.Dimension(150, 23));
//        jToggleButton3.setPreferredSize(new java.awt.Dimension(80, 40));
//        jPanel27.add(jToggleButton3);

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText(type);
        
        value.forEach(e -> {
            JToggleButton jToggleButton = new JToggleButton();
            jToggleButton.setText(e.getName());
//            jToggleButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            jToggleButton.setPreferredSize(new java.awt.Dimension(e.getName().length()*2 + 80, 40));
            jToggleButton.setActionCommand(e.getName());
            
            if(!firstElementSelected){
                jToggleButton.setSelected(true);
                firstElementSelected = true;
            }
            
            jPanel27.add(jToggleButton);
            buttonGroup.add(jToggleButton);
            
            viewSelectedVariant.put(type, buttonGroup.getSelection().getActionCommand());
            jToggleButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    System.out.println("Selected: " + buttonGroup.getSelection().getActionCommand());
                    
                    viewSelectedVariant.put(type, buttonGroup.getSelection().getActionCommand());
                    System.out.println(viewSelectedVariant);
                    
                    List<String> tempList = new ArrayList<>();
                    viewSelectedVariant.forEach((k,e2)->{
                        tempList.add(e2);
                    });
                    String str = String.join("/", tempList);
                    viewProductSelectedVariants.setText(str);
                    
                    VariantService vService = new VariantService();
                    vService.getProductVariants(selectedProduct.getId()).forEach(e2->{
                        if(e2.getName().equals(str)){
                            viewProductPrice.setText(String.valueOf(e2.getPrice()));
                            jLabel12.setText(String.valueOf(e2.getPrice()));
                            selectedProductPrice = e2.getPrice();
                            
                        }
                    });
                    
                    
                }
            });
        });
        
        viewSelectedVariant.put(type, buttonGroup.getSelection().getActionCommand());
        System.out.println(viewSelectedVariant);

        List<String> tempList = new ArrayList<>();
        viewSelectedVariant.forEach((k,e2)->{
            tempList.add(e2);
        });
        String str = String.join("/", tempList);
        viewProductSelectedVariants.setText(str);

        VariantService vService = new VariantService();
        vService.getProductVariants(selectedProduct.getId()).forEach(e2->{
            if(e2.getName().equals(str)){
                viewProductPrice.setText(String.valueOf(e2.getPrice()));
                jLabel12.setText(String.valueOf(e2.getPrice()));
                selectedProductPrice = e2.getPrice();
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
    }
    public void updateData(){
    
    }
}
