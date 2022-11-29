/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
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
                    String sql = "ALTER TABLE TransactionHistory ADD transactionDate DATE"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "TransactionHistory", "orders").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD orders INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "TransactionHistory", "totalPrice").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD totalPrice REAL"; 
                    stmt.executeUpdate(sql);
                }   
                if(!dbmd.getColumns(null, null, "TransactionHistory", "receiptId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD receiptId Integer"; 
                    stmt.executeUpdate(sql);
                }  
                if(!dbmd.getColumns(null, null, "TransactionHistory", "status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD status TEXT"; 
                    stmt.executeUpdate(sql);
                }  
                if(!dbmd.getColumns(null, null, "TransactionHistory", "variantsId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE TransactionHistory ADD variantsId TEXT"; 
                    stmt.executeUpdate(sql);
                }  
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE TransactionHistory(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " productId INTEGER, " + 
                   " transactionDate DATE, " + 
                   " orders INTEGER, " +
                   " totalPrice REAL, " +
                   " receiptId Integer, "+
                   " status TEXT, "+
                   " variantsId TEXT, "+
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } 
    
    public List<TransactionHistory> getAllTransactionHistoryDetails(){
        
        Connection conn = getConnection();
        List<TransactionHistory> t = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productid, transactiondate, orders, totalprice, receiptId, status, variantsId FROM TransactionHistory");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                TransactionHistory th = new TransactionHistory();
                th.setId(Integer.parseInt(rs.getString("id")));
                th.setProductId(Integer.parseInt(rs.getString("productid")));
                th.setTransactionDate(rs.getDate("transactiondate"));
                th.setOrders(Integer.parseInt(rs.getString("orders")));
                th.setTotalPrice(rs.getFloat("totalprice"));
                th.setReceiptId(rs.getInt("receiptId"));
                th.setStatus(rs.getString("status"));
                th.setVariantId(rs.getString("variantsId"));
                t.add(th);
            }
            conn.close();
            return t;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void addTransactionHistory(TransactionHistory th){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO TransactionHistory VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, th.getProductId());
            pst.setDate(3, (Date) th.getTransactionDate());
            pst.setInt(4, th.getOrders());
            pst.setFloat(5, th.getTotalPrice());
            pst.setInt(6, th.getReceiptId());
            pst.setString(7, th.getStatus());
            pst.setString(8, th.getVariantId());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteTransactionHistoryById(int id){
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
    public void UpdateTransactionHistory(int id, int productid, Date transactiondate, int orders, float totalprice, int receiptId, String status, String variantsId){
        try {
            Connection conn = getConnection();
            TransactionHistory th = new TransactionHistory();
            pst = conn.prepareStatement("UPDATE TransactionHistory SET productid =?, transactiondate =?, orders =?, totalprice =?, receiptId=?, status=?, variantsId=?  WHERE id =" + String.valueOf(id));
            
            pst.setInt(1, productid);
            pst.setDate(2, transactiondate);
            pst.setInt(3, orders);
            pst.setFloat(4, totalprice);
            pst.setInt(5, receiptId);
            pst.setString(6, status);
            pst.setString(7, variantsId);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTransactionHistory(int id, TransactionHistory transaction){
        try {
            Connection conn = getConnection();
//            TransactionHistory th = new TransactionHistory();
            pst = conn.prepareStatement("UPDATE TransactionHistory SET productid =?, transactiondate =?, orders =?, totalprice =?, receiptId=?, status=?, variantsId=?  WHERE id =" + String.valueOf(id));
            
            pst.setInt(1, transaction.getProductId());
            pst.setDate(2, (Date) transaction.getTransactionDate());
            pst.setInt(3, transaction.getOrders());
            pst.setFloat(4, transaction.getTotalPrice());
            pst.setInt(5, transaction.getReceiptId());
            pst.setString(6, transaction.getStatus());
            pst.setString(7, transaction.getVariantId());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<TransactionHistory> getTransactionByDate(Date date){
        Connection conn = getConnection();
        List<TransactionHistory> t = new ArrayList<>();
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM TransactionHistory WHERE transactionDate =" + date);
              
            
            rs = pst.executeQuery();
 
            while(rs.next()){
                System.out.println(rs.getString("productid"));
                System.out.println(rs.getString("orders"));
                TransactionHistory th = new TransactionHistory();
                th.setId(Integer.parseInt(rs.getString("id")));
                th.setProductId(Integer.parseInt(rs.getString("productid")));
                th.setTransactionDate(rs.getDate("transactiondate"));
                th.setOrders(Integer.parseInt(rs.getString("orders")));
                th.setTotalPrice(rs.getFloat("totalprice"));
                th.setReceiptId(rs.getInt("receiptId"));
                th.setStatus(rs.getString("status"));
                th.setVariantId(rs.getString("variantsId"));
                t.add(th);
            }
            
            conn.close();
            return t;
        }catch (SQLException ex) {
            System.out.print(ex.getMessage());
            return null;  
        }
    }
    
    
    public List<TransactionHistory> getTransactionByReceiptId(int receiptId){
        Connection conn = getConnection();
        List<TransactionHistory> t = new ArrayList<>();
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM TransactionHistory WHERE receiptId =" + receiptId);
              
            
            rs = pst.executeQuery();
 
            while(rs.next()){
                System.out.println(rs.getString("productid"));
                System.out.println(rs.getString("orders"));
                TransactionHistory th = new TransactionHistory();
                th.setId(Integer.parseInt(rs.getString("id")));
                th.setProductId(Integer.parseInt(rs.getString("productid")));
                th.setTransactionDate(rs.getDate("transactiondate"));
                th.setOrders(Integer.parseInt(rs.getString("orders")));
                th.setTotalPrice(rs.getFloat("totalprice"));
                th.setReceiptId(rs.getInt("receiptId"));
                th.setStatus(rs.getString("status"));
                th.setVariantId(rs.getString("variantsId"));
                t.add(th);
            }
            
            conn.close();
            return t;
        }catch (SQLException ex) {
            System.out.print(ex.getMessage());
            return null;  
        }
    }
}

