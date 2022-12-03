/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pos_timesquare.controller.CustomerService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.controller.UserService;
import pos_timesquare.model.Customer;
import pos_timesquare.model.Product;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.TransactionHistory;
import pos_timesquare.model.User;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class ReceiptDataThumb extends JPanel{
    
    JPanel jPanel216 = new JPanel();
    JPanel jPanel50 = new JPanel();
    JPanel jPanel57 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(new Color(50, 54, 62));
            }else{
                g2.setColor(new Color(230, 230, 230));
            }
            g2.drawLine(1, 1, this.getWidth()-1, 1);
        }

    };
    JPanel jPanel58 = new JPanel();
    JPanel jPanel60 = new JPanel();
    JPanel jPanel59 = new JPanel();
    JPanel jPanel63 = new JPanel();
    JPanel jPanel61 = new JPanel();
    JPanel jPanel66 = new JPanel();
    JPanel jPanel67 = new JPanel();
    JPanel jPanel68 = new JPanel();
    
    
    JLabel jLabel68 = new JLabel();
    JLabel jLabel69 = new JLabel();
    JLabel jLabel70 = new JLabel();
    JLabel jLabel71 = new JLabel();
    JLabel jLabel53 = new javax.swing.JLabel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//            if(darkRB.isSelected()){
//                g2.setColor(new Color(38, 41, 48));
//            }else{
//                g2.setColor(new Color(255, 255, 255));
//            }
            g2.setColor(this.getBackground());
            for(int i = 52; i >= 0; i--){
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
            }
//            g2.setColor(Color.GRAY);
            if(darkRB.isSelected()){
                g2.setColor(new Color(58, 61, 68));
            }else{
                g2.setColor(new Color(225, 225, 225));
            }
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 52, 52);
            
//            if(isHover){
//                g2.setColor(new Color(0,144,228));
//                g2.setStroke(new BasicStroke(1));
//                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//            }
        }
    };
    
