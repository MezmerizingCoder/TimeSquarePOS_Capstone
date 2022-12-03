/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import pos_timesquare.model.Receipt;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class ReceiptService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public ReceiptService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Receipt", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Receipt", "product").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD product TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "stocks").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD stocks Integer"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "price").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD stocks REAL"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "salesPersonId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD salesPersonId INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "date").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD date TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "customerId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD customerId INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Receipt", "type").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Receipt ADD type TEXT"; 
                    stmt.executeUpdate(sql);
                }
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Receipt(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " product TEXT, " + 
                   " stocks INTEGER, " + 
                   " price REAL, " +
                    " salesPersonId INTEGER, " + 
                    " date TEXT, " + 
                        " customerId INTEGER, " + 
                        " type TEXT, " + 
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Receipt> getAllReceipt(){
        
        Connection conn = getConnection();
        List<Receipt> receipts = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
                
                receipts.add(receipt);
            }
            conn.close();
            return receipts;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public Receipt getReceiptById(int id){
        Connection conn = getConnection();
        Receipt receipt = new Receipt();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt WHERE id ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
            }
            conn.close();
            return receipt;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Receipt> getReceiptBySalesPersonId(int id){
        Connection conn = getConnection();
        List<Receipt> receipts = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt WHERE salesPersonId ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
                
                receipts.add(receipt);
                
            }
            conn.close();
            return receipts;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Receipt> getReceiptByCustomerId(int id){
        Connection conn = getConnection();
        List<Receipt> receipts = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt WHERE customerId ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
                
                receipts.add(receipt);
                
            }
            conn.close();
            return receipts;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    
    public int addReceipt(Receipt receipt){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Receipt VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, receipt.getProduct());
            pst.setInt(3, receipt.getStocks());
            pst.setFloat(4, receipt.getPrice());
            pst.setInt(5, receipt.getSalesPersonId());
            pst.setString(6, receipt.getDate());
            pst.setInt(7, receipt.getCustomerId());
            pst.setString(8, receipt.getType());
            
            pst.executeUpdate();
            
            ResultSet rs  = pst.getGeneratedKeys();
            
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            conn.close();
            
            
            return id;
            
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptService.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public List<Receipt> getReceiptByDateRange(String from, String to){
        Connection conn = getConnection();
        List<Receipt> receipts = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt WHERE date BETWEEN date('" + from +"') AND date('"+ to +"')");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
                
                receipts.add(receipt);
                
            }
            conn.close();
            return receipts;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Receipt> getReceiptByDate(String date){
        Connection conn = getConnection();
        List<Receipt> receipts = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Receipt WHERE date(date) == '" + date + "'");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Receipt receipt = new Receipt();
                receipt.setId(rs.getInt("id"));
                receipt.setProduct(rs.getString("product"));
                receipt.setStocks(rs.getInt("stocks"));
                receipt.setPrice(rs.getFloat("price"));
                receipt.setSalesPersonId(rs.getInt("salesPersonId"));
                receipt.setDate(rs.getString("date"));
                receipt.setCustomerId(rs.getInt("customerId"));
                receipt.setType(rs.getString("type"));
                
                receipts.add(receipt);
                
            }
            conn.close();
            return receipts;
            
        } catch (SQLException ex) {
            return null;
        }
    }
}
