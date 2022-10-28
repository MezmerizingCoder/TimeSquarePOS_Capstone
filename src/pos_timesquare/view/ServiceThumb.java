/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pos_timesquare.controller.ServiceTicketsService;
import pos_timesquare.model.ServiceTickets;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.updateGraphics;

/**
 *
 * @author Acer
 */
public class ServiceThumb extends JPanel{
    
    JPanel serviceThumb = new JPanel();
    JPanel jPanel114 = new JPanel();
    JPanel jPanel120 = new JPanel();
    JPanel jPanel121 = new JPanel();
    JPanel serviceThumbExpand = new JPanel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(darkRB.isSelected()){
                g2.setColor(new Color(58, 61, 68));
                g2.drawLine(1, 1, this.getWidth()-2, 1);
            }else{
                g2.setColor(new Color(225, 225, 225));
                g2.drawLine(1, 1, this.getWidth()-2, 1);
            }
        }
    };
    
    JLabel jLabel107 = new JLabel();
    JLabel jLabel105 = new JLabel();
    JLabel jLabel106 = new JLabel();
    JLabel jLabel108 = new JLabel();
    JLabel jLabel118 = new JLabel();
    JLabel jLabel119 = new JLabel();
    JLabel jLabel120 = new JLabel();
    JLabel jLabel121 = new JLabel();
    JLabel jLabel122 = new JLabel();
    JLabel jLabel123 = new JLabel();
    
    JButton jButton17 = new JButton();
    JButton jButton18 = new JButton();
    JButton jButton19 = new JButton();
    
    JComboBox jComboBox5 = new JComboBox();
    
    JTextArea jTextArea2 = new JTextArea();
    
    JScrollPane jScrollPane10 = new JScrollPane();
    
    JTextField jTextField5 = new JTextField();
    JTextField jTextField14 = new JTextField();
    JTextField jTextField12 = new JTextField();
    JTextField jTextField13 = new JTextField();
    
    boolean expanded = false;
    boolean isHover = false;
    
    ServiceTickets serviceTicket = new ServiceTickets();

    public ServiceTickets getServiceTicket() {
        return serviceTicket;
    }

    public void setServiceTicket(ServiceTickets serviceTicket) {
        this.serviceTicket = serviceTicket;
        jLabel105.setText(serviceTicket.getCustomerName());
        jLabel106.setText(serviceTicket.getWalkInDate());
        jLabel108.setText("Order# " + String.valueOf(serviceTicket.getId()));
        
        if(serviceTicket.getStatus().equals("On Progress")){
            jLabel107.setForeground(new java.awt.Color(0, 204, 204));
            jComboBox5.setSelectedIndex(0);
        }else{
            jLabel107.setForeground(new java.awt.Color(0, 204, 104));
            jComboBox5.setSelectedIndex(1);
        }
        jLabel107.setText(serviceTicket.getStatus());
        
        
        //inputs
        jTextField12.setText(serviceTicket.getCustomerName());
        jTextField5.setText(serviceTicket.getWalkInDate());
        jTextField14.setText(serviceTicket.getEstimateFinish());
        jTextArea2.setText(serviceTicket.getDefects());
        jTextField13.setText(String.valueOf(serviceTicket.getPrice()));
        
        
        updateGraphics();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(darkRB.isSelected()){
            g2.setColor(new Color(38, 41, 48));
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-5, 20, 20);
            g2.setColor(new Color(58, 61, 68));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-6, 20, 20);
        }else{
            //            g2.setColor(new Color(245, 245, 245));
            g2.setColor(this.getBackground());
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-5, 20, 20);
            g2.setColor(new Color(225, 225, 225));
            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-6, 20, 20);
        }
        
        if(isHover){
            if(!expanded){
                g2.setColor(new java.awt.Color(0, 144, 228));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-6, 20, 20);
            }
        }else{
            if(darkRB.isSelected()){
                g2.setColor(new Color(58, 61, 68));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-6, 20, 20);
            }else{
                g2.setColor(new Color(225, 225, 225));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-6, 20, 20);
            }
        }
        
    }

    
    ServiceThumb(){
        this.setPreferredSize(null);
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));

        jPanel114.setMaximumSize(new java.awt.Dimension(32767, 71));
        jPanel114.setMinimumSize(new java.awt.Dimension(100, 71));
        jPanel114.setOpaque(false);
        jPanel114.setPreferredSize(new java.awt.Dimension(400, 71));

        jPanel120.setOpaque(false);

        jButton17.setText("Set to Done");

        javax.swing.GroupLayout jPanel120Layout = new javax.swing.GroupLayout(jPanel120);
        jPanel120.setLayout(jPanel120Layout);
        jPanel120Layout.setHorizontalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel120Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jButton17)
                .addGap(15, 15, 15))
        );
        jPanel120Layout.setVerticalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel120Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(0, 204, 204));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("On Progress");

        jPanel121.setOpaque(false);

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel105.setText("Paul Justine");

        jLabel106.setText("Oct 20 2022");

        jLabel108.setText("Order# 1");
        
        jTextArea2.setLineWrap(true);

        javax.swing.GroupLayout jPanel121Layout = new javax.swing.GroupLayout(jPanel121);
        jPanel121.setLayout(jPanel121Layout);
        jPanel121Layout.setHorizontalGroup(
            jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel121Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel121Layout.setVerticalGroup(
            jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel121Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel105)
                .addGap(0, 0, 0)
                .addComponent(jLabel106)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel114Layout = new javax.swing.GroupLayout(jPanel114);
        jPanel114.setLayout(jPanel114Layout);
        jPanel114Layout.setHorizontalGroup(
            jPanel114Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel114Layout.createSequentialGroup()
                .addComponent(jPanel121, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel114Layout.setVerticalGroup(
            jPanel114Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        this.add(jPanel114);

        serviceThumbExpand.setOpaque(false);
        serviceThumbExpand.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel118.setText("Defects");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane10.setViewportView(jTextArea2);

        jButton18.setText("Delete");

        jLabel119.setText("Walk-in Date");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Progress", "Done" }));

        jLabel120.setText("Status");

        jLabel121.setText("Customer name");

        jLabel122.setText("Repair cost");

        jLabel123.setText("Estimated Finish");

        jButton19.setBackground(new java.awt.Color(0, 144, 228));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Save changes");

        javax.swing.GroupLayout serviceThumbExpandLayout = new javax.swing.GroupLayout(serviceThumbExpand);
        serviceThumbExpand.setLayout(serviceThumbExpandLayout);
        serviceThumbExpandLayout.setHorizontalGroup(
            serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(jTextField5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addComponent(jTextField14)))
                    .addComponent(jScrollPane10)
                    .addComponent(jTextField12)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addComponent(jLabel121)
                            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel120))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel122)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 98, Short.MAX_VALUE)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        serviceThumbExpandLayout.setVerticalGroup(
            serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel121)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addComponent(jLabel120)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel122))
                .addGap(18, 18, 18)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        
        jPanel121.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                thumbClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                if(!expanded){
                    isHover = true;
                    updateGraphics();
                }
                System.out.println("is hover");
            }
            public void mouseExited(MouseEvent e){
                if(!expanded){
                    isHover = false;
                    updateGraphics();
                }
                
            }
            
        });
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                if(!expanded){
                    isHover = true;
                    updateGraphics();
                }
                System.out.println("is hover");
            }
            public void mouseExited(MouseEvent e){
                if(!expanded){
                    isHover = false;
                    updateGraphics();
                }
            }
        });
        
        jButton18.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                removeThumb();
            }
            
        });
        
        jButton19.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updateThumb();
            }
        });
        
        jButton17.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setToDone();
            }
        });

