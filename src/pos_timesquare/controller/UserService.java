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
                
                
                if(!dbmd.getColumns(null, null, "User", "address").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD address TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "membershipDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD membershipDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "image").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD image TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "hourWorked").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD hourWorked INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "gender").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD gender TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "birthdate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD birthdate DATE"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD status INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "User", "contactNum").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE User ADD contactNum TEXT"; 
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
                   " address TEXT, " +
                   " membershipDate TEXT, " +
                   " image TEXT, " +
                   " hourWorked INTEGER, " +
                   " gender TEXT, " +
                   " birthdate DATE, " +
                   " status Integer, " +
                   " contactNum TEXT, " +
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
    
    
    public List<User> getAllUserDetails(){
        
        Connection conn = getConnection();
        List<User> users = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM User");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                User user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setMembershipDate(rs.getString("membershipDate"));
                user.setImage(rs.getString("image"));
                user.setHourWorked(rs.getInt("hourWorked"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setStatus(rs.getInt("status"));
                user.setContactNum(rs.getString("contactNum"));
                
                users.add(user);
            }
            conn.close();
            return users;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<User> getUserByName(String name){
        
        Connection conn = getConnection();
        List<User> users = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM User WHERE name LIKE '" + name + "%' AND status == 1");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                User user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setMembershipDate(rs.getString("membershipDate"));
                user.setImage(rs.getString("image"));
                user.setHourWorked(rs.getInt("hourWorked"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setStatus(rs.getInt("status"));
                user.setContactNum(rs.getString("contactNum"));
                
                users.add(user);
            }
            conn.close();
            return users;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<User> getAllUserDeleted(){
        
        Connection conn = getConnection();
        List<User> users = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM User WHERE status == 2");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setMembershipDate(rs.getString("membershipDate"));
                user.setImage(rs.getString("image"));
                user.setHourWorked(rs.getInt("hourWorked"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setStatus(rs.getInt("status"));
                user.setContactNum(rs.getString("contactNum"));
                
                users.add(user);
            }
            conn.close();
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
            pst = conn.prepareStatement("SELECT id, username, password, name, role, address, membershipDate, image, hourWorked, gender, birthdate, status, contactNum FROM User WHERE id ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setMembershipDate(rs.getString("membershipDate"));
                user.setImage(rs.getString("image"));
                user.setHourWorked(rs.getInt("hourWorked"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setStatus(rs.getInt("status"));
                user.setContactNum(rs.getString("contactNum"));
            }
            conn.close();
            return user;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean isUsernameExist(String username){
        Connection conn = getConnection();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id FROM User WHERE username == " + "'" +username + "'");
            rs = pst.executeQuery();
            
            if (!rs.isBeforeFirst()) {    
                return false;
            } 
            conn.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void addUser(User user){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO User VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getName());
            pst.setString(5, user.getRole());
            pst.setString(6, user.getAddress());
            pst.setString(7, user.getMembershipDate());
            pst.setString(8, user.getImage());
            pst.setInt(9, user.getHourWorked());
            pst.setString(10, user.getGender());
            pst.setDate(11, (Date) user.getBirthdate());
            pst.setInt(12, user.getStatus());
            pst.setString(13, user.getContactNum());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateUser(int id, User user){
        try {
            Connection conn = getConnection();
            pst = conn.prepareStatement("UPDATE User SET username=?, password=?, name=?, role=?, address=?, membershipDate=?, image=?, hourWorked=?, gender=?, birthdate=?, status=?, contactNum =?  WHERE id = " + id);
            
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getName());
            pst.setString(4, user.getRole());
            pst.setString(5, user.getAddress());
            pst.setString(6, user.getMembershipDate());
            pst.setString(7, user.getImage());
            pst.setInt(8, user.getHourWorked());
            pst.setString(9, user.getGender());
            pst.setDate(10, (Date) user.getBirthdate());
            pst.setInt(11, user.getStatus());
            pst.setString(12, user.getContactNum());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
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
