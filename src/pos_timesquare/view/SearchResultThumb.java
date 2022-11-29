/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Customer;
import pos_timesquare.model.Product;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.User;
import pos_timesquare.model.Variants;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import static pos_timesquare.view.MainFrame.addProductVariants;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.checkoutProduct;
import static pos_timesquare.view.MainFrame.customerViewDetailsPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.editProductBrand;
import static pos_timesquare.view.MainFrame.editProductNameTextField;
import static pos_timesquare.view.MainFrame.editProductPanel;
import static pos_timesquare.view.MainFrame.editProductPriceField;
import static pos_timesquare.view.MainFrame.editProductStockField;
import static pos_timesquare.view.MainFrame.editProductType;
import static pos_timesquare.view.MainFrame.jButton14;
import static pos_timesquare.view.MainFrame.jButton15;
import static pos_timesquare.view.MainFrame.jButton16;
import static pos_timesquare.view.MainFrame.jButton22;
import static pos_timesquare.view.MainFrame.jButton36;
import static pos_timesquare.view.MainFrame.jButton39;
import static pos_timesquare.view.MainFrame.jButton40;
import static pos_timesquare.view.MainFrame.jButton41;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jDateChooser4;
import static pos_timesquare.view.MainFrame.jLabel129;
import static pos_timesquare.view.MainFrame.jLabel130;
import static pos_timesquare.view.MainFrame.jLabel131;
import static pos_timesquare.view.MainFrame.jLabel233;
import static pos_timesquare.view.MainFrame.jLabel237;
import static pos_timesquare.view.MainFrame.jLabel274;
import static pos_timesquare.view.MainFrame.jLabel275;
import static pos_timesquare.view.MainFrame.jLabel279;
import static pos_timesquare.view.MainFrame.jLabel280;
import static pos_timesquare.view.MainFrame.jLabel281;
import static pos_timesquare.view.MainFrame.jLabel283;
import static pos_timesquare.view.MainFrame.jLabel284;
import static pos_timesquare.view.MainFrame.jLabel291;
import static pos_timesquare.view.MainFrame.jLabel69;
import static pos_timesquare.view.MainFrame.jLabel71;
import static pos_timesquare.view.MainFrame.jLabel76;
import static pos_timesquare.view.MainFrame.jPanel11;
import static pos_timesquare.view.MainFrame.jPanel46;
import static pos_timesquare.view.MainFrame.jPanel47;
import static pos_timesquare.view.MainFrame.jPanel86;
import static pos_timesquare.view.MainFrame.jPanel90;
import static pos_timesquare.view.MainFrame.jPanel91;
import static pos_timesquare.view.MainFrame.jPanel97;
import static pos_timesquare.view.MainFrame.jProgressBar1;
import static pos_timesquare.view.MainFrame.jRadioButton7;
import static pos_timesquare.view.MainFrame.jRadioButton8;
import static pos_timesquare.view.MainFrame.jSpinner4;
import static pos_timesquare.view.MainFrame.jTextField11;
import static pos_timesquare.view.MainFrame.jTextField17;
import static pos_timesquare.view.MainFrame.personnelViewDetailsPanel;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.productImage;
import static pos_timesquare.view.MainFrame.productImage1;
import static pos_timesquare.view.MainFrame.productVariants;
import static pos_timesquare.view.MainFrame.searchPanel;
import static pos_timesquare.view.MainFrame.searchPanel2;
import static pos_timesquare.view.MainFrame.selectedFile;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.selectedUser;
import static pos_timesquare.view.MainFrame.tabPanel;
import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
import static pos_timesquare.view.MainFrame.updateGraphics;
import static pos_timesquare.view.MainFrame.viewProductName;
import static pos_timesquare.view.MainFrame.viewProductPanel;
import static pos_timesquare.view.MainFrame.viewProductVariantTable;
import static pos_timesquare.view.MainFrame.viewSelectedVariant;
import static pos_timesquare.view.MainFrame.jTextField18;
import static pos_timesquare.view.MainFrame.jTextField25;
import static pos_timesquare.view.MainFrame.jTextField26;
import static pos_timesquare.view.MainFrame.jTextField8;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.selectedCustomer;

