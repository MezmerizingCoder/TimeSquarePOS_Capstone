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
import pos_timesquare.model.ServiceTickets;

/**
 *
 * @author Administrator
 */
public class ServiceTicketsService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
 
         public ServiceTicketsService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "ServiceTickets", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "ServiceTickets", "CustomerId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD CustomerId INTEGER"; 
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
                    String sql = "ALTER TABLE ServiceTickets ADD WalkInDate DATE"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "ServiceTickets", "EstimateFinish").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE ServiceTickets ADD EstimateFinish DATE"; 
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
                   " CustomerId INTEGER, " + 
                   " Defects TEXT, " + 
                   " Price REAL, " +
                   " WalkInDate DATE, " +
                   " EstimateFinish DATE, " +
                   " Status TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
   public ServiceTickets getServiceTicketsDetailsById(int id){
        
        Connection conn = getConnection();
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerId, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceTickets WHERE id =="+ id);
            rs = pst.executeQuery();
            
            ServiceTickets st = new ServiceTickets();
            while(rs.next()){
                
                st.setId(Integer.parseInt(rs.getString("id")));
                st.setCustomerId(rs.getInt("CustomerId"));
                st.setDefects(rs.getString("Defects"));
                st.setPrice(Float.parseFloat(rs.getString("Price")));
                st.setWalkInDate(rs.getDate("WalkInDate"));
                st.setEstimateFinish(rs.getDate("EstimateFinish"));
                st.setStatus(rs.getString("Status"));

                
            }
            conn.close();
            return st;
            
        } catch (SQLException ex) {
            return null;
        }
    }
   
   public List<ServiceTickets> getAllServiceTicketsDetails(){
        
        Connection conn = getConnection();
        List<ServiceTickets> s = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerId, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceTickets");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                ServiceTickets st = new ServiceTickets();
                st.setId(Integer.parseInt(rs.getString("id")));
                st.setCustomerId(rs.getInt("CustomerId"));
                st.setDefects(rs.getString("Defects"));
                st.setPrice(Float.parseFloat(rs.getString("Price")));
                st.setWalkInDate(rs.getDate("WalkInDate"));
                st.setEstimateFinish(rs.getDate("EstimateFinish"));
                st.setStatus(rs.getString("Status"));

                s.add(st);
            }
            conn.close();
            return s;
            
        } catch (SQLException ex) {
            return null;
        }
    }
         
     public int addServiceTickets(ServiceTickets st){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO ServiceTickets VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            
            pst.setString(2, st.getDefects());
            pst.setFloat(3, st.getPrice());
            pst.setDate(4, (Date) st.getWalkInDate());
            pst.setDate(5, (Date) st.getEstimateFinish());
            pst.setString(6, st.getStatus());
            pst.setInt(7, st.getCustomerId());
            
            pst.executeUpdate();
            
            ResultSet rs  = pst.getGeneratedKeys();
            
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            conn.close();
            
            
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
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
    
    public void setToDone(int id){
        try{
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("UPDATE ServiceTickets SET Status = 'Done' WHERE id =?");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            conn.close();
        }catch(SQLException ex){
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UpdateServiceTickets(int id, int customerId, String defects, float price, Date walkInDate, Date estimateFinish, String status){
        try {
            Connection conn = getConnection();
            ServiceTickets st = new ServiceTickets();
            
            pst = conn.prepareStatement("UPDATE ServiceTickets SET CustomerId =?, Defects =?, Price =?, WalkInDate =?, EstimateFinish =?, Status =?  WHERE id =?");         
            pst.setInt(1, customerId);
            pst.setString(2, defects);
            pst.setFloat(3, price);
            pst.setDate(4, walkInDate);
            pst.setDate(5, estimateFinish);
            pst.setString(6, status);
            pst.setInt(7, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ServiceTickets> getAllServiceTicketsDetailsByDate(String date){
        
        Connection conn = getConnection();
        List<ServiceTickets> s = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerId, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceTickets WHERE strftime('%Y-%m-%d', WalkInDate/1000, 'unixepoch') == '"+date+"'");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                ServiceTickets st = new ServiceTickets();
                st.setId(Integer.parseInt(rs.getString("id")));
                st.setCustomerId(rs.getInt("CustomerId"));
                st.setDefects(rs.getString("Defects"));
                st.setPrice(Float.parseFloat(rs.getString("Price")));
                st.setWalkInDate(rs.getDate("WalkInDate"));
                st.setEstimateFinish(rs.getDate("EstimateFinish"));
                st.setStatus(rs.getString("Status"));

                s.add(st);
            }
            conn.close();
            return s;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<ServiceTickets> getAllServiceTicketsDetailsByDateBetween(String from, String to){
        
        Connection conn = getConnection();
        List<ServiceTickets> s = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, CustomerId, Defects, Price, WalkInDate, EstimateFinish, Status FROM ServiceTickets WHERE strftime('%Y-%m-%d', WalkInDate/1000, 'unixepoch') BETWEEN '"+from+"' AND '"+to+"'");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                ServiceTickets st = new ServiceTickets();
                st.setId(Integer.parseInt(rs.getString("id")));
                st.setCustomerId(rs.getInt("CustomerId"));
                st.setDefects(rs.getString("Defects"));
                st.setPrice(Float.parseFloat(rs.getString("Price")));
                st.setWalkInDate(rs.getDate("WalkInDate"));
                st.setEstimateFinish(rs.getDate("EstimateFinish"));
                st.setStatus(rs.getString("Status"));

                s.add(st);
            }
            conn.close();
            return s;
            
        } catch (SQLException ex) {
            return null;
        }
    }
}