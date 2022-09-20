/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Product;


/**
 *
 * @author Cassandra
 */
public class ProductService {
    
     Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    


public ProductService(){
    
    Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Product", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Product", "barcode").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD barcode INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Product", "name").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD name TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Product", "size").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD size TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Product", "color").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD color TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Product", "stocks").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD stocks INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Product", "price").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Product ADD price INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Product(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " barcode INTEGER, " + 
                   " name TEXT, " + 
                   " size TEXT, " +
                   " color TEXT, " +
                   " stocks INTEGER, " +
                   " price INTEGER," +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }

}

public List<Product> getAllProductDetails(){
    
    Connection conn = getConnection();
    List<Product> products = new ArrayList<>();
    
    try{
     
        System.out.println("Getting data");
        pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price FROM Product");
        rs = pst.executeQuery();
        
         while(rs.next()){
             
                Product product = new Product();
                product.setId(Integer.parseInt(rs.getString("id")));
                product.setBarcode(Integer.parseInt(rs.getString("barcode")));
                product.setName(rs.getString("name"));
                product.setSize("size");
                product.setColor("color");
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("[price")));
                
                products.add(product);
            }
            return products;
            
    }catch (SQLException ex) {
            return null;
    }
}

public Product getProductById(int id){
        Connection conn = getConnection();
        Product product = new Product();
        
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price FROM Product WHERE id ==" + id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                
                product.setId(Integer.parseInt(rs.getString("id")));
                product.setBarcode(Integer.parseInt(rs.getString("barcode")));
                product.setName(rs.getString("name"));
                product.setSize("size");
                product.setColor("color");
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("[price")));
            }
            
            return product;  
            
            }catch (SQLException ex) {
            return null;
            }
    }
}
