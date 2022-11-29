///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pos_timesquare.view;
//
//import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
//import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
//import java.awt.BasicStroke;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.RenderingHints;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import static java.time.zone.ZoneRulesProvider.refresh;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.Timer;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.table.DefaultTableModel;
//import pos_timesquare.controller.VariantService;
//import pos_timesquare.model.Product;
//import pos_timesquare.model.Variants;
//import static pos_timesquare.view.MainFrame.addProductVariants;
//import static pos_timesquare.view.MainFrame.blurBGPanel;
//import static pos_timesquare.view.MainFrame.checkoutProduct;
//import static pos_timesquare.view.MainFrame.darkRB;
//import static pos_timesquare.view.MainFrame.editProductNameTextField;
//import static pos_timesquare.view.MainFrame.editProductPanel;
//import static pos_timesquare.view.MainFrame.editProductPriceField;
//import static pos_timesquare.view.MainFrame.editProductStockField;
//import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
//import static pos_timesquare.view.MainFrame.jPanel11;
//import static pos_timesquare.view.MainFrame.jPanel45;
//import static pos_timesquare.view.MainFrame.jPanel46;
//import static pos_timesquare.view.MainFrame.jPanel47;
//import static pos_timesquare.view.MainFrame.jProgressBar1;
//import static pos_timesquare.view.MainFrame.jSpinner4;
//import static pos_timesquare.view.MainFrame.popupContentPanel;
//import static pos_timesquare.view.MainFrame.popupPanel;
//import static pos_timesquare.view.MainFrame.productImage;
//import static pos_timesquare.view.MainFrame.productImage1;
//import static pos_timesquare.view.MainFrame.productVariants;
//import static pos_timesquare.view.MainFrame.tabPanel;
//import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
//import static pos_timesquare.view.MainFrame.viewProductName;
//import static pos_timesquare.view.MainFrame.viewProductPanel;
//import static pos_timesquare.view.MainFrame.viewProductVariantTable;
//import static pos_timesquare.view.MainFrame.selectedProduct;
//import static pos_timesquare.view.MainFrame.viewProductPrice;
//import static pos_timesquare.view.MainFrame.viewSelectedVariant;
//import static pos_timesquare.view.MainFrame.selectedFile;
//import static pos_timesquare.view.MainFrame.updateGraphics;
//
//
///**
// *
// * @author Acer
// */
//public class ProductThumb extends JPanel {
//    
//    private JPanel jPanel40 = new javax.swing.JPanel(){
//
//        public void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(Color.GRAY);
////            g2.setStroke(new BasicStroke(1));
//            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//        }
//
//    };
//    
//    private JPanel jPanel44 = new javax.swing.JPanel(){
//
//        public void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(33, 37, 43));
//            }else{
//                g2.setColor(new Color(235,235,235));
//            }
//            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
//        }
//
//    };
//    private JLabel jLabel47 = new JLabel();
//    private JLabel jLabel48 = new JLabel();
//    private JLabel jLabel49 = new JLabel();
//    private JLabel jLabel50 = new JLabel();
//    private JLabel jLabel51 = new JLabel();
//    private JLabel jLabel55 = new JLabel();
//    
//
//    private String productName;
//    private int productStocks;
//    
//    private Product productDetails;
//    
//    Timer timer;
//    
//
//    public Product getProductDetails() {
//        return productDetails;
//    }
//
//    public void setProductDetails(Product productDetails) {
//        this.productDetails = productDetails;
//        jLabel47.setText(productDetails.getName());
//        jLabel50.setText(String.valueOf(productDetails.getStocks()));
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//        jLabel47.setText(productName);
//    }
//
//    public int getProductStocks() {
//        return productStocks;
//    }
//
//    public void setProductStocks(int productStocks) {
//        this.productStocks = productStocks;
//        jLabel50.setText(String.valueOf(productStocks));
//    }
//    
//    
//
//    
//    public ProductThumb(){
//        jPanel40.setPreferredSize(new java.awt.Dimension(220, 95));
//
//        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        jLabel47.setText("Rolex xxx");
//
//        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
//        jLabel49.setText("In Stock:");
//
//        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
//        jLabel50.setText("20");
//
//        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
//        jPanel44.setLayout(jPanel44Layout);
//        jPanel44Layout.setHorizontalGroup(
//            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel44Layout.createSequentialGroup()
//                .addGap(7, 7, 7)
//                .addComponent(jLabel49)
//                .addGap(0, 0, 0)
//                .addComponent(jLabel50)
//                .addGap(7, 7, 7))
//        );
//        jPanel44Layout.setVerticalGroup(
//            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel44Layout.createSequentialGroup()
//                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(0, 0, 0))
//        );
//
//        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/favorite-icon-gray.png"))); // NOI18N
//
//        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pencil-icon-gray.png"))); // NOI18N
//
//        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
//        jPanel40.setLayout(jPanel40Layout);
//        jPanel40Layout.setHorizontalGroup(
//            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel40Layout.createSequentialGroup()
//                .addGap(15, 15, 15)
//                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel47)
//                    .addGroup(jPanel40Layout.createSequentialGroup()
//                        .addComponent(jLabel48)
//                        .addGap(0, 0, 0)
//                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addContainerGap(138, Short.MAX_VALUE))
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(3, 3, 3)
//                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(15, 15, 15))
//        );
//        jPanel40Layout.setVerticalGroup(
//            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel40Layout.createSequentialGroup()
//                .addGap(15, 15, 15)
//                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(0, 0, 0)
//                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel48)
//                .addGap(5, 5, 5)
//                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(15, 15, 15))
//        );
//        jPanel40.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                showPopup();
//                jProgressBar1.setVisible(true);
//                viewSelectedVariant = new HashMap<>();
//                
//                HashMap<String, List<Variants>> variants = new HashMap<>();
//                Object[][] sortedVariants;
//                
//                tabPanel.add(ticketMainPanelHeader);
//                
//                blurBGPanel.setVisible(true);
//                popupPanel.setVisible(true);
//                popupContentPanel.removeAll();
//                popupContentPanel.add(viewProductPanel);
//                
//                selectedProduct = productDetails;
//
//                ///checkout thumb
//                productImage1.setIcon(null);
//                productImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//                Thread t2 = new Thread(new Runnable() {
//                    public void run() {
//                        if(productDetails.getImage() != "" && productDetails.getImage() != null){
//                            BufferedImage bufferedImage = null;
//                            try {
//                                bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
//                                if(bufferedImage != null){
////                                    Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
////                                    Image scaledImage = bufferedImage.getScaledInstance(-1, productImage.getHeight(), Image.SCALE_SMOOTH);
//                                    Image scaledImage;
//                                    if(bufferedImage.getWidth() > bufferedImage.getHeight()){
//                                        scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), -1, Image.SCALE_SMOOTH);
//                                    }else{
//                                         scaledImage = bufferedImage.getScaledInstance(-1, productImage.getHeight(), Image.SCALE_SMOOTH);
//                                    }
//                                    ImageIcon image = new ImageIcon(scaledImage);
//                                    productImage1.setIcon(image);
//                                }
//                            } catch (IOException ex) {
//                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }
//                });
//                t2.start();
//                
////                if(productDetails.getImage() != "" && productDetails.getImage() != null){
////                    BufferedImage bufferedImage = null;
////                    try {
////                        bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
////                        if(bufferedImage != null){
////                            Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
////                            ImageIcon image = new ImageIcon(scaledImage);
////                            productImage1.setIcon(image);
////                        }
////                    } catch (IOException ex) {
////                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
//                
//                
//                viewProductName.setText(productDetails.getName());
//                jSpinner4.setValue(0);
//                checkoutProduct.forEach((k, e)->{
//                    if(k == productDetails.getId()){
//                        jSpinner4.setValue(e.getStocks());
//                    }
//                });
//                
//                
//                jPanel11.removeAll();
//                
//                VariantService vService = new VariantService();
//                vService.getProductVariants(productDetails.getId()).forEach(e -> {
//                    if(variants.containsKey(e.getType())){
//                        List<Variants> temp = new ArrayList<>();
//                        Variants v = new Variants();
////                        v.setType(e.getType());
////                        v.setName(e.getName());
////                        v.setPrice(e.getPrice());
////                        v.setBarcode(e.getBarcode());
//                        temp = variants.get(e.getType());
//                        temp.add(e);
//                        variants.put(e.getType(), temp);
//                    }else{
//                        List<Variants> temp = new ArrayList<>();
//                        temp.add(e);
//                        variants.put(e.getType(), temp);
//                    }
//                    
//                });
//                
//                System.out.println(variants);
//                System.out.println(variants.size());
//                
//                ///view product table row
//                DefaultTableModel model = (DefaultTableModel) viewProductVariantTable.getModel();
//                model.setRowCount(0);
//                Object rowData[] = new Object[10]; 
//                variants.forEach((k,e)->{
//                    e.forEach(e2 ->{
//                        if(e2.getMainVariant() == 0){
//                            rowData[0] = e2.getName();
//                            rowData[1] = e2.getPrice();
//                            rowData[2] = "In stock: "+ e2.getStocks();
//
//                            model.addRow(rowData);
//                        }
//                    });
//                    if(!k.equals("generated")){
//                        jPanel11.add(new ViewProductOptionPanel(k, e));
//                    }
//                });
//                
//                
////                JFrame frame = (JFrame)getTopLevelAncestor();
//
////                popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
////                Thread t = new Thread(new Runnable() {
////                    public void run() {
////                        while(popupPanel.getX() > 0){
////                            popupPanel.setBounds(popupPanel.getX() - 15, 0, frame.getWidth(), frame.getHeight());
////                            try {
////                                Thread.sleep((long) 1);
////                            } catch (InterruptedException ex) {
////                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
////                            }
////                        }
////                        if(popupPanel.getX() < 0){
////                            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
////                        }
////                    }
////                });
////                t.start();
////                if(popupPanel.getX() <= 0) t.stop();
//
////                JFrame frame = this;
////                Thread t = new Thread(new Runnable() {
////                    public void run() {
////                        popupPanel.setVisible(true);
////                        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
////                        timer = new Timer(1, new ActionListener() {
////                            @Override
////                            public void actionPerformed(ActionEvent ae) {
////                                popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
////
////                                if(popupPanel.getX() < 0){
////                                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
////                                    timer.stop();
////                                }
////                            }
////                        });
////                        timer.start();
////                    }
////                });
////                t.start();
////                if(popupPanel.getX() <= 0) t.stop();
//                
////                if(darkRB.isSelected()){
////                    try {
////                        System.out.println("Dark");
//////                        FlatAtomOneDarkContrastIJTheme.install();
////                        UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
////                        FlatAtomOneDarkContrastIJTheme.updateUI();
////                    } catch (UnsupportedLookAndFeelException ex) {
////                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                    
////                }else {
////                    try {
////                        //                        FlatMaterialLighterIJTheme.install();
////                        UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
////                        FlatMaterialLighterIJTheme.updateUI();
////                    } catch (UnsupportedLookAndFeelException ex) {
////                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
//                updateGraphics();
//                jProgressBar1.setVisible(false);
//            }
//        });
//        
//        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        
//        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                showPopup();
//                jProgressBar1.setVisible(true);
//                selectedProduct = productDetails;
//                addProductVariants.clear();
//                productVariants.clear();
//                System.out.println(addProductVariants);
//                
//                selectedFile = null;
//                
//                tabPanel.add(ticketMainPanelHeader);
//                
//                VariantService service = new VariantService();
//                List<String> list = new ArrayList<>();
//                service.getProductVariants(productDetails.getId()).forEach(e -> {
//                    if(addProductVariants.containsKey(e.getType())){
//                        List<String> temp = new ArrayList<>();
//                        temp = addProductVariants.get(e.getType());
//                        temp.add(e.getName());
//                        addProductVariants.put(e.getType(), temp);
//                    }else{
//                        List<String> temp = new ArrayList<>();
//                        temp.add(e.getName());
//                        addProductVariants.put(e.getType(), temp);
//                    }
//                    
//                    if(e.getMainVariant() == 0){
//                        productVariants.add(e);
//                    }
//                });
//                System.out.println(addProductVariants);
//                
//                
//                
//                
//                jPanel46.removeAll();
//                jPanel47.removeAll();
//                addProductVariants.forEach((k , e) -> {
//                    if(!k.equals("generated")){
//                        
//                        ProductOptionPanel option = new ProductOptionPanel();
//                        option.setType(k);
//                        option.setValue(e);
//                        JPanel panel = new JPanel();
////                        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234,234,234)));
//                        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
//                        panel.add(option);
//                        jPanel46.add(panel);
//                    }
////                    else{
////                        e.forEach(e2 -> {
////                            EditProductTableRow tableRow = new EditProductTableRow();
////                            tableRow.setVariantName(e2);
////                            jPanel47.add(tableRow);
////                        });
////                        
////                        
////                    }
//                });
//                
//                productVariants.forEach(e -> {
//                    EditProductTableRow tableRow = new EditProductTableRow();
//                    tableRow.setVariantName(e.getName());
//                    tableRow.setVariantPrice(e.getPrice());
//                    tableRow.setVariantStocks(e.getStocks());
//                    tableRow.setVariantBarcode(e.getBarcode());
//                    tableRow.setVariantId(e.getId());
//                    jPanel47.add(tableRow);
//                            
//                    revalidate();
//                    repaint();
//                });
//                
//                blurBGPanel.setVisible(true);
//                popupPanel.setVisible(true);
//                popupContentPanel.removeAll();
//                popupContentPanel.add(editProductPanel);
//                
//                editProductNameTextField.setText(productDetails.getName());
//                editProductPriceField.setText(String.valueOf(productDetails.getPrice()));
//                editProductStockField.setValue(productDetails.getStocks());
//                
//                productImage.setIcon(null);
//                productImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//                if(productDetails.getImage() != "" && productDetails.getImage() != null){
//                    BufferedImage bufferedImage = null;
//                    try {
//                        bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
//                        if(bufferedImage != null){
////                            Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
//                            Image scaledImage;
//                            if(bufferedImage.getWidth() > bufferedImage.getHeight()){
//                                scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), -1, Image.SCALE_SMOOTH);
//                            }else{
//                                 scaledImage = bufferedImage.getScaledInstance(-1, productImage.getHeight(), Image.SCALE_SMOOTH);
//                            }
//                            ImageIcon image = new ImageIcon(scaledImage);
//                            productImage.setIcon(image);
//                        }
//                    } catch (IOException ex) {
//                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//                
////                JFrame frame = (JFrame)getTopLevelAncestor();
//
////                popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
////                
////                Thread t = new Thread(new Runnable() {
////                    public void run() {
////                        while(popupPanel.getX() > 0){
////                            popupPanel.setBounds(popupPanel.getX() - 15, 0, frame.getWidth(), frame.getHeight());
////                            try {
////                                Thread.sleep((long) 1);
////                            } catch (InterruptedException ex) {
////                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
////                            }
////                        }
////                        if(popupPanel.getX() < 0){
////                            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
////                        }
////                    }
////                });
////                t.start();
////                if(popupPanel.getX() <= 0) t.stop();
//
////                Thread t = new Thread(new Runnable() {
////                    public void run() {
////                        popupPanel.setVisible(true);
////                        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
////                        timer = new Timer(1, new ActionListener() {
////                            @Override
////                            public void actionPerformed(ActionEvent ae) {
////                                popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
////
////                                if(popupPanel.getX() < 0){
////                                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
////                                    timer.stop();
////                                }
////                            }
////                        });
////                        timer.start();
////                    }
////                });
////                t.start();
////                if(popupPanel.getX() <= 0) t.stop();
////                
////                if(darkRB.isSelected()){
////                    try {
////                        System.out.println("Dark");
//////                        FlatAtomOneDarkContrastIJTheme.install();
////                        UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
////                        FlatAtomOneDarkContrastIJTheme.updateUI();
////                    } catch (UnsupportedLookAndFeelException ex) {
////                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                    
////                }else {
////                    try {
////                        //                        FlatMaterialLighterIJTheme.install();
////                        UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
////                        FlatMaterialLighterIJTheme.updateUI();
////                    } catch (UnsupportedLookAndFeelException ex) {
////                        Logger.getLogger(ProductThumb.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
//                updateGraphics();
//                jProgressBar1.setVisible(false);
////                jPanel46
//            }
//        });
//        
//        setLayout(new BorderLayout());
//        add(jPanel40);
//        
//        
//    }
//    
//    void showPopup(){
//        JFrame frame = (JFrame)getTopLevelAncestor();
//        if(jCheckBoxMenuItem1.isSelected()){
//            
//            popupPanel.setVisible(true);
//            popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
//            timer = new Timer(1, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent ae) {
//                    popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
//
//                    if(popupPanel.getX() < 0){
//                        popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//                        timer.stop();
//                    }
//                }
//            });
//            timer.start();
//        }else{
//            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//        }
//    }
//    
//}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Product;
import pos_timesquare.model.Variants;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import static pos_timesquare.view.MainFrame.addProductVariants;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.checkoutProduct;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.editProductBrand;
import static pos_timesquare.view.MainFrame.editProductNameTextField;
import static pos_timesquare.view.MainFrame.editProductPanel;
import static pos_timesquare.view.MainFrame.editProductPriceField;
import static pos_timesquare.view.MainFrame.editProductStockField;
import static pos_timesquare.view.MainFrame.editProductType;
import static pos_timesquare.view.MainFrame.favoriteCount;
import static pos_timesquare.view.MainFrame.jButton36;
import static pos_timesquare.view.MainFrame.jButton41;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jLabel12;
import static pos_timesquare.view.MainFrame.jLabel199;
import static pos_timesquare.view.MainFrame.jLabel215;
import static pos_timesquare.view.MainFrame.jLabel65;
import static pos_timesquare.view.MainFrame.jPanel11;
import static pos_timesquare.view.MainFrame.jPanel46;
import static pos_timesquare.view.MainFrame.jPanel47;
import static pos_timesquare.view.MainFrame.jProgressBar1;
import static pos_timesquare.view.MainFrame.jSpinner4;
import static pos_timesquare.view.MainFrame.jTextField17;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.productImage;
import static pos_timesquare.view.MainFrame.productImage1;
import static pos_timesquare.view.MainFrame.productVariants;
import static pos_timesquare.view.MainFrame.selectedFile;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.tabPanel;
import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
import static pos_timesquare.view.MainFrame.updateGraphics;
import static pos_timesquare.view.MainFrame.viewProductName;
import static pos_timesquare.view.MainFrame.viewProductPanel;
import static pos_timesquare.view.MainFrame.viewProductPrice;
import static pos_timesquare.view.MainFrame.viewProductSelectedVariants;
import static pos_timesquare.view.MainFrame.viewProductVariantTable;
import static pos_timesquare.view.MainFrame.viewSelectedVariant;
import static pos_timesquare.view.MainFrame.selectedProductPrice;

