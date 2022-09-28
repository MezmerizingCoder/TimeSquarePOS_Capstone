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
import pos_timesquare.model.ServiceHistory;

/**
 *
 * @author Administrator
 */
public class ServiceHistoryService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
 
   /*      public ServiceHistoryService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "ServiceHistory", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "ServiceHistory", "CustomerName").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD CustomerName TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceHistory", "Defects").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD Defects TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceHistory", "Price").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD Price FLOAT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceHistory", "WalkInDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD WalkInDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceHistory", "EstimateFinish").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD EstimateFinish TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceHistory", "Status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceHistory ADD Status TEXT"; 
                    stmt.executeUpdate(sql);
                }
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE ServiceHistory(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " CustomerName TEXT, " + 
                   " Defects TEXT, " + 
                   " Price FLOAT, " +
                   " WalkInDate TEXT, " +
                   " EstimateFinish TEXT, " +
                   " Status TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } */
    
   public List<ServiceHistory> getAllServiceHistoryDetails(){
        
        Connection conn = getConnection();
        List<ServiceHistory> s = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerName, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceHistory");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                ServiceHistory sh = new ServiceHistory();
                sh.setId(Integer.parseInt(rs.getString("id")));
                sh.setCustomerName(rs.getString("CustomerName"));
                sh.setDefects(rs.getString("Defects"));
                sh.setPrice(Float.parseFloat(rs.getString("Price")));
                sh.setWalkInDate(rs.getString("WalkInDate"));
                sh.setEstimateFinish(rs.getString("EstimateFinish"));
                sh.setStatus(rs.getString("Status"));

                s.add(sh);
            }
            return s;
            
        } catch (SQLException ex) {
            return null;
        }
    }
         
     public void addServiceHistory(ServiceHistory sh){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO ServiceHistory VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, sh.getCustomerName());
            pst.setString(3, sh.getDefects());
            pst.setFloat(4, sh.getPrice());
            pst.setString(5, sh.getWalkInDate());
            pst.setString(6, sh.getEstimateFinish());
            pst.setString(7, sh.getStatus());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void deleteServiceHistoryById(int id){
        try {
           Connection conn = getConnection();   
            pst = conn.prepareStatement("DELETE FROM ServiceHistory WHERE id = ?");    
            pst.setInt(1, id);          
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void UpdateServiceHistory(int id, String customerName, String defects, float price, String walkInDate, String estimateFinish, String status){
        try {
            Connection conn = getConnection();
            ServiceHistory sh = new ServiceHistory();
            
            pst = conn.prepareStatement("UPDATE ServiceHistory SET CustomerName =?, Defects =?, Price =?, WalkInDate =?, EstimateFinish =?, Status =?  WHERE id =?");         
            pst.setString(1, customerName);
            pst.setString(2, defects);
            pst.setFloat(3, price);
            pst.setString(4, walkInDate);
            pst.setString(5, estimateFinish);
            pst.setString(6, status);
            pst.setInt(7, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}