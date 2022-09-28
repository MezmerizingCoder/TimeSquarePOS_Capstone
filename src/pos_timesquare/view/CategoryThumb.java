/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.model.Category;
import pos_timesquare.model.Product;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.jPanel30;
import static pos_timesquare.view.MainFrame.jProgressBar1;

/**
 *
 * @author Acer
 */
public class CategoryThumb extends JPanel {
    
    boolean hover = false;
    
    
    
    JPanel jPanel39 = new javax.swing.JPanel(){

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            
            
            if(darkRB.isSelected()){
                g2.setColor(new Color(49, 52, 58));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
                
                g2.setColor(new Color(71,71,71));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }else{
                g2.setColor(new Color(190,190,190));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }
            if(hover){
                
                if(darkRB.isSelected()){
                    g2.setColor(new Color(62, 70, 86));
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
                }else{
                    g2.setColor(new Color(225, 225, 225));
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
                }
            }
            
            
        }

    };
    JPanel jPanel18 = new JPanel();

    JLabel jLabel23 = new JLabel();
    JLabel jLabel31 = new JLabel();
    
    String categoryType;
    String categoryBrand;

    
        
    
    public String getCategoryBrand() {
        return categoryBrand;
    }

    public void setCategoryBrand(String categoryBrand) {
        this.categoryBrand = categoryBrand;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    
    public void setThumbTitle(String title){
        jLabel23.setText(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase());
        categoryBrand = title;
    }
    
    public void setThumbItems(int item){
        jLabel31.setText(Integer.toString(item) + " items");
    }
    
    public CategoryThumb(){
        
        jPanel39.setMaximumSize(new java.awt.Dimension(32767, 50));
        
        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel23)
                .addGap(0, 0, 0)
                .addComponent(jLabel31)
                .addGap(8, 8, 8))
        );
        
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Something");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel31.setText("22 items");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );
        
        jPanel39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel39.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Type: " + categoryType + ", Brand: " + categoryBrand);
                jProgressBar1.setVisible(true);
                jPanel30.removeAll();
                
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllCategory();
                
                ProductService productsService = new ProductService();
                List<Product>products = productsService.getAllProductDetails();
                
                categories.forEach(element -> {
                    if(element.getBrand().equals(categoryBrand) && element.getType().equals(categoryType)){
                        products.forEach(element2 -> {
                            if(element2.getId() == element.getId()){
                                ProductThumb productThumb = new ProductThumb();
                                productThumb.setProductDetails(element2);
                                jPanel30.add(productThumb);
                                System.out.println(element2.getName() + " Added");
                            }
                        });
                    }
                });
                
                jProgressBar1.setVisible(false);

//                ProductService productsService = new ProductService();
//                List<Product>products = productsService.getAllProductDetails();
//
//                products.forEach(elements -> {
//                    ProductThumb productThumb = new ProductThumb();
//                    
//                    productThumb.setProductDetails(elements);
//                    jPanel30.add(productThumb);
//                });
            }
        });
        
        jPanel39.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
                revalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
                hover = false;
                repaint();
                revalidate();
            }
            
        });
        
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        add(jPanel18);
    }
}