/**
 *
 * @author Acer
 */
public class ProductThumb extends JPanel{
    
    JPanel productThumb1 = new JPanel();
    JPanel jPanel122 = new javax.swing.JPanel(){

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
    
    JLabel jLabel125 = new JLabel();
    JLabel jLabel127= new JLabel();
    JLabel jLabel128= new JLabel();
    JLabel jLabel129= new JLabel();
    JLabel jLabel130= new JLabel();
    JLabel jLabel131= new javax.swing.JLabel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(this.getBackground());
            for(int i = 25; i >= 0; i--){
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
            }
//            g2.setColor(Color.GRAY);
            if(darkRB.isSelected()){
                g2.setColor(new Color(58, 61, 68));
            }else{
                g2.setColor(new Color(225, 225, 225));
            }
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            
            if(isHover){
                g2.setColor(new Color(0,144,228));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }
        }

    };
    JLabel jLabel126= new JLabel();
    JButton jButton22 = new JButton();
    JSpinner jSpinner5 = new JSpinner();
    
    private String productName;
    private int productStocks;
    private String image;
    
    String currentType;
    String currentBrand;
    
    int totalStocks = 0;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        
        Thread t = new Thread(new Runnable() {
            public void run() {
                BufferedImage bufferedImage = null;
                try {
                    String cwd = System.getProperty("user.dir");
                    bufferedImage = ImageIO.read(new File(cwd + image));
//                    bufferedImage = ImageIO.read(getClass().getResource(image));
                } catch (IOException ex) {

                }
                Image scaledImage;
                if(bufferedImage.getWidth() > bufferedImage.getHeight()){
                    scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);
//                     scaledImage = bufferedImage.getScaledInstance(-1, 120, Image.SCALE_SMOOTH);
                }else{
//                     scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);
                    scaledImage = bufferedImage.getScaledInstance(-1, 120, Image.SCALE_SMOOTH);
                }

                ImageIcon imageIcon = new ImageIcon(scaledImage);

                jLabel131.setIcon(imageIcon);
                System.out.println(bufferedImage.getWidth() + " " + bufferedImage.getHeight());
            }
        });
        t.start();
    }
    
    private Product productDetails;
    
    Timer timer;
    boolean isHover =false;
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(isHover){
//            g2.setColor(new Color(0,144,228));
//            g2.setStroke(new BasicStroke(1));
//            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//        }
    
    }
    
    
    public Product getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Product productDetails) {
        this.productDetails = productDetails;
        jLabel125.setText(productDetails.getName());
        
        VariantService vs = new VariantService();
        List<Variants> variant  = vs.getProductVariants(productDetails.getId());
        
        totalStocks = 0;
        if(variant.size() > 0){
            variant.forEach(e -> {
                if(e.getStatus() == 1){
                    totalStocks += e.getStocks();
                }
            });
            jLabel128.setText(String.valueOf(totalStocks));
        }else{
            jLabel128.setText(String.valueOf(productDetails.getStocks()));
        }
        
        setImage(productDetails.getImage());
        
        if(productDetails.getFavorite() == 0){
            FlatSVGIcon favicon = new FlatSVGIcon("img/icon/heart-regular.svg", 16, 16);
            favicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                public Color apply(Color t){
                    return new Color(84, 84, 84);
                }
            }));
            jLabel129.setIcon(favicon);
        }else{
            FlatSVGIcon favicon = new FlatSVGIcon("img/icon/heart-solid.svg", 16, 16);
            favicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                public Color apply(Color t){
                    return new Color(255, 79, 79);
                }
            }));
            jLabel129.setIcon(favicon);
        }
        
        
        
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        jLabel125.setText(productName);
    }

    public int getProductStocks() {
        return productStocks;
    }

    public void setProductStocks(int productStocks) {
        this.productStocks = productStocks;
        jLabel128.setText(String.valueOf(productStocks));
    }
    
    
    
    
    public ProductThumb(){
        
        this.setPreferredSize(new java.awt.Dimension(220, 216));

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel125.setText("Rolex xxx");

        jLabel127.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel127.setText("In Stock:");

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel128.setText("20");

        javax.swing.GroupLayout jPanel122Layout = new javax.swing.GroupLayout(jPanel122);
        jPanel122.setLayout(jPanel122Layout);
        jPanel122Layout.setHorizontalGroup(
            jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel122Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel127)
                .addGap(0, 0, 0)
                .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );
        jPanel122Layout.setVerticalGroup(
            jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel122Layout.createSequentialGroup()
                .addGroup(jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/favorite-icon-gray.png"))); // NOI18N
        FlatSVGIcon favicon = new FlatSVGIcon("img/icon/heart-regular.svg", 16, 16);
        favicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(84, 84, 84);
            }
        }));
        jLabel129.setIcon(favicon);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pencil-icon-gray.png"))); // NOI18N
        FlatSVGIcon editicon = new FlatSVGIcon("img/icon/pencil-solid.svg", 13, 13);
        editicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(84, 84, 84);
            }
        }));
        jLabel130.setIcon(editicon);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setPreferredSize(new java.awt.Dimension(220, 120));

        jButton22.setBackground(new java.awt.Color(51, 51, 51));
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Add to Checkout");
        jButton22.setPreferredSize(new java.awt.Dimension(79, 40));

        jSpinner5.setPreferredSize(new java.awt.Dimension(30, 38));

        javax.swing.GroupLayout productThumb1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(productThumb1Layout);
        productThumb1Layout.setHorizontalGroup(
            productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumb1Layout.createSequentialGroup()
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productThumb1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addComponent(jSpinner5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel125)
                            .addGroup(productThumb1Layout.createSequentialGroup()
                                .addComponent(jLabel126)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        productThumb1Layout.setVerticalGroup(
            productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumb1Layout.createSequentialGroup()
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel126))
        );
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshSpinner();
                showPopup();
                jProgressBar1.setVisible(true);
                viewSelectedVariant = new HashMap<>();
                
                HashMap<String, List<Variants>> variants = new HashMap<>();
                Object[][] sortedVariants;
                
                tabPanel.add(ticketMainPanelHeader);
                
                blurBGPanel.setVisible(true);
                popupPanel.setVisible(true);
                popupContentPanel.removeAll();
                popupContentPanel.add(viewProductPanel);
                
                selectedProduct = productDetails;

                ///checkout thumb
                productImage1.setIcon(null);
                productImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                Thread t2 = new Thread(new Runnable() {
                    public void run() {
                        if(productDetails.getImage() != "" && productDetails.getImage() != null){
                            BufferedImage bufferedImage = null;
                            try {
                                String cwd = System.getProperty("user.dir");
                                bufferedImage = ImageIO.read(new File(cwd + productDetails.getImage()));
//                                bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
                                if(bufferedImage != null){
//                                    Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
                                    Image scaledImage = bufferedImage.getScaledInstance(-1, productImage.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon image = new ImageIcon(scaledImage);
                                    productImage1.setIcon(image);
                                }
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                t2.start();
                
                
                viewProductName.setText(productDetails.getName());
                jSpinner4.setValue(1);
//                checkoutProduct.forEach((k, e)->{
//                    if(k == productDetails.getId()){
//                        jSpinner4.setValue(e.getStocks());
//                    }
//                });
                
                jLabel65.setText(productDetails.getName());
                viewProductPrice.setText(String.valueOf(productDetails.getPrice()));
                
                jLabel12.setText(String.valueOf(productDetails.getPrice()));
                viewProductSelectedVariants.setText("");
                selectedProductPrice = productDetails.getPrice();
                
                jPanel11.removeAll();
                
                VariantService vService = new VariantService();
                vService.getProductVariants(productDetails.getId()).forEach(e -> {
                    if(variants.containsKey(e.getType())){
                        List<Variants> temp = new ArrayList<>();
                        Variants v = new Variants();
//                        v.setType(e.getType());
//                        v.setName(e.getName());
//                        v.setPrice(e.getPrice());
//                        v.setBarcode(e.getBarcode());
                        temp = variants.get(e.getType());
                        temp.add(e);
                        variants.put(e.getType(), temp);
                    }else{
                        List<Variants> temp = new ArrayList<>();
                        temp.add(e);
                        variants.put(e.getType(), temp);
                    }
                    
                });
                
                System.out.println(variants);
                System.out.println(variants.size());
                
                ///view product table row
                DefaultTableModel model = (DefaultTableModel) viewProductVariantTable.getModel();
                model.setRowCount(0);
                Object rowData[] = new Object[10]; 
                variants.forEach((k,e)->{
                    e.forEach(e2 ->{
                        if(e2.getMainVariant() == 0){
                            rowData[0] = e2.getName();
                            rowData[1] = e2.getPrice();
                            
                            if(e2.getStatus() == 1){
                                rowData[2] = "In Stock: "+ e2.getStocks();
                            }else{
                                rowData[2] = "Not Available";
                            }

                            model.addRow(rowData);
                        }
                    });
                    if(!k.equals("generated")){
                        jPanel11.add(new ViewProductOptionPanel(k, e));
                    }
                });
                
                
                
                
                jProgressBar1.setVisible(false);
                updateGraphics();
                
            }
        });
        
        jLabel130.setVisible(false);
        jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPopup();
                jProgressBar1.setVisible(true);
                selectedProduct = productDetails;
                addProductVariants.clear();
                productVariants.clear();
                System.out.println(addProductVariants);
                
                selectedFile = null;
                
                tabPanel.add(ticketMainPanelHeader);
                
                VariantService service = new VariantService();
                List<String> list = new ArrayList<>();
                service.getProductVariants(productDetails.getId()).forEach(e -> {
                    if(addProductVariants.containsKey(e.getType())){
                        List<String> temp = new ArrayList<>();
                        temp = addProductVariants.get(e.getType());
                        temp.add(e.getName());
                        addProductVariants.put(e.getType(), temp);
                    }else{
                        List<String> temp = new ArrayList<>();
                        temp.add(e.getName());
                        addProductVariants.put(e.getType(), temp);
                    }
                    
                    if(e.getMainVariant() == 0){
                        productVariants.add(e);
                    }
                });
                System.out.println(addProductVariants);
                
                
                
                
                jPanel46.removeAll();
                jPanel47.removeAll();
                addProductVariants.forEach((k , e) -> {
                    if(!k.equals("generated")){
                        
                        ProductOptionPanel option = new ProductOptionPanel();
                        option.setType(k);
                        option.setValue(e);
                        JPanel panel = new JPanel();
//                        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234,234,234)));
                        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
                        panel.add(option);
                        jPanel46.add(panel);
                    }
                });
                
                productVariants.forEach(e -> {
                    EditProductTableRow tableRow = new EditProductTableRow();
                    tableRow.setVariantName(e.getName());
                    tableRow.setVariantPrice(e.getPrice());
                    tableRow.setVariantStocks(e.getStocks());
                    tableRow.setVariantBarcode(e.getBarcode());
                    tableRow.setVariantId(e.getId());
                    tableRow.setVariantStatus(e.getStatus());
                    
                    jPanel47.add(tableRow);
                            
//                    updateGraphics();
                });
                
                blurBGPanel.setVisible(true);
                popupPanel.setVisible(true);
                popupContentPanel.removeAll();
                popupContentPanel.add(editProductPanel);
                
                editProductNameTextField.setText(productDetails.getName());
                editProductPriceField.setText(String.valueOf(productDetails.getPrice()));
                editProductStockField.setValue(productDetails.getStocks());
                jTextField17.setText(productDetails.getBarcode());
                
                productImage.setIcon(null);
                productImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                if(productDetails.getImage() != "" && productDetails.getImage() != null){
                    BufferedImage bufferedImage = null;
                    try {
                        String cwd = System.getProperty("user.dir");
                        bufferedImage = ImageIO.read(new File(cwd + productDetails.getImage()));
//                        bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
                        if(bufferedImage != null){
//                            Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
                            Image scaledImage = bufferedImage.getScaledInstance(-1, productImage.getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon image = new ImageIcon(scaledImage);
                            productImage.setIcon(image);
                        }
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
                
                //category
                CategoryService categoryService = new CategoryService();
        
                List<String> type = new ArrayList();
                List<String> brand = new ArrayList();
                
                

                categoryService.getAllCategory().forEach(e -> {
                    type.add(e.getType());
                    brand.add(e.getBrand());
                    
                    if(selectedProduct.getId() == e.getProduct_id()){
                        currentType = e.getType();
                        currentBrand = e.getBrand();
                    }
                });

                List<String> sortedType = removeDuplicates(type);

                editProductType.removeAllItems();
                sortedType.forEach(e ->{
                    editProductType.addItem(e);
                    if(currentType.equals(e)){
                        editProductType.setSelectedItem(e);
                    }
                });
                
                List<String> sortedBrand = removeDuplicates(brand);
                editProductBrand.removeAllItems();
                sortedBrand.forEach(e ->{
                    editProductBrand.addItem(e);
                    if(currentBrand.equals(e)){
                        editProductBrand.setSelectedItem(e);
                    }
                });
                
                
                jProgressBar1.setVisible(false);
                
                
                if(productDetails.getStatus().equals("active")){
                    jButton41.setVisible(false);
                    jButton36.setVisible(true);
                }else{
                    jButton41.setVisible(true);
                    jButton36.setVisible(false);
                }
                
                updateGraphics();
                
            }
        });
        
        jLabel129.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                ProductService ps = new ProductService();
                if(productDetails.getFavorite() == 0){
                    FlatSVGIcon favicon = new FlatSVGIcon("img/icon/heart-solid.svg", 16, 16);
                    favicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                        public Color apply(Color t){
                            return new Color(255, 79, 79);
                        }
                    }));
                    jLabel129.setIcon(favicon);
                    favoriteCount++;

                    productDetails.setFavorite(1);
                    ps.updateProduct(productDetails.getId(), productDetails);
                }else{
                    FlatSVGIcon favicon = new FlatSVGIcon("img/icon/heart-regular.svg", 16, 16);
                    favicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                        public Color apply(Color t){
                            return new Color(84, 84, 84);
                        }
                    }));
                    jLabel129.setIcon(favicon);
                    favoriteCount--;
                    
                    productDetails.setFavorite(0);
                    ps.updateProduct(productDetails.getId(), productDetails);
                }
                jLabel215.setText( String.valueOf(favoriteCount) + " item");
                jLabel199.setText( String.valueOf(favoriteCount) + " item");
            }
        });
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e) {
                isHover = false;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHover = true;
                repaint();
            }
            
        });
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jButton22.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> tempList = new ArrayList<>();
                VariantService vService = new VariantService();
                Variants tempVariants = new Variants();

                List<Variants> variants = vService.getProductVariants(productDetails.getId());


