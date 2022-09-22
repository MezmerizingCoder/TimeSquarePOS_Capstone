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
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Notification", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Notification", "productId").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Notification ADD productId INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Notification", "date").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Notification ADD date DATE"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Notification", "time").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Notification ADD time DATETIME"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Notification (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                   " productId INT      NOT NULL\n" +
"                       REFERENCES Sales ( productid ) MATCH FULL, " + 
                   " date      DATE     NOT NULL, " + 
                   " time      DATETIME NOT NULL, "; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }     
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
}     
