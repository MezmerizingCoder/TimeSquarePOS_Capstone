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
import pos_timesquare.model.ServiceTickets;

/**
 *
 * @author Administrator
 */
public class ServiceTicketsService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
 
      /*   public ServiceTicketsService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "ServiceTickets", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "ServiceTickets", "CustomerName").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD CustomerName TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "Defects").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD Defects TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "Price").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD Price REAL"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "WalkInDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD WalkInDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "EstimateFinish").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD EstimateFinish TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "Status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD Status TEXT"; 
                    stmt.executeUpdate(sql);
                }
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE ServiceTickets(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " CustomerName TEXT, " + 
                   " Defects TEXT, " + 
                   " Price REAL, " +
                   " WalkInDate TEXT, " +
                   " EstimateFinish TEXT, " +
                   " Status TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } */
   
   public List<ServiceTickets> getAllServiceTicketsDetails(){
        
        Connection conn = getConnection();
        List<ServiceTickets> s = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerName, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceTickets");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                ServiceTickets st = new ServiceTickets();
                st.setId(Integer.parseInt(rs.getString("id")));
                st.setCustomerName(rs.getString("CustomerName"));
                st.setDefects(rs.getString("Defects"));
                st.setPrice(Float.parseFloat(rs.getString("Price")));
                st.setWalkInDate(rs.getString("WalkInDate"));
                st.setEstimateFinish(rs.getString("EstimateFinish"));
                st.setStatus(rs.getString("Status"));

                s.add(st);
            }
            return s;
            
        } catch (SQLException ex) {
            return null;
        }
    }
         
     public void addServiceTickets(ServiceTickets st){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO ServiceTickets VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, st.getCustomerName());
            pst.setString(3, st.getDefects());
            pst.setFloat(4, st.getPrice());
            pst.setString(5, st.getWalkInDate());
            pst.setString(6, st.getEstimateFinish());
            pst.setString(7, st.getStatus());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void deleteServiceTicketsById(int id){
        try {
           Connection conn = getConnection();   
            pst = conn.prepareStatement("DELETE FROM ServiceTickets WHERE id = ?");    
            pst.setInt(1, id);          
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void UpdateServiceTickets(int id, String customerName, String defects, float price, String walkInDate, String estimateFinish, String status){
        try {
            Connection conn = getConnection();
            ServiceTickets st = new ServiceTickets();
            
            pst = conn.prepareStatement("UPDATE ServiceTickets SET CustomerName =?, Defects =?, Price =?, WalkInDate =?, EstimateFinish =?, Status =?  WHERE id =?");         
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
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}