//                viewSelectedVariant.forEach((k,e2)->{
//                    tempList.add(e2);
//                });
                
//                String str = String.join("/", tempList);
                variants.forEach(e2->{
                    if(e2.getMainVariant() == 0){
//                        if(e2.getName().equals(str)){
//                            tempVariants.setPrice(e2.getPrice());
//                        }
                        tempVariants.setName(e2.getName());
                        tempVariants.setPrice(e2.getPrice());
                        return;
                    }
                });

                tempVariants.setId(productDetails.getId());
//                tempVariants.setName(str);
                tempVariants.setStocks((int) jSpinner5.getValue());
                if(checkoutProduct.containsKey(productDetails.getId())){
                    List<Variants> tempVar = new ArrayList<>();
                    tempVar = checkoutProduct.get(productDetails.getId());
                    tempVar.add(tempVariants);
                    checkoutProduct.put(productDetails.getId(), tempVar);
                }else{
                    List<Variants> tempVar = new ArrayList<>();
                    tempVar.add(tempVariants);
                    checkoutProduct.put(productDetails.getId(), tempVar);
                }
                
                System.out.println(checkoutProduct);
            }
            
        });
        
    }
    void refreshSpinner(){
        this.remove(jSpinner5);
        this.add(jSpinner5);
    }
    void showPopup(){
        JFrame frame = (JFrame)getTopLevelAncestor();
        popupPanel.setVisible(true);

        if(jCheckBoxMenuItem1.isSelected()){
            popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    popupPanel.setBounds(popupPanel.getX() - 60, 0, frame.getWidth(), frame.getHeight());

                    if(popupPanel.getX() < 0){
                        popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                        timer.stop();
                    }
                }
            });
            timer.start();
        }else{
            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        }
    }
}
