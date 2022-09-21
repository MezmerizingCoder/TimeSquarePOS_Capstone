/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class UserService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public UserService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "User", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "User", "username").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD username TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "password").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD password TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "name").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD name TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "role").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD role TEXT"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE User(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " username TEXT, " + 
                   " password TEXT, " + 
                   " name TEXT, " +
                   " role TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public List<User> getAllUserDetails(){
        
        Connection conn = getConnection();
        List<User> users = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, username, password FROM User");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                User user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
                users.add(user);
            }
            return users;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public User getUserById(int id){
        Connection conn = getConnection();
        User user = new User();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, username, password FROM User WHERE id ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName("name");
                user.setRole("role");
            }
            return user;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void addUser(User user){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO User VALUES(?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getName());
            pst.setString(5, user.getRole());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteUserById(int id){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM User WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