/**
 *
 * @author Acer
 */
public class SearchResultThumb extends javax.swing.JPanel {

    /**
     * Creates new form SearchResultThumb
     */
    boolean isHover = false;
    Product productDetails;
    User userData;
    Customer customer;
    
    boolean isAdmin = false;
    
    int type = 0;
    
    String currentType;
    String currentBrand;
    
    float totalOrders;
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(darkRB.isSelected()){
//            g2.setColor(new Color(38, 41, 48));
//        }else{
//            g2.setColor(new Color(255, 255, 255));
//        }
        if(isHover){
            if(darkRB.isSelected()){
                g2.setColor(new Color(38, 41, 48));
            }else{
                g2.setColor(new Color(240,240,240));
            }
            g2.fillRect(0, 0, this.getWidth(), this.getHeight()-1);
            g2.setColor(new Color(0,144,228));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(2, 4, 2, this.getHeight() - 7);
        }

    }
    
    public SearchResultThumb() {
        initComponents();
    }

    public void setData(Product product, int type){
        productDetails = product;
        this.type = type;
        
        jLabel1.setText(product.getName());
    }
    public void setData(User user, int type){
        userData = user;
        this.type = type;
        
        jLabel1.setText(user.getName());
    }
    
    public void setData(Customer customer, int type){
        this.customer = customer;
        this.type = type;
        if(customer.getName().equals("")){
            jLabel1.setText("Customer #"+customer.getId());
        }else{
            jLabel1.setText(customer.getName());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(32767, 40));
        setOpaque(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        jLabel1.setText("Product Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
        isHover = true;
        repaint();
        revalidate();
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
        isHover = false;
        repaint();
        revalidate();
    }//GEN-LAST:event_formMouseExited

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        if(type == 0){
            searchPanel.setVisible(false);

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
                            bufferedImage = ImageIO.read(this.getClass().getResource(productDetails.getImage()));
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
//            jSpinner4.setValue(1);
//            checkoutProduct.forEach((k, e)->{
//                if(k == productDetails.getId()){
//                    jSpinner4.setValue(e.getStocks());
//                }
//            });


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

            updateGraphics();
        }else if(type == 1){
            
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
        
        }else if(type == 2){
//            jTextField18.setFocusable(false);
//            jTextField18.setFocusable(true);
            
//            jTextField18.setText(userData.getName());
            
            searchPanel2.setVisible(false);
            
            showPopup();
            jTextField18.setText(userData.getName());
            
            popupContentPanel.removeAll();
            popupContentPanel.add(personnelViewDetailsPanel);

            selectedUser = userData;


            jLabel130.setText(userData.getName());
            jLabel279.setText(userData.getAddress());
            jLabel280.setText(userData.getGender());
            jLabel71.setText(userData.getContactNum());

            DateFormat formater = new SimpleDateFormat("MMMM d, yyyy");
            java.sql.Date sqldate = new java.sql.Date( userData.getBirthdate().getTime() );
            String birthdate = formater.format(sqldate);
            jLabel69.setText(birthdate);


            long millis = System.currentTimeMillis();        
            java.sql.Date currentDate = new java.sql.Date(millis);        
            LocalDate date = Instant.ofEpochMilli(userData.getBirthdate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();        

            jLabel281.setText(String.valueOf(Period.between(date, currentDate.toLocalDate()).getYears()));

            if(userData.getStatus() == 0){
                FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
                pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                    public Color apply(Color t) {
                        return new Color(249, 92, 92);
                    }
                }));
                jLabel274.setIcon(pnegativeicon);
                jLabel275.setText("Deactivated");

                jButton16.setText("Activate");
            }else if(userData.getStatus() == 2){
                FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
                pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                    public Color apply(Color t) {
                        return new Color(168, 168, 168);
                    }
                }));
                jLabel274.setIcon(pnegativeicon);
                jLabel275.setText("Deleted");

                jButton16.setText("Activate");

            }else{
                FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/check_circle_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
                pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                    public Color apply(Color t) {
                        return new Color(77, 213, 151);
                    }
                }));
                jLabel274.setIcon(pnegativeicon);
                jLabel275.setText("Active");

                jButton16.setText("Deactivate");
            }

            jLabel283.setText(String.valueOf(userData.getHourWorked()));

            ReceiptService rs = new ReceiptService();
            List<Receipt> personSales = rs.getReceiptBySalesPersonId(userData.getId());
            jLabel284.setText(String.valueOf(personSales.size()));


            try {
                DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
                Date date3;
                date3 = format.parse(userData.getMembershipDate());

                String membershipDate = format2.format(date3);
                jLabel291.setText(membershipDate); 
            } catch (ParseException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

//                jLabel291.setText(userData.getMembershipDate());


            if(userData.getRole().equals("admin")){
                jLabel131.setForeground(new java.awt.Color(255, 151, 55));
                jLabel131.setText("Admin");
                isAdmin = true;

                jButton15.setText("Set as Employee");
            }else{
                jLabel131.setForeground(new java.awt.Color(0, 102, 255));
                jLabel131.setText("Employee");
                isAdmin = false;

                jButton15.setText("Set as Admin");
            }

//                jLabel131.setText();

            jPanel90.removeAll();


            rs.getReceiptBySalesPersonId(userData.getId()).forEach(e2 -> {
                Receipt receiptData = rs.getReceiptById(e2.getId());
                ReceiptDataThumb rdt = new ReceiptDataThumb();

                rdt.setReceiptData(receiptData, false);
                jPanel90.add(rdt);
            });
            jLabel129.setIcon(null);
            if(userData.getImage() != null && !userData.getImage().equals("")){
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(getClass().getResource(userData.getImage()));
                        } catch (IOException ex) {

                        }
                        Image scaledImage;
                        if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                            scaledImage = bufferedImage.getScaledInstance(190, -1, Image.SCALE_SMOOTH);
                        }else{
                            scaledImage = bufferedImage.getScaledInstance(-1, 190, Image.SCALE_SMOOTH);
                        }

                        ImageIcon imageIcon = new ImageIcon(scaledImage);

                        jLabel129.setIcon(imageIcon);

                    }
                });
                t.start();
            }
        }else if(type == 3){
//            jTextField25.setFocusable(false);
//            jTextField25.setFocusable(true);
            
//            jTextField25.setText(customer.getName());
            
            showPopup();
            jTextField25.setText(customer.getName());
                
            popupContentPanel.removeAll();
            popupContentPanel.add(customerViewDetailsPanel);

            selectedCustomer = customer;

            jLabel233.setIcon(null);
            jLabel237.setText(customer.getName());
//                jLabel318.setText(customer.getAddress());
//                jLabel319.setText(customer.getGender());
//                jLabel320.setText(customer.getBirthDate());

            jTextField11.setText(customer.getName());
            jTextField8.setText(customer.getAddress());
//                jTextField9.setText(customer.getGender());
//                jTextField10.setText(customer.getBirthDate());
            jTextField26.setText(customer.getContactNum());

            if(customer.getGender() != null){
                if(customer.getGender().equals("male")){
                    jRadioButton7.setSelected(true);
                }else{
                    jRadioButton8.setSelected(true);
                }
            }

            if(customer.getBirthDate() != null){
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
                    Date date3;
                    date3 = format.parse(customer.getBirthDate());

                    jDateChooser4.setDate(date3);
                } catch (ParseException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }



            jTextField8.setEnabled(false);
//                jTextField9.setFocusable(false);
//                jTextField10.setFocusable(false);
            jTextField26.setEnabled(false);
            jDateChooser4.setEnabled(false);
            jRadioButton7.setEnabled(false);
            jRadioButton8.setEnabled(false);

            jLabel237.setVisible(!false);
//                jLabel318.setVisible(!false);
//                jLabel319.setVisible(!false);
//                jLabel320.setVisible(!false);

            jTextField11.setVisible(false);
//                jTextField8.setVisible(false);
//                jTextField9.setVisible(false);
//                jTextField10.setVisible(false);

            if(customer.getBirthDate() != null){
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
                    Date date3;
                    date3 = format.parse(customer.getBirthDate());

//                    String membershipDate = format2.format(date3);

                    long millis = System.currentTimeMillis();        
                    java.sql.Date currentDate = new java.sql.Date(millis);        

                    LocalDate date = Instant.ofEpochMilli(date3.getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    jLabel76.setText(String.valueOf(Period.between(date, currentDate.toLocalDate()).getYears()));


                } catch (ParseException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }



            jButton14.setVisible(!false);
            jButton39.setVisible(!true);

            if(customer.getImage() != null && !customer.getImage().equals("")){
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(getClass().getResource(customer.getImage()));
                        } catch (IOException ex) {

                        }
                        Image scaledImage;
                        if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                            scaledImage = bufferedImage.getScaledInstance(190, -1, Image.SCALE_SMOOTH);
                        }else{
                            scaledImage = bufferedImage.getScaledInstance(-1, 190, Image.SCALE_SMOOTH);
                        }

                        ImageIcon imageIcon = new ImageIcon(scaledImage);

                        jLabel233.setIcon(imageIcon);

                    }
                });
                t.start();
            }

            jButton40.setVisible(true);
            if(customer.getStatus().equals("Blocked")){
                jPanel97.setVisible(true);
//                    jButton22.setVisible(false);
//                    jButton42.setVisible(true);
                jButton22.setText("Activate");
            }else if(customer.getStatus().equals("Deleted")){
                jButton40.setVisible(false);
//                    jButton42.setVisible(true);
                jButton22.setText("Activate");
            }else{
                jPanel97.setVisible(false);
//                    jButton22.setVisible(true);
//                    jButton42.setVisible(false);
            }

