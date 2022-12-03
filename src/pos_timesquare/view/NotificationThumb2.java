/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pos_timesquare.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
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
public class NotificationThumb2 extends javax.swing.JPanel {

    /**
     * Creates new form NotificationThumb2
     */
    
    Notification notification = new Notification();
    
    String currentType;
    String currentBrand;
    
    JFrame mainFrame;
    
    public void setLimitedSize(){
        this.setPreferredSize(new Dimension(238, 51));
        this.setMaximumSize(new Dimension(238, 51));
        jLabel189.setPreferredSize(new Dimension(100, 25));
        jLabel189.setMaximumSize(new Dimension(50, 25));
        jLabel189.setMinimumSize(new Dimension(50, 25));
        
        jLabel188.setPreferredSize(new Dimension(100, 14));
        jLabel188.setMaximumSize(new Dimension(100, 14));
        jLabel188.setMinimumSize(new Dimension(100, 14));
    }
    
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification, JFrame frame) {
        this.notification = notification;
        mainFrame= frame;
        
        jLabel188.setText(notification.getTitle());
        
        if(notification.getStatus() == 0){
            jPanel1.setVisible(true);
        }else{
            jPanel1.setVisible(false);
        }
        
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
            Logger.getLogger(NotificationThumb2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(darkRB.isSelected()){
            g2.setColor(new Color(50, 54, 62));
        }else{
            g2.setColor(new Color(235, 235, 235));
        }
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
    }
    
    
    
    public NotificationThumb2() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel189 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        jPanel1 =
        new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(25, 151, 203));
                }else{
                    g2.setColor(new Color(25, 151, 203));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }

        };
        jLabel1 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32767, 53));
        setPreferredSize(new java.awt.Dimension(261, 51));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jLabel189.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel189.setText("10/30/22 | 23:00");

        jLabel188.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel188.setText("Product out of stocks!");

        jPanel1.setPreferredSize(new java.awt.Dimension(35, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("new");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel189, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel188, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel188, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel189)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
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
    }//GEN-LAST:event_formMouseClicked
    Timer timer;
    void showPopup(){
//        JFrame frame = (JFrame)getTopLevelAncestor();
        
        
        
        mainFrame.requestFocusInWindow();
        
        popupPanel.setVisible(true);
        blurBGPanel.setVisible(true);

        if(jCheckBoxMenuItem1.isSelected()){
            popupPanel.setBounds(mainFrame.getWidth(), 0, mainFrame.getWidth(), mainFrame.getHeight());
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    popupPanel.setBounds(popupPanel.getX() - 60, 0, mainFrame.getWidth(), mainFrame.getHeight());

                    if(popupPanel.getX() < 0){
                        popupPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
                        timer.stop();
                    }
                }
            });
            timer.start();
        }else{
            popupPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