//        this.add(serviceThumbExpand);
    }
    public void setToDone(){
        ServiceTicketsService sts = new ServiceTicketsService();
        sts.setToDone(serviceTicket.getId());
        jLabel107.setForeground(new java.awt.Color(0, 204, 104));
        jLabel107.setText("Done");
        updateGraphics();
    }
    public void removeThumb(){
        ServiceTicketsService sts = new ServiceTicketsService();
        sts.deleteServiceTicketsById(serviceTicket.getId());
        System.out.println("delete ticket: " + serviceTicket.getId());
        this.getParent().remove(this);
    }
    public void thumbClicked(){
        System.out.println("Expand this panel");
        if(expanded){
            this.remove(serviceThumbExpand);
            expanded = false;
        }else{
            this.add(serviceThumbExpand);
            expanded = true;
        }
        updateGraphics();
    }
    
    public void updateThumb(){
        ServiceTicketsService sts = new ServiceTicketsService();
        sts.UpdateServiceTickets(
                serviceTicket.getId(), 
                jTextField12.getText(), 
                jTextArea2.getText(), 
                Float.parseFloat(jTextField13.getText()), 
                jTextField5.getText(), 
                jTextField14.getText(), 
                jComboBox5.getSelectedItem().toString());
        
        if(jComboBox5.getSelectedItem().toString().equals("Done")){
            jLabel107.setForeground(new java.awt.Color(0, 204, 104));
        }else{
            jLabel107.setForeground(new java.awt.Color(0, 204, 204));
        }
        jLabel107.setText(jComboBox5.getSelectedItem().toString());
        jLabel105.setText(jTextField12.getText());
        jLabel106.setText(jTextField5.getText());

        this.remove(serviceThumbExpand);
        expanded = false;

        System.out.println("Update Success!!");
        System.out.println("ID: " + serviceTicket.getId());
    }
}
