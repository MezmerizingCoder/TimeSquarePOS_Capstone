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
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.customerViewDetailsPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jButton14;
import static pos_timesquare.view.MainFrame.jButton22;
import static pos_timesquare.view.MainFrame.jButton39;
import static pos_timesquare.view.MainFrame.jButton40;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jDateChooser4;
import static pos_timesquare.view.MainFrame.jLabel233;
import static pos_timesquare.view.MainFrame.jLabel237;
import static pos_timesquare.view.MainFrame.jLabel309;
import static pos_timesquare.view.MainFrame.jLabel76;
import static pos_timesquare.view.MainFrame.jPanel86;
import static pos_timesquare.view.MainFrame.jPanel91;
import static pos_timesquare.view.MainFrame.jPanel97;
import static pos_timesquare.view.MainFrame.jRadioButton7;
import static pos_timesquare.view.MainFrame.jRadioButton8;
import static pos_timesquare.view.MainFrame.jTextField11;
import static pos_timesquare.view.MainFrame.jTextField26;
import static pos_timesquare.view.MainFrame.jTextField8;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.selectedCustomer;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class CustomerUnregisteredThumb extends javax.swing.JPanel {

    /**
     * Creates new form CustomerUnregisteredThumb
     */
    
    Customer customerData;
    int totalOrders;

    public Customer getCustomerData() {
        return customerData;
    }

    public void setCustomerData(Customer customerData) {
        this.customerData = customerData;
        
        jLabel1.setText("Customer #" + String.valueOf(customerData.getId()));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(customerData.getMembershipDate());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    
            DateFormat dateformatter = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateformatter.format(new Date(timestamp.getTime()));
            
            DateFormat timeformatter = new SimpleDateFormat("hh:mm a");
            String time = timeformatter.format(new Date(timestamp.getTime()));
            
            jLabel4.setText(date +" | "+ time);
            
            System.out.println(timestamp);
        } catch (ParseException ex) {
//            Logger.getLogger(NotificationThumb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public CustomerUnregisteredThumb() {
        initComponents();
        FlatSVGIcon questionIcon = new FlatSVGIcon("img/icon/question-solid.svg", 13, 20);
        questionIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(168, 168, 168);
            }
        }));
        jLabel2.setIcon(questionIcon);
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                popupContentPanel.removeAll();
                popupContentPanel.add(customerViewDetailsPanel);
                
                selectedCustomer = customerData;
                
                if(selectedCustomer.getName().equals("")){
                    jLabel237.setText("Customer #" + selectedCustomer.getId());
                }else{
                    jLabel237.setText(selectedCustomer.getName());
                }
                
                
//                jLabel318.setText(customer.getAddress());
//                jLabel319.setText(customer.getGender());
//                jLabel320.setText(customer.getBirthDate());
                
                jTextField11.setText(selectedCustomer.getName());
                jTextField8.setText(selectedCustomer.getAddress());
//                jTextField9.setText(customer.getGender());
//                jTextField10.setText(customer.getBirthDate());
                jTextField26.setText(selectedCustomer.getContactNum());
                if(selectedCustomer.getGender() != null){
                    if(selectedCustomer.getGender().equals("male")){
                        jRadioButton7.setSelected(true);
                    }else{
                        jRadioButton8.setSelected(true);
                    }
                }else{
                    jRadioButton7.setSelected(true);
                }
                
                if(selectedCustomer.getBirthDate() != null){
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
                        Date date3;
                        date3 = format.parse(selectedCustomer.getBirthDate());

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

                if(selectedCustomer.getBirthDate() != null){
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
                        Date date3;
                        date3 = format.parse(selectedCustomer.getBirthDate());

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
                jLabel233.setIcon(null);
                if(customerData.getImage() != null && !customerData.getImage().equals("")){
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            BufferedImage bufferedImage = null;
                            try {
                                String cwd = System.getProperty("user.dir");
                                bufferedImage = ImageIO.read(new File(cwd + customerData.getImage()));
//                                bufferedImage = ImageIO.read(getClass().getResource(customerData.getImage()));
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
                if(customerData.getStatus().equals("Blocked")){
                    jPanel97.setVisible(true);
//                    jButton22.setVisible(false);
//                    jButton42.setVisible(true);
                    jButton22.setText("Activate");
                }else if(customerData.getStatus().equals("Deleted")){
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
                List<Receipt> receiptList = rs.getReceiptByCustomerId(customerData.getId());
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
                
                
                
                showPopup();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new JLabel(){
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(297, 54));
        setMinimumSize(new java.awt.Dimension(100, 54));
        setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Customer Id");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setToolTipText("");
        jLabel2.setPreferredSize(new java.awt.Dimension(44, 44));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel3.setText("Date");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
