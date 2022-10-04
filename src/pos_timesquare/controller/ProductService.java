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
import pos_timesquare.model.TransactionHistory;


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
                    String sql = "ALTER TABLE Product ADD price REAL"; 
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
                   " price REAL," +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
            conn.close();

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
                product.setSize(rs.getString("size"));
                product.setColor(rs.getString("color"));
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("price")));
                
                products.add(product);
            }
            conn.close();
            return products;
            
        }catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
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
            product.setPrice(Float.parseFloat(rs.getString("price")));
        }

        conn.close();
        return product;  

        }catch (SQLException ex) {
            return null;
        }
    }

    public void updateProduct(int id, Product product){
        try {
            Connection conn = getConnection();
            pst = conn.prepareStatement("UPDATE Product SET barcode = ?, name = ?, size = ?, color = ?, stocks = ?, price = ? WHERE id = " + id);
            
            pst.setInt(1, product.getBarcode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getSize());
            pst.setString(4, product.getColor());
            pst.setInt(5, product.getStocks());
            pst.setFloat(6, product.getPrice());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
