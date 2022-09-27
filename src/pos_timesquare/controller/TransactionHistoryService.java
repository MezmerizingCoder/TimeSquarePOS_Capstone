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
import pos_timesquare.model.TransactionHistory;

/**
 *
 * @author Administrator
 */
public class TransactionHistoryService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
 
    /*     public TransactionHistoryService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "TransactionHistory", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "TransactionHistory", "productId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD productId INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "TransactionHistory", "transactionDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD transactionDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "TransactionHistory", "orders").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD orders INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "TransactionHistory", "totalPrice").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD totalPrice FLOAT"; 
                    stmt.executeUpdate(sql);
                }      
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE TransactionHistory(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " productId INTEGER, " + 
                   " transactionDate TEXT, " + 
                   " orders INTEGER, " +
                   " totalPrice FLOAT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } */
    
    public List<TransactionHistory> getAllTransactionDetails(){
        
        Connection conn = getConnection();
        List<TransactionHistory> t = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productid, transactiondate, orders, totalprice FROM TransactionHistory");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                TransactionHistory th = new TransactionHistory();
                th.setId(Integer.parseInt(rs.getString("id")));
                th.setProductId(Integer.parseInt(rs.getString("productid")));
                th.setTransactionDate(rs.getString("transactiondate"));
                th.setOrders(Integer.parseInt(rs.getString("orders")));
                th.setTotalPrice(Float.parseFloat(rs.getString("totalprice")));
                
                t.add(th);
            }
            return t;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void addTransaction(TransactionHistory th){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO TransactionHistory VALUES(?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, th.getProductId());
            pst.setString(3, th.getTransactionDate());
            pst.setInt(4, th.getOrders());
            pst.setFloat(5, th.getTotalPrice());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteTransactionById(int id){
        try {
           Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM TransactionHistory WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void UpdateTransaction(int id, int productid, String transactiondate, int orders, float totalprice){
        try {
            Connection conn = getConnection();
            TransactionHistory th = new TransactionHistory();
            pst = conn.prepareStatement("UPDATE TransactionHistory SET productid =?, transactiondate =?, orders =?, totalprice =?  WHERE id =?");
            
            pst.setInt(1, productid);
            pst.setString(2, transactiondate);
            pst.setInt(3, orders);
            pst.setFloat(4, totalprice);
            pst.setInt(5, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

