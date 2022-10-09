   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Product;
import pos_timesquare.model.User;
import pos_timesquare.model.Variants;

/**
 *
 * @author Acer
 */
public class VariantService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public VariantService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Variants", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Variants", "product_id").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD product_id INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "type").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD type TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "name").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD name TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "main_variant").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD main_variant INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "price").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD price REAL"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "stocks").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD stocks INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "barcode").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD barcode INTEGER"; 
                    stmt.executeUpdate(sql);
                }
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Variants(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " product_id INTEGER, " + 
                   " type TEXT, " + 
                   " name TEXT, " +
                   "main_variant INTEGER, " +
                   "price REAL, " +
                   "stocks INTEGER, " +
                   "barcode INTEGER, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Variants> getProductVariants(int product_id){
        Connection conn = getConnection();
        List<Variants> variants = new ArrayList<>();
       
        try{
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, product_id, type, name, main_variant, price, stocks, barcode FROM Variants WHERE product_id = " + product_id);
            rs = pst.executeQuery();

             while(rs.next()){

                Variants variant = new Variants();

                variant.setId(Integer.parseInt(rs.getString("id")));
                variant.setProduct_id(Integer.parseInt(rs.getString("product_id")));
                variant.setType(rs.getString("type"));
                variant.setName(rs.getString("name"));
                variant.setMainVariant(Integer.parseInt(rs.getString("main_variant")));
                variant.setPrice(Float.parseFloat(rs.getString("price")));
                variant.setStocks(Integer.parseInt(rs.getString("stocks")));
                variant.setBarcode(Integer.parseInt(rs.getString("barcode")));
                variants.add(variant);
            }
            conn.close();
            return variants;
        }catch (SQLException ex) {
                Logger.getLogger(VariantService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
        }
    }
    
    public void addVariant(Variants variants){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Variants VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, variants.getProduct_id());
            pst.setString(3, variants.getType());
            pst.setString(4, variants.getName());
            pst.setInt(5, variants.getMainVariant());
            pst.setFloat(6, variants.getPrice());
            pst.setInt(7, variants.getStocks());
            pst.setInt(8, variants.getBarcode());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VariantService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateVariant(int id, Variants variants){
        try {
            Connection conn = getConnection();
            pst = conn.prepareStatement("UPDATE Variants SET product_id = ?, type = ?, name = ? WHERE id = " + id);
            
            pst.setInt(1, variants.getProduct_id());
            pst.setString(2, variants.getType());
            pst.setString(3, variants.getName());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VariantService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteVariantByProductId(int product_id){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM Variants WHERE product_id = ?");
            
            pst.setInt(1, product_id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VariantService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
