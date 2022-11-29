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
                String sql = "ALTER TABLE Product ADD barcode TEXT"; 
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
            if(!dbmd.getColumns(null, null, "Product", "image").next()){
                Statement stmt = conn.createStatement();
                String sql = "ALTER TABLE Product ADD image TEXT"; 
                stmt.executeUpdate(sql);
            }
            if(!dbmd.getColumns(null, null, "Product", "favorite").next()){
                Statement stmt = conn.createStatement();
                String sql = "ALTER TABLE Product ADD favorite INTEGER"; 
                stmt.executeUpdate(sql);
            }
            if(!dbmd.getColumns(null, null, "Product", "status").next()){
                Statement stmt = conn.createStatement();
                String sql = "ALTER TABLE Product ADD status TEXT"; 
                stmt.executeUpdate(sql);
            }

        }
        else {
            System.out.println("Not exist");
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE Product(" +
               "id INTEGER NOT NULL UNIQUE," +
               " barcode TEXT, " + 
               " name TEXT, " + 
               " size TEXT, " +
               " color TEXT, " +
               " stocks INTEGER, " +
               " price REAL," +
               " image TEXT," +
               " favorite INTEGER," +
               " status TEXT," +
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
            pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price, image, favorite, status FROM Product");
            rs = pst.executeQuery();
        
            while(rs.next()){
             
                Product product = new Product();
                product.setId(Integer.parseInt(rs.getString("id")));
                product.setBarcode(rs.getString("barcode"));
                product.setName(rs.getString("name"));
                product.setSize(rs.getString("size"));
                product.setColor(rs.getString("color"));
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("price")));
                product.setImage(rs.getString("image"));
                product.setFavorite(rs.getInt("favorite"));
                product.setStatus(rs.getString("status"));
                
                products.add(product);
            }
            conn.close();
            return products;
            
        }catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Product> getDeletedProduct(){
    
        Connection conn = getConnection();
        List<Product> products = new ArrayList<>();

        try{
     
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price, image, favorite, status FROM Product WHERE status == 'deleted'");
            rs = pst.executeQuery();
        
            while(rs.next()){
             
                Product product = new Product();
                product.setId(Integer.parseInt(rs.getString("id")));
                product.setBarcode(rs.getString("barcode"));
                product.setName(rs.getString("name"));
                product.setSize(rs.getString("size"));
                product.setColor(rs.getString("color"));
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("price")));
                product.setImage(rs.getString("image"));
                product.setFavorite(rs.getInt("favorite"));
                product.setStatus(rs.getString("status"));
                
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
        pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price, image, favorite, status FROM Product WHERE id ==" + id);
        rs = pst.executeQuery();

        while(rs.next()){

            product.setId(Integer.parseInt(rs.getString("id")));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setSize("size");
            product.setColor("color");
            product.setStocks(Integer.parseInt(rs.getString("stocks")));
            product.setPrice(Float.parseFloat(rs.getString("price")));
            product.setImage(rs.getString("image"));
            product.setFavorite(rs.getInt("favorite"));
            product.setStatus(rs.getString("status"));
        }

        conn.close();
        return product;  

        }catch (SQLException ex) {
            return null;
        }
    }
    
    
    public List<Product> getProductsByName(String name){
    Connection conn = getConnection();
    List<Product> products = new ArrayList<>();

    try {

        System.out.println("Getting data");
        pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price, image, favorite, status FROM Product WHERE name LIKE '" + name + "%' AND status != 'deleted'");
        rs = pst.executeQuery();

        while(rs.next()){
            Product product = new Product();

            product.setId(Integer.parseInt(rs.getString("id")));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setSize("size");
            product.setColor("color");
            product.setStocks(Integer.parseInt(rs.getString("stocks")));
            product.setPrice(Float.parseFloat(rs.getString("price")));
            product.setImage(rs.getString("image"));
            product.setFavorite(rs.getInt("favorite"));
            product.setStatus(rs.getString("status"));
            
            products.add(product);
        }

        conn.close();
        return products;  

        }catch (SQLException ex) {
            return null;
        }
    }
    

    public void updateProduct(int id, Product product){
        try {
            Connection conn = getConnection();
            pst = conn.prepareStatement("UPDATE Product SET barcode = ?, name = ?, size = ?, color = ?, stocks = ?, price = ?, image = ?, favorite = ?, status = ? WHERE id = " + id);
            
            pst.setString(1, product.getBarcode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getSize());
            pst.setString(4, product.getColor());
            pst.setInt(5, product.getStocks());
            pst.setFloat(6, product.getPrice());
            pst.setString(7, product.getImage());
            pst.setInt(8, product.getFavorite());
            pst.setString(9, product.getStatus());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Product> getProductByBarcode(String barcode){
        Connection conn = getConnection();
        List<Product> products = new ArrayList<>();

        try {

            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, barcode, name, size, color, stocks, price, image, favorite, status FROM Product WHERE barcode ==" + barcode);
            rs = pst.executeQuery();

            while(rs.next()){
                Product product = new Product();
                product.setId(Integer.parseInt(rs.getString("id")));
                product.setBarcode(rs.getString("barcode"));
                product.setName(rs.getString("name"));
                product.setSize("size");
                product.setColor("color");
                product.setStocks(Integer.parseInt(rs.getString("stocks")));
                product.setPrice(Float.parseFloat(rs.getString("price")));
                product.setImage(rs.getString("image"));
                product.setFavorite(rs.getInt("favorite"));
                product.setStatus(rs.getString("status"));
                
                products.add(product);
            }

            conn.close();
            return products;  

        }catch (SQLException ex) {
            return null;
        }
    }
    
    public long addProduct(Product product){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Product VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, product.getBarcode());
            pst.setString(3, product.getName());
            pst.setString(4, product.getSize());
            pst.setString(5, product.getColor());
            pst.setInt(6, product.getStocks());
            pst.setFloat(7, product.getPrice());
            pst.setString(8, product.getImage());
            pst.setInt(9, product.getFavorite());
            pst.setString(10, product.getStatus());
            
            pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
            long id = rs.getLong(1);
            
            System.out.println("Add Success");
            
            conn.close();
            return id;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
    }
    
    public void deleteProductById(int id){
        try {
           Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM Product WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void UpdateProduct(int id, String barcode, String name, String color, String size, int stocks, float price){
        try {
            Connection conn = getConnection();
            Product product = new Product();
            pst = conn.prepareStatement("UPDATE Product SET barcode =?, name =?, color =?, size =?, stocks=?, price =?  WHERE id =?");
            
            pst.setString(1, barcode);
            pst.setString(2, name);
            pst.setString(3, color);
            pst.setString(4, size);
            pst.setInt(5, stocks);
            pst.setFloat(6, price);
            pst.setInt(7, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