//                if(customer.getStatus().equals("Deleted")){
//                    jButton40.setVisible(false);
//                    jButton42.setVisible(true);
//                }else{
//                    jButton40.setVisible(true);
//                    jButton42.setVisible(false);
//                }

            ReceiptService rs = new ReceiptService();

            TransactionHistoryService ths = new TransactionHistoryService();
            HashMap<String, Integer> category = new HashMap<>();

            ProductService ps = new ProductService();
            VariantService vs = new VariantService();
            CategoryService cs = new CategoryService();

            jPanel91.removeAll();
            List<Receipt> receiptList = rs.getReceiptByCustomerId(customer.getId());
            receiptList.forEach(e2 -> {
                Receipt receiptData = rs.getReceiptById(e2.getId());
                ReceiptDataThumb rdt = new ReceiptDataThumb();

                rdt.setReceiptData(receiptData, true);
                jPanel91.add(rdt);

                //Order Percent
                ths.getTransactionByReceiptId(e2.getId()).forEach(e3 -> {


                    cs.getCategoryByProductId(e3.getProductId()).forEach(e4 -> {
                        System.out.println("Category Receipt: "+e4.getType());
                        if(category.containsKey(e4.getType())){
                            category.put(e4.getType(), e3.getOrders());
                        }else{
                            category.put(e4.getType(), e3.getOrders());
                        }

                    });

                });
            });


            jPanel86.removeAll();
            totalOrders = 0;
            category.forEach((k, e2)->{
                totalOrders = totalOrders + e2;
            });

            category.forEach((k, e2)->{
                OrderedProduct orderedProduct = new OrderedProduct();
                orderedProduct.setData(k, ((float)e2 / (float)totalOrders) * 100);
//                    System.out.println( e2 + " " + totalOrders);
//                    System.out.println("TOtal " +  ((double)e2 / (double)totalOrders) * 100);
                jPanel86.add(orderedProduct);
            });
        }
        
    }//GEN-LAST:event_formMouseClicked

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
    
    class OrderedProduct extends JPanel{
        
        JPanel jPanel85 = new JPanel();
        
        JLabel jLabel26 = new JLabel();
        JProgressBar jProgressBar3 = new JProgressBar();
        
        void setData(String name, float value){
            jLabel26.setText(name);
            jProgressBar3.setValue(Math.round(value));
        }
        
        OrderedProduct(){
            this.setOpaque(false);
            
            javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(this);
            this.setLayout(jPanel85Layout);
            jPanel85Layout.setHorizontalGroup(
                jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel85Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel26)
                        .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(120, Short.MAX_VALUE))
            );
            jPanel85Layout.setVerticalGroup(
                jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel26)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10))
            );
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
