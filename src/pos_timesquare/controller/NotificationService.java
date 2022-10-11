/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Notification;



/**
 *
 * @author Merryjane A Alis
 */
public class NotificationService {
   Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
     public NotificationService(){
//        Connection conn = getConnection();
//        try {
//            DatabaseMetaData dbmd = conn.getMetaData();
//            ResultSet tables = dbmd.getTables(null, null, "Notification", null);
//            if (tables.next()) {
//                System.out.println("Exist");
//                
//                if(!dbmd.getColumns(null, null, "Notification", "productId").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE Notification ADD productId INTEGER"; 
//                    stmt.executeUpdate(sql);
//                }
//                if(!dbmd.getColumns(null, null, "Notification", "date").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE Notification ADD date DATE"; 
//                    stmt.executeUpdate(sql);
//                }
//                if(!dbmd.getColumns(null, null, "Notification", "time").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE Notification ADD time DATETIME"; 
//                    stmt.executeUpdate(sql);
//                }
//                
//            }
//            else {
//                System.out.println("Not exist");
//                Statement stmt = conn.createStatement();
//                String sql = "CREATE TABLE Notification (" +
//                   "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                   " productId INTEGER      NOT NULL\n" +
//"                       REFERENCES Product ( productid ) MATCH FULL, " + 
//                   " date      DATE     NOT NULL, " + 
//                   " time      DATETIME NOT NULL, "; 
//
//                stmt.executeUpdate(sql);
//                System.out.println("Created table in given database...");  
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
//        }     
    }
    public List<Notification> getAllNotificationDetails(){
    
    Connection conn = getConnection();
    List<Notification> notifications = new ArrayList<>();
    
    try{
     
        System.out.println("Getting data");
        pst = conn.prepareStatement("SELECT id, productId, date, time FROM Notification");
        rs = pst.executeQuery();
        
         while(rs.next()){
             
               Notification notification = new Notification();
                notification.setId(Integer.parseInt(rs.getString("id")));
                notification.setProductId(Integer.parseInt(rs.getString("productId")));
                notification.setDate(rs.getString("date"));
                notification.setTime(rs.getString("time"));
                
                notifications.add(notification);
            }
            return notifications;
            
    }catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
    }
}
    public Notification getNotificationById(int id){
        Connection conn = getConnection();
        Notification notification = new Notification();
        
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productId, date, time FROM Notification WHERE id ==" + id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                notification.setId(Integer.parseInt(rs.getString("id")));
                notification.setProductId(Integer.parseInt(rs.getString("productId")));
                notification.setDate(rs.getString("date"));
                notification.setTime(rs.getString("time"));
               
            }
            
            return notification;  
            
            }catch (SQLException ex) {
              return null;
            }
    }
        public void addNotification(Notification notification){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Notification VALUES(?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, notification.getProductId());
            pst.setString(3, notification.getDate());
            pst.setString(4, notification.getTime());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);} 
        
    }
    
    public void deleteNotificationById(int id){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM Notification WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);}

    }
    public void UpdateNotification(int id, int productid, String date, String time){
        try {
            Connection conn = getConnection();
            Notification nf = new Notification();
            pst = conn.prepareStatement("UPDATE Notification SET productid =?, date =?, time =? WHERE id =?");
            
            pst.setInt(1, productid);
            pst.setString(2, date);
            pst.setString(3, time);
            pst.setInt(4, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}