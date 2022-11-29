/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Customer;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.TransactionHistory;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.customerViewDetailsPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jButton14;
import static pos_timesquare.view.MainFrame.jButton22;
import static pos_timesquare.view.MainFrame.jButton39;
import static pos_timesquare.view.MainFrame.jButton40;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jDateChooser4;
import static pos_timesquare.view.MainFrame.jLabel129;
import static pos_timesquare.view.MainFrame.jLabel233;
import static pos_timesquare.view.MainFrame.jLabel237;
import static pos_timesquare.view.MainFrame.jLabel291;
import static pos_timesquare.view.MainFrame.jLabel76;
import static pos_timesquare.view.MainFrame.jPanel86;
import static pos_timesquare.view.MainFrame.jPanel90;
import static pos_timesquare.view.MainFrame.jPanel91;
import static pos_timesquare.view.MainFrame.jPanel97;
import static pos_timesquare.view.MainFrame.jRadioButton7;
import static pos_timesquare.view.MainFrame.jRadioButton8;
import static pos_timesquare.view.MainFrame.jTextField11;
import static pos_timesquare.view.MainFrame.jTextField26;
import static pos_timesquare.view.MainFrame.jTextField8;
import static pos_timesquare.view.MainFrame.personnelViewDetailsPanel;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.selectedCustomer;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class CustomerThumb extends JPanel {
    
    JPanel jPanel227 = new JPanel();
    JPanel jPanel229 = new JPanel();
    JPanel jPanel57 = new javax.swing.JPanel(){

    public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //        if(darkRB.isSelected()){
    //            g2.setColor(new Color(38, 41, 48));
    //        }else{
    //            
    //        }
            g2.setColor(new Color(126, 180, 177));
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
        }

    };
    
    JLabel jLabel307 = new JLabel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(darkRB.isSelected()){
                g2.setColor(new Color(38, 41, 48));
            }else{
                g2.setColor(new Color(255, 255, 255));
            }
            for(int i = 24; i >= 0; i--){
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
            }
//            g2.setColor(Color.GRAY);
            if(darkRB.isSelected()){
                g2.setColor(new Color(58, 61, 68));
            }else{
                g2.setColor(new Color(225, 225, 225));
            }
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            
//            if(isHover){
//                g2.setColor(new Color(0,144,228));
//                g2.setStroke(new BasicStroke(1));
//                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
//            }
        }
    };
    JLabel jLabel314 = new JLabel();
    JLabel jLabel315 = new JLabel();
    JLabel jLabel22 = new JLabel();
    JLabel jLabel24 = new JLabel();
    JLabel jLabel25 = new JLabel();
    
    Customer customer;
    int totalOrders;
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(darkRB.isSelected()){
            g2.setColor(new Color(38, 41, 48));
        }else{
            g2.setColor(new Color(255, 255, 255));
        }
        g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

        if(darkRB.isSelected()){
            g2.setColor(new Color(55, 55, 65));
        }else{
            g2.setColor(new Color(225, 225, 225));
        }
        
        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
    }
    
    public void setCustomerData(Customer customer){
        this.customer = customer;
        jLabel22.setText(customer.getName());
        jLabel25.setText(customer.getContactNum());
        
        if(customer.getStatus().equals("Blocked")){
            jPanel229.setVisible(true);
        }else{
            jPanel229.setVisible(false);
        }
        
        
        
        if(customer.getImage() != null && !customer.getImage().equals("")){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    BufferedImage bufferedImage = null;
                    try {
                        String cwd = System.getProperty("user.dir");
                        bufferedImage = ImageIO.read(new File(cwd + customer.getImage()));
//                        bufferedImage = ImageIO.read(getClass().getResource(customer.getImage()));
                    } catch (IOException ex) {

                    }
                    Image scaledImage;
                    if(bufferedImage.getWidth() < bufferedImage.getHeight()){
                        scaledImage = bufferedImage.getScaledInstance(62, -1, Image.SCALE_SMOOTH);
                    }else{
                        scaledImage = bufferedImage.getScaledInstance(-1, 62, Image.SCALE_SMOOTH);
                    }

                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    jLabel307.setIcon(imageIcon);
                    
//                    updateGraphics();
//                    revalidate();
//                    repaint();
                }
            });
            t.start();
        }
        
        
    }
    
    
    public CustomerThumb(){
        FlatSVGIcon phoneicon = new FlatSVGIcon("img/icon/phone-solid.svg", 15, 15);
        phoneicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(126, 180, 177);
            }
        }));
        jLabel24.setIcon(phoneicon);
        
        FlatSVGIcon banicon = new FlatSVGIcon("img/icon/ban-solid.svg", 15, 15);
        banicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(230, 76, 76);
            }
        }));
        jLabel315.setIcon(banicon);
        
        this.setMaximumSize(null);
        this.setOpaque(false);
        this.setPreferredSize(new java.awt.Dimension(280, 135));

        jLabel307.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel307.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel229.setOpaque(false);

        jLabel314.setText("Blocked");

        jLabel315.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel229Layout = new javax.swing.GroupLayout(jPanel229);
        jPanel229.setLayout(jPanel229Layout);
        jPanel229Layout.setHorizontalGroup(
            jPanel229Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel229Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel315, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel314)
                .addGap(12, 12, 12))
        );
        jPanel229Layout.setVerticalGroup(
            jPanel229Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel314, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jLabel315, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Paul Justine Faustino");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("099989898");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addContainerGap())
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel227Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel227Layout);
        jPanel227Layout.setHorizontalGroup(
            jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel227Layout.createSequentialGroup()
                .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel229, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel307, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addGroup(jPanel227Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(15, 15, 15))
        );
        jPanel227Layout.setVerticalGroup(
            jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel227Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel307, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel229, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                showPopup();
                
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
                                String cwd = System.getProperty("user.dir");
                                bufferedImage = ImageIO.read(new File(cwd + customer.getImage()));
//                                bufferedImage = ImageIO.read(getClass().getResource(customer.getImage()));
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
                
//                jPanel86
                
                
                updateGraphics();
                
            }
            
        });
        
    }
    
    Timer timer;
    void showPopup(){
        JFrame frame = (JFrame)getTopLevelAncestor();
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
    
    public class OrderedProduct extends JPanel{
        
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
    
}
