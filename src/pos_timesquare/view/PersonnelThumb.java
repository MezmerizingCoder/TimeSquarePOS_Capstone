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
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.controller.UserService;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.User;
import static pos_timesquare.view.MainFrame.blurBGPanel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jButton15;
import static pos_timesquare.view.MainFrame.jButton16;
import static pos_timesquare.view.MainFrame.jButton6;
import static pos_timesquare.view.MainFrame.jCheckBoxMenuItem1;
import static pos_timesquare.view.MainFrame.jLabel129;
import static pos_timesquare.view.MainFrame.jLabel130;
import static pos_timesquare.view.MainFrame.jLabel131;
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
import static pos_timesquare.view.MainFrame.jPanel90;
import static pos_timesquare.view.MainFrame.personnelViewDetailsPanel;
import static pos_timesquare.view.MainFrame.popupContentPanel;
import static pos_timesquare.view.MainFrame.popupPanel;
import static pos_timesquare.view.MainFrame.updateGraphics;
import static pos_timesquare.view.MainFrame.selectedUser;
import static pos_timesquare.view.MainFrame.userSalesRank;

/**
 *
 * @author Acer
 */
public class PersonnelThumb extends JPanel {
    
    JPanel jPanel219 = new JPanel();
    JPanel jPanel222 = new JPanel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            if(darkRB.isSelected()){
//                g2.setColor(new Color(38, 41, 48));
//            }else{
//                g2.setColor(new Color(255, 255, 255));
//            }
            if(isAdmin){
                g2.setColor(new Color(255, 168, 38, 80));
            }else{
                g2.setColor(new Color(38, 151, 255, 60));
            }
            g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);

//            if(darkRB.isSelected()){
//                g2.setColor(new Color(55, 55, 65));
//            }else{
//                g2.setColor(new Color(225, 225, 225));
//            }
//
//            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
        }
    };
    JPanel jPanel220 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(this.getBackground());
            }else{
                g2.setColor(new Color(245, 245, 245));
            }
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
        }

    };
    
    JLabel jLabel258 = new JLabel(){
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
    JLabel jLabel259 = new JLabel();
    JLabel jLabel261 = new JLabel();
    JLabel jLabel260 = new JLabel();
    JLabel jLabel262 = new JLabel();
    JLabel jLabel263 = new JLabel();
    JLabel jLabel264 = new JLabel();
    JLabel jLabel265 = new JLabel();
    JLabel jLabel266 = new JLabel();
    
    JButton jButton38 = new JButton();
    JButton jButton37 = new JButton();
    JProgressBar jProgressBar2 = new JProgressBar();
    
    Timer timer;
    
    boolean myAccount = false;

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

    
    User userData = new User();
    boolean isAdmin = false;

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
        jButton6.setVisible(true);
        
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
                        scaledImage = bufferedImage.getScaledInstance(62, -1, Image.SCALE_SMOOTH);
                    }else{
                        scaledImage = bufferedImage.getScaledInstance(-1, 62, Image.SCALE_SMOOTH);
                    }

                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    jLabel258.setIcon(imageIcon);
                    