//    {
//
//        public void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(38, 41, 48));
//            }else{
//                g2.setColor(new Color(245, 245, 245));
//            }
//            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 52, 52);
//        }
//
//    };
    
    JLabel jLabel72 = new JLabel();
    JLabel jLabel77 = new JLabel();
    JLabel jLabel126 = new JLabel();
    JLabel jLabel127 = new JLabel();
    JLabel jLabel233 = new JLabel();
    JLabel jLabel73 = new JLabel();
    JLabel jLabel79 = new JLabel();
    JLabel jLabel130 = new JLabel();
    JLabel jLabel129 = new JLabel();
    
    
    JPanel jPanel62 = new JPanel();
    JLabel jLabel237 = new JLabel();
    
    JButton jButton12 = new JButton();
    JButton jButton14 = new JButton();
    
    
    
    Receipt receiptData = new Receipt();
    float totalPrice;
    float totalRefunded;
    int totalItems;
    List<Integer> totalProducts = new ArrayList<>();
    
    boolean expanded = false;
    boolean isHover = false;
    
    HashMap<Integer, TransactionHistory> selectedTransaction = new HashMap<>();
    

    public float getTotalPrice(){
        return totalPrice - totalRefunded;
    }
    
    public float getTotalRefunded(){
        return totalRefunded;
    }
    
    public Receipt getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(Receipt receiptData, boolean isForCustomer) {
        this.receiptData = receiptData;
        jLabel68.setText("Receipt #" +receiptData.getId());
        
        UserService us = new UserService();
        User user = us.getUserById(receiptData.getSalesPersonId());
        CustomerService cs = new CustomerService();
        Customer customer = cs.getCustomerById(receiptData.getCustomerId());
        
        jLabel77.setText(user.getName());
        jLabel79.setText(customer.getName());
        jLabel130.setText(receiptData.getType().substring(0, 1).toUpperCase() + receiptData.getType().substring(1));
        
        totalItems = 0;
        totalPrice = 0;
        totalRefunded = 0;
        TransactionHistoryService ths = new TransactionHistoryService();
        ths.getTransactionByReceiptId(receiptData.getId()).forEach(e -> {
            totalPrice += e.getTotalPrice();
            totalItems += e.getOrders();
            if(e.getStatus().equals("Refund")){
                totalRefunded += e.getTotalPrice();
            }
            if(totalProducts.isEmpty()){
                totalProducts.add(e.getProductId());
            }else{
                if(!totalProducts.contains(e.getProductId())){
                    totalProducts.add(e.getProductId());
                }
            }
            ReceiptOrderListThumb receiptOrderListThumb = new ReceiptOrderListThumb();
            receiptOrderListThumb.setTransaction(e);
            jPanel63.add(receiptOrderListThumb);
        });
        float price = (float)totalPrice - (float)totalRefunded;
        jLabel70.setText("Total Price: " + price);
        
        jLabel71.setText(totalProducts.size() + " products, " + totalItems + " items");
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(receiptData.getDate());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    
            DateFormat dateformatter = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateformatter.format(new Date(timestamp.getTime()));
            
            DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
            String time = timeformatter.format(new Date(timestamp.getTime()));
            
            jLabel69.setText(date +" | "+ time);
            jLabel127.setText(date +" | "+ time);
            
            System.out.println(timestamp);
        } catch (ParseException ex) {
//            Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!isForCustomer){
            
            if(customer != null){
                if(customer.getImage() != null && !customer.getImage().equals("")){
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            BufferedImage bufferedImage = null;
                            try {
                                String cwd = System.getProperty("user.dir");
                                bufferedImage = ImageIO.read(new File(cwd + customer.getImage()));
//                                bufferedImage = ImageIO.read(getClass().getResource(customer.getImage()));
                            } catch (IOException ex) {
                                Logger.getLogger(ReceiptDataThumb.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Image scaledImage;
                            if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                                scaledImage = bufferedImage.getScaledInstance(52, -1, Image.SCALE_SMOOTH);
                            }else{
                                scaledImage = bufferedImage.getScaledInstance(-1, 52, Image.SCALE_SMOOTH);
                            }

                            ImageIcon imageIcon = new ImageIcon(scaledImage);

                            jLabel53.setIcon(imageIcon);

                        }
                    });
                    t.start();
                }
            }
        }else{
            if(user.getImage() != null && !user.getImage().equals("")){
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        BufferedImage bufferedImage = null;
                        try {
                            String cwd = System.getProperty("user.dir");
                            bufferedImage = ImageIO.read(new File(cwd + user.getImage()));
//                            bufferedImage = ImageIO.read(getClass().getResource(user.getImage()));
                        } catch (IOException ex) {

                        }
                        Image scaledImage;
                        if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                            scaledImage = bufferedImage.getScaledInstance(52, -1, Image.SCALE_SMOOTH);
                        }else{
                            scaledImage = bufferedImage.getScaledInstance(-1, 52, Image.SCALE_SMOOTH);
                        }

                        ImageIcon imageIcon = new ImageIcon(scaledImage);

                        jLabel53.setIcon(imageIcon);

                    }
                });
                t.start();
            }
        }
        
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if(darkRB.isSelected()){
//            g2.setColor(new Color(38, 41, 48));
//        }else{
//            g2.setColor(new Color(245, 245, 245));
//        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(expanded){
            if(darkRB.isSelected()){
                g2.setColor(new Color(50, 54, 62));
            }else{
                g2.setColor(new Color(230, 230, 230));
            }
            g2.drawRoundRect(0, 1, this.getWidth()-1, this.getHeight()-3, 15, 15);
        }
        
        if(isHover){
            g2.setColor(new java.awt.Color(0, 144, 228));
            g2.drawRoundRect(0, 1, this.getWidth()-1, this.getHeight()-3, 15, 15);
        }
    }
    
    class ReceiptOrderListThumb extends JPanel{
        JPanel jPanel64 = new JPanel();
        JPanel jPanel65 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                if(darkRB.isSelected()){
                    if(isSold){
                        g2.setColor(new Color(0, 148, 255, 20));
                    }else{
                        g2.setColor(new Color(210, 210, 210, 20));
                    }

                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), this.getHeight(), this.getHeight());

                }else{
                    if(isSold){
                        g2.setColor(new Color(0, 148, 255, 20));
                    }else{
                        g2.setColor(new Color(0, 0, 0, 20));
                    }
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-1, this.getHeight(), this.getHeight());
                }

            }

        };
        
        JLabel jLabel78 = new JLabel();
        JLabel jLabel125 = new JLabel();
        JLabel jLabel128 = new JLabel();
        
        JCheckBox jCheckBox8 = new JCheckBox();
        
        TransactionHistory transaction;
        
        boolean isSold = true;

        public TransactionHistory getTransaction() {
            return transaction;
        }

        public void setTransaction(TransactionHistory transaction) {
            this.transaction = transaction;
            
            ProductService ps = new ProductService();
            
            Product product = ps.getProductById(transaction.getProductId());
            
            if(transaction.getVariantId() != null){
                jLabel78.setText(product.getName()+"("+transaction.getVariantId()+")");
            }else{
                jLabel78.setText(product.getName());
            }
            
            jLabel125.setText(String.valueOf(transaction.getTotalPrice()));
            
            if(transaction.getStatus().equals("Sold")){
                
                jLabel128.setText("Sold");
                isSold = true;
            }else{
                jLabel128.setText("Refunded");
                isSold = false;
            }
            
            if(selectedTransaction.containsKey(transaction.getId())){
                jCheckBox8.setSelected(true);
            }
            
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(new Color(38, 41, 48));
            }else{
                g2.setColor(new Color(248, 248, 248));
            }
            g2.fillRoundRect(0, 1, this.getWidth()-1, this.getHeight()-3, 15, 15);
        }

        
        public ReceiptOrderListThumb(){
            this.setMaximumSize(new java.awt.Dimension(32767, 54));
            this.setMinimumSize(new java.awt.Dimension(0, 54));
            this.setOpaque(false);
            this.setPreferredSize(new java.awt.Dimension(898, 50));

            
            jLabel125.setPreferredSize(new Dimension(150, 16));
            
            jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            jLabel78.setText("Product Name");

            jLabel125.setText("Price");

            jPanel65.setOpaque(false);

            jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel128.setText("Sold");

            javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
            jPanel65.setLayout(jPanel65Layout);
            jPanel65Layout.setHorizontalGroup(
                jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
            );
            jPanel65Layout.setVerticalGroup(
                jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel65Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
            );

            javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(this);
            this.setLayout(jPanel64Layout);
            jPanel64Layout.setHorizontalGroup(
                jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel64Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jCheckBox8)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel78)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 485, Short.MAX_VALUE)
                    .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(67, 67, 67)
                    .addComponent(jLabel125)
                    .addGap(25, 25, 25))
            );
            jPanel64Layout.setVerticalGroup(
                jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel64Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel64Layout.createSequentialGroup()
                            .addGap(0, 11, Short.MAX_VALUE)
                            .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel125)))
                            .addGap(0, 11, Short.MAX_VALUE))
                        .addComponent(jPanel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            
            jCheckBox8.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!selectedTransaction.containsKey(transaction.getId())){
                        selectedTransaction.put(transaction.getId(), transaction);
                    }else{
                        selectedTransaction.remove(transaction.getId());
                    }
                    
                    if(selectedTransaction.isEmpty()){
                        jPanel62.setVisible(false);
                    }else{
                        jLabel237.setText(selectedTransaction.size() + " Selected");
                        jPanel62.setVisible(true);
                    }
                }
                
            });
            
            
            
        }
        
        
    }
    
    public ReceiptDataThumb(){
        
        jPanel57.setVisible(false);
        jPanel62.setVisible(false);
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel50.setMaximumSize(new java.awt.Dimension(32767, 68));
        jPanel50.setOpaque(false);

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel68.setText("Receipt #1");

        jLabel69.setText("January 20, 2022");

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel70.setText("Total Price: 20123");

        jLabel71.setText("2 product, 2 items");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addGap(0, 0, 0)
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        add(jPanel50);

        jPanel57.setOpaque(false);
        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel66.setOpaque(false);
        jPanel66.setRequestFocusEnabled(false);

        jPanel67.setOpaque(false);

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel73.setText("Customer");
        jLabel73.setPreferredSize(null);

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel79.setText("Paul Justine Faustino");
        jLabel79.setPreferredSize(null);

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel68.setOpaque(false);

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel129.setText("Payment Method");
        jLabel129.setPreferredSize(null);

        jLabel130.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel130.setText("Cash");
        jLabel130.setPreferredSize(null);

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel57.add(jPanel66);

        jPanel58.setOpaque(false);
        jPanel58.setRequestFocusEnabled(false);

        jPanel59.setOpaque(false);

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel72.setText("Sales Person");
        jLabel72.setPreferredSize(null);

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel77.setText("Paul Justine Faustino");
        jLabel77.setPreferredSize(null);

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel60.setOpaque(false);

        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel126.setText("Date");
        jLabel126.setPreferredSize(null);

        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel127.setText("January 20, 2022");
        jLabel127.setPreferredSize(null);

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel57.add(jPanel58);

        jPanel61.setOpaque(false);

        jPanel62.setOpaque(false);

        jButton12.setText("Refund");
        

        jButton14.setBackground(new java.awt.Color(0, 144, 228));
        jButton14.setForeground(new java.awt.Color(240, 240, 240));
        jButton14.setText("Sold");
        

        jLabel233.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel233.setText("Bulk Action");

        jLabel237.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel237.setForeground(new java.awt.Color(17, 184, 255));
        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel237.setText("2 Selected");
        jLabel237.setBorder(BorderFactory.createDashedBorder(new Color(17,184,255), 7, 7));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel233)
                .addGap(26, 26, 26)
                .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel237, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
            .addComponent(jLabel233, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel63.setOpaque(false);
        jPanel63.setLayout(new javax.swing.BoxLayout(jPanel63, javax.swing.BoxLayout.PAGE_AXIS));

        

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel57.add(jPanel61);

        add(jPanel57);                 
        
        
//        jPanel57.setVisible(false);
//        jPanel62.setVisible(false);
//        
//        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
//
//        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jPanel50.setMaximumSize(new java.awt.Dimension(32767, 68));
//        jPanel50.setOpaque(false);
//
//        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel68.setText("Receipt #1");
//
//        jLabel69.setText("January 20, 2022");
//        
//
//        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
//        jLabel70.setText("Total Price: 20123");
//
//        jLabel71.setText("2 product, 2 items");
//
//        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
//        jPanel50.setLayout(jPanel50Layout);
//        jPanel50Layout.setHorizontalGroup(
//            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel50Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jLabel71)
//                .addGap(0, 0, 0)
//                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(23, 23, 23))
//        );
//        jPanel50Layout.setVerticalGroup(
//            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel50Layout.createSequentialGroup()
//                .addGap(8, 8, 8)
//                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel50Layout.createSequentialGroup()
//                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(0, 0, 0)
//                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                    .addGroup(jPanel50Layout.createSequentialGroup()
//                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(0, 0, Short.MAX_VALUE))
//                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addGap(8, 8, 8))
//        );
//
//        this.add(jPanel50);
//
//        jPanel57.setOpaque(false);
//        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.PAGE_AXIS));
//
//        jPanel58.setOpaque(false);
//        jPanel58.setRequestFocusEnabled(false);
//
//        jPanel59.setOpaque(false);
//
//        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel72.setText("Sales Person");
//
//        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel77.setText("Paul Justine Faustino");
//
//        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
//        jPanel59.setLayout(jPanel59Layout);
//        jPanel59Layout.setHorizontalGroup(
//            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel59Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel72)
//                    .addComponent(jLabel77))
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel59Layout.setVerticalGroup(
//            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel59Layout.createSequentialGroup()
//                .addComponent(jLabel72)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel77)
//                .addContainerGap())
//        );
//
//        jPanel60.setOpaque(false);
//
//        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel126.setText("Date");
//
//        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel127.setText("January 20, 2022");
//
//        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
//        jPanel60.setLayout(jPanel60Layout);
//        jPanel60Layout.setHorizontalGroup(
//            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel60Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel126)
//                    .addComponent(jLabel127))
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel60Layout.setVerticalGroup(
//            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel60Layout.createSequentialGroup()
//                .addComponent(jLabel126)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel127)
//                .addContainerGap())
//        );
//
//        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
//        jPanel58.setLayout(jPanel58Layout);
//        jPanel58Layout.setHorizontalGroup(
//            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel58Layout.createSequentialGroup()
//                .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(0, 0, 0)
//                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel58Layout.setVerticalGroup(
//            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
//                .addGap(12, 12, 12)
//                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addContainerGap())
//        );
//
//        jPanel57.add(jPanel58);
//
//        jPanel61.setOpaque(false);
//
//        jPanel62.setOpaque(false);
//
//        jButton12.setText("Refund");
//        
//
//        jButton14.setBackground(new java.awt.Color(0, 144, 228));
//        jButton14.setForeground(new java.awt.Color(240, 240, 240));
//        jButton14.setText("Sold");
//        
//        jLabel233.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        jLabel233.setText("Bulk Action");
//
//        jLabel237.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
//        jLabel237.setForeground(new java.awt.Color(17, 184, 255));
//        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel237.setText("2 Selected");
//        jLabel237.setBorder(BorderFactory.createDashedBorder(new Color(17,184,255), 7, 7));
//
//        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
//        jPanel62.setLayout(jPanel62Layout);
//        jPanel62Layout.setHorizontalGroup(
//            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jLabel233)
//                .addGap(26, 26, 26)
//                .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(20, 20, 20))
//        );
//        jPanel62Layout.setVerticalGroup(
//            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
//                .addGap(0, 0, Short.MAX_VALUE)
//                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
//            .addGroup(jPanel62Layout.createSequentialGroup()
//                .addGap(4, 4, 4)
//                .addComponent(jLabel237, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(4, 4, 4))
//            .addComponent(jLabel233, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//
//        jPanel63.setOpaque(false);
//        jPanel63.setLayout(new javax.swing.BoxLayout(jPanel63, javax.swing.BoxLayout.PAGE_AXIS));
//
//        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
//        jPanel61.setLayout(jPanel61Layout);
//        jPanel61Layout.setHorizontalGroup(
//            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addGroup(jPanel61Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
//                .addGap(20, 20, 20))
//        );
//        jPanel61Layout.setVerticalGroup(
//            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel61Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(10, 10, 10))
//        );
//
//        jPanel57.add(jPanel61);
//
//        this.add(jPanel57);

        

//        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
//
//        jPanel50.setMaximumSize(new java.awt.Dimension(32767, 68));
//        jPanel50.setOpaque(false);
//
//        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel68.setText("Receipt #1");
//
//        jLabel69.setText("January 20, 2022");
//
//        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
//        jLabel70.setText("Total Price: 20123");
//
//        jLabel71.setText("2 product, 2 items");
//
//        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
//        jPanel50.setLayout(jPanel50Layout);
//        jPanel50Layout.setHorizontalGroup(
//            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel50Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
//                .addComponent(jLabel71)
//                .addGap(140, 140, 140)
//                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(23, 23, 23))
//        );
//        jPanel50Layout.setVerticalGroup(
//            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel50Layout.createSequentialGroup()
//                .addGap(8, 8, 8)
//                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel50Layout.createSequentialGroup()
//                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(0, 0, 0)
//                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                    .addGroup(jPanel50Layout.createSequentialGroup()
//                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(0, 0, Short.MAX_VALUE))
//                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addGap(8, 8, 8))
//        );
//
//        this.add(jPanel50);
//
//        jPanel57.setOpaque(false);
//        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.PAGE_AXIS));
//
//        jPanel58.setOpaque(false);
//        jPanel58.setRequestFocusEnabled(false);
//
//        jPanel59.setOpaque(false);
//
//        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel72.setText("Sales Person");
//
//        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel77.setText("Paul Justine Faustino");
//
//        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
//        jPanel59.setLayout(jPanel59Layout);
//        jPanel59Layout.setHorizontalGroup(
//            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel59Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel72)
//                    .addComponent(jLabel77))
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel59Layout.setVerticalGroup(
//            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel59Layout.createSequentialGroup()
//                .addComponent(jLabel72)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel77)
//                .addContainerGap())
//        );
//
//        jPanel60.setOpaque(false);
//
//        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel126.setText("Date");
//
//        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel127.setText("January 20, 2022");
//
//        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
//        jPanel60.setLayout(jPanel60Layout);
//        jPanel60Layout.setHorizontalGroup(
//            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel60Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel126)
//                    .addComponent(jLabel127))
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel60Layout.setVerticalGroup(
//            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel60Layout.createSequentialGroup()
//                .addComponent(jLabel126)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel127)
//                .addContainerGap())
//        );
//
//        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
//        jPanel58.setLayout(jPanel58Layout);
//        jPanel58Layout.setHorizontalGroup(
//            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel58Layout.createSequentialGroup()
//                .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(0, 0, 0)
//                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel58Layout.setVerticalGroup(
//            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
//                .addGap(12, 12, 12)
//                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addContainerGap())
//        );
//
//        jPanel57.add(jPanel58);
//
//        jPanel61.setOpaque(false);
//
//        jPanel62.setOpaque(false);
//
//        jButton12.setText("Refund");
//
//        jButton14.setBackground(new java.awt.Color(0, 144, 228));
//        jButton14.setForeground(new java.awt.Color(240, 240, 240));
//        jButton14.setText("Sold");
//
//        jLabel233.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        jLabel233.setText("Bulk Action");
//
//        jLabel237.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
//        jLabel237.setForeground(new java.awt.Color(17, 184, 255));
//        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel237.setText("2 Selected");
//        jLabel237.setBorder(BorderFactory.createDashedBorder(new Color(17,184,255), 7, 7));
//
//        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
//        jPanel62.setLayout(jPanel62Layout);
//        jPanel62Layout.setHorizontalGroup(
//            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jLabel233)
//                .addGap(26, 26, 26)
//                .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(20, 20, 20))
//        );
//        jPanel62Layout.setVerticalGroup(
//            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
//                .addGap(0, 0, Short.MAX_VALUE)
//                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
//            .addGroup(jPanel62Layout.createSequentialGroup()
//                .addGap(4, 4, 4)
//                .addComponent(jLabel237, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(4, 4, 4))
//            .addComponent(jLabel233, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//
//        jPanel63.setOpaque(false);
//        jPanel63.setLayout(new javax.swing.BoxLayout(jPanel63, javax.swing.BoxLayout.PAGE_AXIS));
//
//
//        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
//        jPanel61.setLayout(jPanel61Layout);
//        jPanel61Layout.setHorizontalGroup(
//            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addGroup(jPanel61Layout.createSequentialGroup()
//                .addGap(20, 20, 20)
//                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(20, 20, 20))
//        );
//        jPanel61Layout.setVerticalGroup(
//            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel61Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(10, 10, 10))
//        );
//
//        jPanel57.add(jPanel61);
//
//        this.add(jPanel57);
        
        jPanel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jPanel50.addMouseListener(new MouseAdapter(){
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

            @Override
            public void mouseClicked(MouseEvent e) {
                
                
                if(expanded){
                    jPanel57.setVisible(false);
                }else{
                    jPanel57.setVisible(true);
                }
                expanded = !expanded;
            }
            
            
        });
        
        jButton14.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus("Sold");
            }

        });

        jButton12.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus("Refund");
            }

        });
        
    }
    
    void updateStatus(String status){
        selectedTransaction.forEach((k, e) -> {
            TransactionHistoryService ths = new TransactionHistoryService();
            e.setStatus(status);
            ths.updateTransactionHistory(k, e);
        });

        //reset thumbs
        jPanel63.removeAll();
        TransactionHistoryService ths = new TransactionHistoryService();
        ths.getTransactionByReceiptId(receiptData.getId()).forEach(e -> {
            ReceiptOrderListThumb receiptOrderListThumb = new ReceiptOrderListThumb();
            receiptOrderListThumb.setTransaction(e);
            jPanel63.add(receiptOrderListThumb);
        });
        
        updateGraphics();
    }
}
