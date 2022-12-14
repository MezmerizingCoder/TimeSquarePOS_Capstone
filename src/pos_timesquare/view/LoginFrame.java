/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pos_timesquare.controller.LoginFunctionService;
import pos_timesquare.controller.UserService;
import pos_timesquare.model.User;
import pos_timesquare.test.TestTimeDate;

/**
 *
 * @author Acer
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    
    SignupAccount frame;
    
    public LoginFrame() {
        initComponents();
        usernameField.putClientProperty("JTextField.placeholderText", "Username");
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
//        adminButton.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 20 ) );
//        employeeButton.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 20 ) );

        adminConfirmation.setLocationRelativeTo(null);
        
        try {
            Files.createDirectories(Paths.get("./img/profile/"));
            Files.createDirectories(Paths.get("./img/product/"));
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        BufferedImage bufferedImage = null;
        try {
            String cwd = System.getProperty("user.dir");
//            bufferedImage = ImageIO.read(new File(cwd + image));
                    bufferedImage = ImageIO.read(getClass().getResource("/img/icon/T.jpg"));
        } catch (IOException ex) {

        }
        Image scaledImage;
        if(bufferedImage.getWidth() > bufferedImage.getHeight()){
            scaledImage = bufferedImage.getScaledInstance(jLabel4.getWidth(), -1, Image.SCALE_SMOOTH);
        }else{
            scaledImage = bufferedImage.getScaledInstance(-1, jLabel4.getHeight(), Image.SCALE_SMOOTH);
        }

        ImageIcon imageIcon = new ImageIcon(scaledImage);

        jLabel4.setIcon(imageIcon);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        adminConfirmation = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        adminUsernameInput = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        adminPasswordInput = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        adminErrorLog = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(244,244,244));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }

        };
        usernameField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(244,244,244));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }

        };
        passwordField = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        adminButton = new javax.swing.JButton();
        employeeButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        errorLog = new javax.swing.JPanel();

        jDialog1.setTitle("Authentication Failed");
        jDialog1.setAlwaysOnTop(true);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        adminConfirmation.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        adminConfirmation.setTitle("Admin Permision Required");
        adminConfirmation.setAlwaysOnTop(true);
        adminConfirmation.setMinimumSize(new java.awt.Dimension(420, 360));
        adminConfirmation.setModalityType(java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        adminConfirmation.setType(java.awt.Window.Type.POPUP);
        adminConfirmation.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                adminConfirmationWindowClosed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Admin Username");

        adminUsernameInput.setPreferredSize(new java.awt.Dimension(71, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Admin Password");

        adminPasswordInput.setPreferredSize(new java.awt.Dimension(90, 40));

        jButton1.setBackground(new java.awt.Color(0, 144, 228));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Continue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        adminErrorLog.setLayout(new javax.swing.BoxLayout(adminErrorLog, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(adminPasswordInput, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(adminErrorLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(adminUsernameInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminUsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminErrorLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel5);

        javax.swing.GroupLayout adminConfirmationLayout = new javax.swing.GroupLayout(adminConfirmation.getContentPane());
        adminConfirmation.getContentPane().setLayout(adminConfirmationLayout);
        adminConfirmationLayout.setHorizontalGroup(
            adminConfirmationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        adminConfirmationLayout.setVerticalGroup(
            adminConfirmationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - Time Square");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 40));

        usernameField.setBackground(new java.awt.Color(245, 245, 245));
        usernameField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        usernameField.setBorder(null);
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        loginButton.setBackground(new java.awt.Color(0, 144, 228));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setOpaque(false);

        passwordField.setBackground(new java.awt.Color(245, 245, 245));
        passwordField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwordField.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204)));

        jLabel1.setText("Create new account");

        adminButton.setText("Admin");
        adminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminButtonActionPerformed(evt);
            }
        });

        employeeButton.setText("Employee");
        employeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(adminButton, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        errorLog.setLayout(new javax.swing.BoxLayout(errorLog, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(errorLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(251, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(errorLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        LoginFunctionService login = new LoginFunctionService();
        User user = login.getMatchAccount(usernameField.getText(), passwordField.getText());
        
//        System.out.println(user);
        if(user != null){
            
            if(user.getStatus() == 1){
                this.dispose();

                MainFrame frame = new MainFrame(user);
                frame.setVisible(true);
            }else if(user.getStatus() == 0){
                errorLog.removeAll();
                errorLog.add(errorPanel("Account is deactivated!"));

    //            usernameField.setText("");
                passwordField.setText("");

                loginButton.putClientProperty("JComponent.outline", "error");

                repaint();
                revalidate();
            }else{
                errorLog.removeAll();
                errorLog.add(errorPanel("User & Pass doesn't match!"));

    //            usernameField.setText("");
                passwordField.setText("");

                loginButton.putClientProperty("JComponent.outline", "error");

                repaint();
                revalidate();
            }
//            System.out.println(user.getRole());
           
        }else{
//            JOptionPane.showMessageDialog(this, "Username and Password doesn't match!", "Authentication Error",
//            JOptionPane.ERROR_MESSAGE);

            errorLog.removeAll();
            errorLog.add(errorPanel("User & Pass doesn't match!"));
            
//            usernameField.setText("");
            passwordField.setText("");
            
            loginButton.putClientProperty("JComponent.outline", "error");
            
            repaint();
            revalidate();
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void employeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeButtonActionPerformed
        // TODO add your handling code here:
//        this.dispose();
            
        frame = new SignupAccount("Employee Account");
        frame.setVisible(true);
    }//GEN-LAST:event_employeeButtonActionPerformed

    private void adminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminButtonActionPerformed
        // TODO add your handling code here:
//        this.dispose();
        
            
        
        frame = new SignupAccount("Admin Account");
//        frame.setEnabled(false);
        frame.setVisible(true);
        
        adminConfirmation.setVisible(true);
        
    }//GEN-LAST:event_adminButtonActionPerformed

    private void adminConfirmationWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_adminConfirmationWindowClosed
        // TODO add your handling code here:
        frame.dispose();
        
    }//GEN-LAST:event_adminConfirmationWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        adminConfirmation.setVisible(false);
        
        LoginFunctionService login = new LoginFunctionService();
        User user = login.getMatchAccount(adminUsernameInput.getText(), adminPasswordInput.getText());
        
        adminErrorLog.removeAll();
        if(user != null){
            if(user.getRole().equals("admin")){
                adminConfirmation.setVisible(false);
            }else{
                adminErrorLog.add(errorPanel("Account doesn't have admin access!"));
            }
        }else{
            adminErrorLog.add(errorPanel("User & Pass doesn't match!"));
        }
        
        adminConfirmation.repaint();
        adminConfirmation.revalidate();
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public JPanel errorPanel(String value){
        JPanel panel = new JPanel();
        
        panel.setMinimumSize(new Dimension(120, 35));
        
        panel.setPreferredSize(new Dimension(120, 35));
        panel.setBackground(new Color(255, 132, 132));
        
//        panel.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
        panel.putClientProperty( FlatClientProperties.STYLE, "arc: 10" );
        
        JLabel label = new JLabel(value);
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setForeground(Color.white);
//        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label.setPreferredSize(new Dimension(150, 25));
        
//        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
        panel.add(label);
        
        return panel;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try {
            //                            FlatMaterialLighterIJTheme.install();
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
            FlatMaterialLighterIJTheme.updateUI();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JFrame.setDefaultLookAndFeelDecorated( true );
        JDialog.setDefaultLookAndFeelDecorated( true );
        
//        UIManager.put( "TextComponent.arc", 20 );
//        UIManager.put( "Button.arc", 20 );
//        UIManager.put("ScrollBar.thumbArc", 999);
//        UIManager.put("ScrollBar.thumbInsets", new Insets(3, 3, 3, 3));
        
        UIManager.put("Button.arc", 18);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 20);
        UIManager.put("Component.arc", 18);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(3, 3, 3, 3));
        
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminButton;
    private javax.swing.JDialog adminConfirmation;
    private javax.swing.JPanel adminErrorLog;
    private javax.swing.JPasswordField adminPasswordInput;
    private javax.swing.JTextField adminUsernameInput;
    private javax.swing.JButton employeeButton;
    private javax.swing.JPanel errorLog;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