//                    updateGraphics();
//                    revalidate();
//                    repaint();
                }
            });
            t.start();
        }
        
        jLabel259.setText(userData.getName());
        
        if(userData.getRole().equals("admin")){
            jLabel261.setForeground(new java.awt.Color(255, 151, 55));
            jLabel261.setText("Admin");
            isAdmin = true;
            
            jButton37.setText("Set as Employee");
        }else{
            jLabel261.setForeground(new java.awt.Color(0, 102, 255));
            jLabel261.setText("Employee");
            isAdmin = false;
            
            jButton37.setText("Set as Admin");
        }
        
        if(userData.getStatus() == 1){
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/check_circle_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
//                    return new Color(55, 146, 53);
                    return new Color(77, 213, 151);
                }
            }));
            jLabel266.setIcon(pnegativeicon);
            jLabel265.setText("Active");
            
            jButton38.setText("Deactivate");
        }else if(userData.getStatus() == 2){
            jButton37.setVisible(false);
            jButton38.setVisible(false);
            
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
//                    return new Color(215, 122, 122);255, 85, 85
                    return new Color(168, 168, 168);
                }
            }));
            jLabel266.setIcon(pnegativeicon);
            jLabel265.setText("Deleted");
            
            jButton38.setText("Activate");
            jButton6.setVisible(false);
            
            this.setPreferredSize(new java.awt.Dimension(255, 210));
            
        }else{
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
//                    return new Color(215, 122, 122);255, 85, 85
                    return new Color(249, 92, 92);
                }
            }));
            jLabel266.setIcon(pnegativeicon);
            jLabel265.setText("Deactivated");
            
            
            
            jButton38.setText("Activate");
        }
        
        ReceiptService rs = new ReceiptService();
        int totalSales = rs.getAllReceipt().size();
        List<Receipt> personSales = rs.getReceiptBySalesPersonId(userData.getId());
        float percentSale = 0;
        if(personSales.size() > 0 ){
            percentSale = ( (float)personSales.size() /  (float)totalSales) * 100;
        }
        
        System.out.println("Person sales: " + personSales.size() + " / "+ rs.getAllReceipt().size());
        
        jLabel264.setText(personSales.size() + " Sales");
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        jLabel263.setText(String.valueOf(df.format(percentSale)) + "%");
        
        jProgressBar2.setValue((int) percentSale);
        
        
        userSalesRank.put(userData.getId(), percentSale);
        
    }
    
    
    public void isMyAccount(){
        jButton37.setEnabled(false);
        jButton38.setEnabled(false);
        jLabel259.setText(userData.getName() + "(You)");
        
        myAccount = true;
    }
    
    public PersonnelThumb(){
        this.setMaximumSize(new java.awt.Dimension(300, 252));
        this.setMinimumSize(new java.awt.Dimension(300, 252));
        
        this.setOpaque(false);
        
        jLabel258.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel222.setOpaque(false);
        jPanel220.setOpaque(false);

        jButton37.setText("Set as Admin");

        jButton38.setText("Deactivate");

//        jProgressBar2.setForeground(new java.awt.Color(0, 153, 102));

        jProgressBar2.setForeground(new java.awt.Color(78, 209, 130));
        jProgressBar2.setValue(80);

//        jLabel258.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel259.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel259.setText("Employee Name");

        jLabel260.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel260.setText("11/11/2022");

        jPanel222.setBackground(new java.awt.Color(199, 214, 255));

        jLabel261.setForeground(new java.awt.Color(0, 102, 255));
        jLabel261.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel261.setText("Employee");

        javax.swing.GroupLayout jPanel222Layout = new javax.swing.GroupLayout(jPanel222);
        jPanel222.setLayout(jPanel222Layout);
        jPanel222Layout.setHorizontalGroup(
            jPanel222Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel261, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        jPanel222Layout.setVerticalGroup(
            jPanel222Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel261, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jLabel262.setText("Performance:");

        jLabel263.setText("80%");

        jLabel264.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel264.setText("12 Sales");

        jLabel265.setText("Deactivated");

        jLabel266.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel220Layout = new javax.swing.GroupLayout(jPanel220);
        jPanel220.setLayout(jPanel220Layout);
        jPanel220Layout.setHorizontalGroup(
            jPanel220Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel220Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel266, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel265)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel220Layout.setVerticalGroup(
            jPanel220Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel265, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
            .addComponent(jLabel266, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel219Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel219Layout);
        jPanel219Layout.setHorizontalGroup(
            jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel219Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel219Layout.createSequentialGroup()
                        .addComponent(jPanel220, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel219Layout.createSequentialGroup()
                        .addGroup(jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel219Layout.createSequentialGroup()
                                .addComponent(jLabel262)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel263)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel264))
                            .addComponent(jLabel260, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel219Layout.createSequentialGroup()
                                .addComponent(jLabel258, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel222, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel259, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel219Layout.createSequentialGroup()
                                .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton38, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))))
        );
        jPanel219Layout.setVerticalGroup(
            jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel219Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel222, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel258, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel259)
                .addGap(0, 0, 0)
                .addComponent(jLabel260)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel220, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel262)
                    .addComponent(jLabel263)
                    .addComponent(jLabel264))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel219Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jButton38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        
        jButton37.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UserService us = new UserService();
                if(userData.getRole().equals("admin")){
                    userData.setRole("employee");
                    us.updateUser(userData.getId(), userData);
                    jButton37.setText("Set as Admin");
                    
                    jLabel261.setForeground(new java.awt.Color(0, 102, 255));
                    jLabel261.setText("Employee");
                    isAdmin = false;
                }else{
                    userData.setRole("admin");
                    us.updateUser(userData.getId(), userData);
                    jButton37.setText("Set as Employee");
                    
                    jLabel261.setForeground(new java.awt.Color(255, 151, 55));
                    jLabel261.setText("Admin");
                    isAdmin = true;
                }   
            }
            
        });
        
        jButton38.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UserService us = new UserService();
                if(userData.getStatus() == 1){
                    userData.setStatus(0);
                    jButton38.setText("Activate");
                    
                    FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
                    pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                        public Color apply(Color t) {
                            return new Color(249, 92, 92);
                        }
                    }));
                    jLabel266.setIcon(pnegativeicon);
                    jLabel265.setText("Deactivated");

                    jButton38.setText("Activate");
                }else{
                    userData.setStatus(1);
                    jButton38.setText("Deactivate");
                    
                    FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/check_circle_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
                    pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                        public Color apply(Color t) {
                            return new Color(77, 213, 151);
                        }
                    }));
                    jLabel266.setIcon(pnegativeicon);
                    jLabel265.setText("Active");

                    jButton38.setText("Deactivate");
                }
                us.updateUser(userData.getId(), userData);
                
            }
            
        });
        
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(myAccount){
                    
                }else{
                    
                }
                
                showPopup();
                
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
                
                
                        
                
            }
            
        });
        
    }
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
}
