/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

/**
 *
 * @author Administrator
 */

import java.sql.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.TransactionHistory;

public class TransactionHistoryService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public TransactionHistoryService(){
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
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public List<TransactionHistory> getAllTransactionHistoryDetails(){
        
        Connection conn = getConnection();
        List<TransactionHistory> transactionHistory = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productId, transactionDate, orders, totalPrice FROM TransactionHistory");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                TransactionHistory th = new TransactionHistory();
                th.setId(Integer.parseInt(rs.getString("id")));
                th.setProdcutid(Integer.parseInt(rs.getString("productid")));
                th.setTransactionDate(rs.getString("transactionDate"));
                th.setOrders(Integer.parseInt(rs.getString("orders")));
                th.setTotalPrice(Float.parseFloat(rs.getString("totalPrice")));
                
                transactionHistory.add(th);
            }
            return transactionHistory;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
}  
