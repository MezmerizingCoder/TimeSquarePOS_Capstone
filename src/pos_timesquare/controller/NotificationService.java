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
                    String sql = "ALTER TABLE Notification ADD date TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Notification", "title").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Notification ADD title TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Notification", "status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Notification ADD status INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Notification(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " productId INTEGER, " +
                   " date TEXT, " +
                   " title TEXT, " +
                   " status TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
     
     
    public List<Notification> getAllNotificationDetails(){
    
        Connection conn = getConnection();
        List<Notification> notifications = new ArrayList<>();

        try{

            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Notification");
            rs = pst.executeQuery();

             while(rs.next()){

                   Notification notification = new Notification();
                    notification.setId(Integer.parseInt(rs.getString("id")));
                    notification.setProductId(Integer.parseInt(rs.getString("productId")));
                    notification.setDate(rs.getString("date"));
                    notification.setTitle(rs.getString("title"));
                    notification.setStatus(rs.getInt("status"));

                    notifications.add(notification);
                }
                conn.close();
                return notifications;

        }catch (SQLException ex) {
                Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
        }
    }
    
    
    public List<Notification> getLimitedNotification(int num){
    
        Connection conn = getConnection();
        List<Notification> notifications = new ArrayList<>();

        try{

            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Notification ORDER BY id DESC LIMIT "+num);
            rs = pst.executeQuery();

             while(rs.next()){

                   Notification notification = new Notification();
                    notification.setId(Integer.parseInt(rs.getString("id")));
                    notification.setProductId(Integer.parseInt(rs.getString("productId")));
                    notification.setDate(rs.getString("date"));
                    notification.setTitle(rs.getString("title"));
                    notification.setStatus(rs.getInt("status"));

                    notifications.add(notification);
                }
                conn.close();
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
            pst = conn.prepareStatement("SELECT id, productId, date, title FROM Notification WHERE id ==" + id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                notification.setId(Integer.parseInt(rs.getString("id")));
                notification.setProductId(Integer.parseInt(rs.getString("productId")));
                notification.setDate(rs.getString("date"));
                notification.setTitle(rs.getString("title"));
                notification.setStatus(rs.getInt("status"));
            }
            conn.close();
            
            return notification;  
            
            }catch (SQLException ex) {
              return null;
            }
    }
        public void addNotification(Notification notification){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Notification VALUES(?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, notification.getProductId());
            pst.setString(3, notification.getDate());
            pst.setString(4, notification.getTitle());
            pst.setInt(5, notification.getStatus());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
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
    public void UpdateNotification(int id, int productid, String date, String title, int status){
        try {
            Connection conn = getConnection();
            Notification nf = new Notification();
            pst = conn.prepareStatement("UPDATE Notification SET productid =?, date =?, title=?, status=? WHERE id =?");
            
            pst.setInt(1, productid);
            pst.setString(2, date);
            pst.setString(3, title);
            
            pst.setInt(4, status);
            pst.setInt(5, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}