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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Product;
import pos_timesquare.model.Variants;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import static pos_timesquare.view.MainFrame.addProductVariants;
import static pos_timesquare.view.MainFrame.barcodeFrame;
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
import static pos_timesquare.view.MainFrame.jLabel12;
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
import static pos_timesquare.view.MainFrame.productMainMenu;
import static pos_timesquare.view.MainFrame.productVariants;
import static pos_timesquare.view.MainFrame.selectedFile;
import static pos_timesquare.view.MainFrame.selectedMenu;
import static pos_timesquare.view.MainFrame.selectedProduct;
import static pos_timesquare.view.MainFrame.selectedProductPrice;
import static pos_timesquare.view.MainFrame.tabPanel;
import static pos_timesquare.view.MainFrame.ticketMainMenu;
import static pos_timesquare.view.MainFrame.ticketMainPanelHeader;
import static pos_timesquare.view.MainFrame.updateGraphics;
import static pos_timesquare.view.MainFrame.viewProductName;
import static pos_timesquare.view.MainFrame.viewProductPanel;
import static pos_timesquare.view.MainFrame.viewProductPrice;
import static pos_timesquare.view.MainFrame.viewProductSelectedVariants;
import static pos_timesquare.view.MainFrame.viewProductVariantTable;
import static pos_timesquare.view.MainFrame.viewSelectedVariant;

/**
 *
 * @author Acer
 */
public class BarcodeResultThumb extends JPanel{
    
    JPanel jPanel213 = new JPanel();
    JLabel jLabel233 = new JLabel();
    JLabel jLabel237 = new JLabel();
    
    String name;
    
    String currentType;
    String currentBrand;
    Timer timer;
    
    JFrame frame;
    

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        jLabel233.setText(name);

    }

    public String getBarcode() {
        return barcode;
        
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
        jLabel237.setText(barcode);
    }
    String barcode;
    Product product = new Product();

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        if(darkRB.isSelected()){
            g2.setColor(new Color(49, 52, 58));
            g2.fillRoundRect(0, 2, this.getWidth()-1, this.getHeight()-5, 15, 15);

            g2.setColor(new Color(71,71,71));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 2, this.getWidth()-1, this.getHeight()-5, 15, 15);
        }else{
            g2.setColor(new Color(246, 246, 246));
            g2.fillRoundRect(0, 2, this.getWidth()-1, this.getHeight()-5, 15, 15);

            g2.setColor(new Color(137, 137, 137));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 2, this.getWidth()-1, this.getHeight()-5, 15, 15);
        }
    }

    
    public BarcodeResultThumb(){
        this.setOpaque(false);

        jLabel233.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel233.setText("Casio ");

        jLabel237.setText("2019600154");

        javax.swing.GroupLayout jPanel213Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel213Layout);
        jPanel213Layout.setHorizontalGroup(
            jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel213Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel233)
                    .addComponent(jLabel237))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel213Layout.setVerticalGroup(
            jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel213Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel233)
                .addGap(0, 0, 0)
                .addComponent(jLabel237)
                .addGap(12, 12, 12))
        );
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                barcodeFrame.setVisible(false);
//                if(selectedMenu == productMainMenu){
//                    showDetails();
//                }else {
                    showTicketOptions();
//                }
            }
            
        });
    }
    
    void showDetails(){
        showPopup();
        jProgressBar1.setVisible(true);
        selectedProduct = product;
        addProductVariants.clear();
        productVariants.clear();
        System.out.println(addProductVariants);

        selectedFile = null;

        tabPanel.add(ticketMainPanelHeader);

        VariantService service = new VariantService();
        List<String> list = new ArrayList<>();
        service.getProductVariants(product.getId()).forEach(e -> {
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

        editProductNameTextField.setText(product.getName());
        editProductPriceField.setText(String.valueOf(product.getPrice()));
        editProductStockField.setValue(product.getStocks());

        productImage.setIcon(null);
        productImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        if(product.getImage() != "" && product.getImage() != null){
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(this.getClass().getResource(product.getImage()));
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
        
        jProgressBar1.setVisible(false);
        updateGraphics();
    }
    
    
    void showTicketOptions(){
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

        selectedProduct = product;

        ///checkout thumb
        productImage1.setIcon(null);
        productImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                if(product.getImage() != "" && product.getImage() != null){
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(this.getClass().getResource(product.getImage()));
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


        viewProductName.setText(product.getName());
        jSpinner4.setValue(1);
//                checkoutProduct.forEach((k, e)->{
//                    if(k == productDetails.getId()){
//                        jSpinner4.setValue(e.getStocks());
//                    }
//                });
        jLabel65.setText(product.getName());
        viewProductPrice.setText(String.valueOf(product.getPrice()));

        jLabel12.setText(String.valueOf(product.getPrice()));
        viewProductSelectedVariants.setText("");
        selectedProductPrice = product.getPrice();


        jPanel11.removeAll();

        VariantService vService = new VariantService();
        vService.getProductVariants(product.getId()).forEach(e -> {
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
    
    
    void showPopup(){
//        JFrame frame = (JFrame)getTopLevelAncestor();
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
