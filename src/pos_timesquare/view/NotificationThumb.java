/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Notification;
import pos_timesquare.model.Product;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import static pos_timesquare.view.MainFrame.addProductVariants;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.editProductBrand;
import static pos_timesquare.view.MainFrame.editProductNameTextField;
import static pos_timesquare.view.MainFrame.editProductPanel;
import static pos_timesquare.view.MainFrame.editProductPriceField;
import static pos_timesquare.view.MainFrame.editProductStockField;
import static pos_timesquare.view.MainFrame.editProductType;
import static pos_timesquare.view.MainFrame.jButton36;
import static pos_timesquare.view.MainFrame.jButton41;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jPanel46;
import static pos_timesquare.view.MainFrame.jPanel47;
import static pos_timesquare.view.MainFrame.jProgressBar1;
import static pos_timesquare.view.MainFrame.jTextField17;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.productImage;
import static pos_timesquare.view.MainFrame.productVariants;
import static pos_timesquare.view.MainFrame.searchPanel;
import static pos_timesquare.view.MainFrame.selectedFile;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.tabPanel;
import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class NotificationThumb extends JPanel{
    
    JLabel jLabel188 = new JLabel();
    JLabel jLabel189 = new JLabel();
    
    Notification notification = new Notification();
    
    String currentType;
    String currentBrand;
    
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
        
        jLabel188.setText(notification.getTitle());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(notification.getDate());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    
            DateFormat dateformatter = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateformatter.format(new Date(timestamp.getTime()));
            
            DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
            String time = timeformatter.format(new Date(timestamp.getTime()));
            
            jLabel189.setText(date +" | "+ time);
            
            System.out.println(timestamp);
        } catch (ParseException ex) {
            Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(darkRB.isSelected()){
            g2.setColor(new Color(58, 61, 68));
        }else{
            g2.setColor(new Color(215, 215, 215));
        }
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
    }

    
    public NotificationThumb(){
        this.setMaximumSize(new java.awt.Dimension(30000, 61));
        this.setMinimumSize(new java.awt.Dimension(246, 61));

        jLabel188.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel188.setText("Product out of stocks!");
        jLabel188.setMaximumSize(new java.awt.Dimension(30000, 20));

        jLabel189.setText("10/30/22 | 23:00");

        javax.swing.GroupLayout jPanel163Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel163Layout);
        jPanel163Layout.setHorizontalGroup(
            jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel163Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel189))
                .addContainerGap())
        );
        jPanel163Layout.setVerticalGroup(
            jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel163Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel189)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
                if(notification.getProductId() != 0){
                    ProductService ps = new ProductService();
                    Product productDetails = ps.getProductById(notification.getProductId());
                    
                    searchPanel.setVisible(false);
            
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
                            bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
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

                    if(productDetails.getStatus().equals("active")){
                        jButton41.setVisible(false);
                        jButton36.setVisible(true);
                    }else{
                        jButton41.setVisible(true);
                        jButton36.setVisible(false);
                    }

                    jProgressBar1.setVisible(false);
                    updateGraphics();
                    
                }
            }
        });
        
    }
    Timer timer;
    void showPopup(){
        JFrame frame = (JFrame)getTopLevelAncestor();
        frame.requestFocusInWindow();
        
        popupPanel.setVisible(true);
        blurBGPanel.setVisible(true);

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
