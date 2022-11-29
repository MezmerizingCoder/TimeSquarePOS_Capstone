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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Variants;
import static pos_timesquare.view.MainFrame.checkoutProduct;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jSpinner4;
import static pos_timesquare.view.MainFrame.productImage;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.viewSelectedVariant;

/**
 *
 * @author Acer
 */
public class CheckoutProductThumbPanel extends JPanel{
    
    JPanel checkoutProductThumbPanel = new JPanel();
    private JLabel jLabel21 = new javax.swing.JLabel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

    //        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //        g2.setColor(new Color(232,232,232));
    //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

    //        g2.setColor(Color.GRAY);
    //        g2.setStroke(new BasicStroke(1));
    //        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(38, 42, 48));
//            }else{
//                g2.setColor(new Color(232,232,232));
//            }
////            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

            g2.setColor(this.getParent().getBackground());
            for(int i = 25; i >= 0; i--){
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
            }

            if(darkRB.isSelected()){
                g2.setColor(new Color(70, 70, 80));
            }else{
                g2.setColor(new Color(205,205,205));
            }

            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
        }

    };
    private JPanel jPanel25 = new JPanel();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel22 = new JLabel();
    private JSpinner jSpinner2 = new JSpinner();
    private JButton jButton9 = new JButton();
    private JLabel jLabel24 = new JLabel();
    private JLabel jLabel26 = new JLabel();
    private JLabel jLabel25 = new JLabel();
    
    private float price;
    private int items;
    private String variants;
    private String productName;
    private String image;
    private int productId;
    private int variantId;

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
//        spinnerListener();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerListener();
                
            }
        });
        spinnerListener();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        if(image != "" && image != null){
            BufferedImage bufferedImage = null;
            try {
                String cwd = System.getProperty("user.dir");
                bufferedImage = ImageIO.read(new File(cwd + image));
//                bufferedImage = ImageIO.read(this.getClass().getResource(image));
                if(bufferedImage != null){
                    Image scaledImage = bufferedImage.getScaledInstance(-1, 108, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    jLabel21.setIcon(imageIcon);
                }
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("width: " + jLabel21.getWidth());
            System.out.println("height: " + jLabel21.getHeight());
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        jLabel20.setText(productName);
    }

    public String getVariants() {
        return variants;
    }

    public void setVariants(String variants) {
        this.variants = variants;
        jLabel24.setText(variants);
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
        jSpinner2.setValue(items);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        jLabel25.setText(String.valueOf(price));
    }
    
    CheckoutProductThumbPanel(int key){
        
        //jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Product Name");
        
        jLabel21.setPreferredSize(new Dimension(130, 108));

        jLabel22.setText("Items");

        jButton9.setText("Cancel");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Size");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("P3,400");

//        jLabel26.setText("Small");
        
        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        javax.swing.GroupLayout checkoutProductThumbPanelLayout = new javax.swing.GroupLayout(checkoutProductThumbPanel);
        checkoutProductThumbPanel.setLayout(checkoutProductThumbPanelLayout);
        checkoutProductThumbPanelLayout.setHorizontalGroup(
            checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                        .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        checkoutProductThumbPanelLayout.setVerticalGroup(
            checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        
        
        
        
        jButton9.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for(int i = 0; i < checkoutProduct.get(key).size(); i++){
                    if(checkoutProduct.get(key).get(i).getId() == variantId){
                        checkoutProduct.get(key).remove(i);
                    }
                }
                if(checkoutProduct.get(key).size() == 0){
                    checkoutProduct.remove(key);
                }
//                checkoutProduct.remove(key);
                removeThumb();
                repaint();
                revalidate();
            }
        
        });
        setLayout(new BorderLayout());
        add(checkoutProductThumbPanel);
    }
    public void removeThumb(){
        this.getParent().remove(this);
    }
    public void spinnerListener(){
        List<String> tempList = new ArrayList<>();
        VariantService vService = new VariantService();
        Variants tempVariants = new Variants();

        List<Variants> variants = vService.getProductVariants(productId);
        
        if((int) jSpinner2.getValue() < 1){
            jSpinner2.setValue(1);
        }

        if(variantId == 0){
            ProductService ps = new ProductService();

            if(ps.getProductById(productId).getStocks() >= (int) jSpinner2.getValue() && ps.getProductById(productId).getStocks() != 0){
                tempVariants.setStocks((int) jSpinner2.getValue());
                List<Variants> listvar = checkoutProduct.get(productId);
                for(int i = 0; i < listvar.size(); i++){
                    if(listvar.get(i).getId() == variantId){
                        listvar.get(i).setStocks((int) jSpinner2.getValue());
                    }
                }
                checkoutProduct.put(productId, listvar);
                jSpinner2.putClientProperty("JComponent.outline", "default");
            }else{
                jSpinner2.putClientProperty("JComponent.outline", "warning");
//                        JOptionPane.showMessageDialog(this, selectedProduct.getName() + " not enough Stock! \nStock Available: " + selectedProduct.getStocks(), " Insuficient Stock",
//                            JOptionPane.WARNING_MESSAGE);
                System.out.println("Product id: " + productId);
                System.out.println("Available stocks: " + ps.getProductById(productId).getStocks());
            }
        }else{

//            viewSelectedVariant.forEach((k,e2)->{
//                tempList.add(e2);
//            });
//
//            String str = String.join("/", tempList);
//            variants.forEach(e->{
//                if(e.getMainVariant() == 0){
//                    if(e.getName().equals(str)){
//                        tempVariants.setPrice(e.getPrice());
//                        tempVariants.setId(e.getId());
//                    }
//                }
//            });

            Variants variant = vService.getVariantsById(variantId);
//            tempVariants.setId(selectedProduct.getId());
//            tempVariants.setName(str);
//            tempVariants.setStocks((int) jSpinner2.getValue());

            if(variant.getStocks() >= (int) jSpinner2.getValue() && variant.getStocks() != 0){
                for(int i = 0; i < checkoutProduct.get(productId).size(); i++){
                    if(checkoutProduct.get(productId).get(i).getId() == variantId){
                        checkoutProduct.get(productId).get(i).setStocks((int) jSpinner2.getValue());
                    }
                }
                
//                tempVariants.setStocks((int) jSpinner2.getValue());
                jSpinner2.putClientProperty("JComponent.outline", "default");
//                checkoutProduct.put(productId, tempVariants);
            }else{
                jSpinner2.putClientProperty("JComponent.outline", "warning");
//                JOptionPane.showMessageDialog(this, selectedProduct.getName()+ "(" + tempVariants.getId()+ ") not enough Stock! \nStock Available: " + variant.getStocks(), " Insuficient Stock",
//                    JOptionPane.WARNING_MESSAGE);
            }

        }
        System.out.println(checkoutProduct.toString());
    }
}
