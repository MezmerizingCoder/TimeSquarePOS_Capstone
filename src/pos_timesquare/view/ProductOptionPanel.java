/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pos_timesquare.controller.VariantService;

/**
 *
 * @author Acer
 */
public class ProductOptionPanel extends JPanel{
    
    JPanel jPanel47 = new JPanel();
    JPanel jPanel81 = new JPanel();
    JPanel jPanel82 = new JPanel();
    JPanel jPanel83 = new JPanel();
    JPanel jPanel51 = new JPanel();
    
    JLabel jLabel54 = new JLabel();
    JLabel jLabel65 = new JLabel();
    
    JButton jButton10 = new JButton();
    
    JScrollPane jScrollPane12 = new JScrollPane();


    String type;
    List<String> value = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        jLabel54.setText(type);
    }

    public List<String> getValue() {
        return value;
        
    }

    public void setValue(List<String> value) {
        this.value = value;
        
        value.forEach(e -> {
            JLabel jLabel = new JLabel();
            jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel.setText(e);
            jLabel.setMinimumSize(new java.awt.Dimension(70, 30));
            jLabel.setPreferredSize(new java.awt.Dimension(70, 30));
            jLabel.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 30 ) );
            jPanel51.add(jLabel);
        });
        
    }
    
    
    public ProductOptionPanel(){
//        this.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234,234,234)));
        this.setPreferredSize(new java.awt.Dimension(405, 60));
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel81.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel81.setPreferredSize(new java.awt.Dimension(586, 40));

        jLabel54.setText("Color");

        jButton10.setText("Edit");
        jButton10.setPreferredSize(new java.awt.Dimension(79, 35));

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        this.add(jPanel81);

        jPanel82.setMaximumSize(new java.awt.Dimension(32767, 55));
        jPanel82.setPreferredSize(new java.awt.Dimension(586, 40));

        jScrollPane12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
//        jScrollPane12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234,234,234)));

        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel83.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel83.setLayout(new javax.swing.BoxLayout(jPanel83, javax.swing.BoxLayout.LINE_AXIS));

        jPanel51.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

//        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel65.setText("Red");
//        jLabel65.setMinimumSize(new java.awt.Dimension(70, 30));
//        jLabel65.setPreferredSize(new java.awt.Dimension(70, 30));
//        jLabel65.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 30 ) );
        
//        jPanel51.add(jLabel65);
        
        VariantService vService = new VariantService();

        jPanel83.add(jPanel51);

        jScrollPane12.setViewportView(jPanel83);

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initAddProductOption();
            }
        });

        this.add(jPanel82);
    }
    
    public void initAddProductOption(){
        this.removeAll();
        AddProductOptionPanel addOption = new AddProductOptionPanel();
        addOption.initVariants(type, value);
        this.add(addOption);
        
        revalidate();
        repaint();
    }
}
