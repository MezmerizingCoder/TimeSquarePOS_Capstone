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
                
            }
            return user;
            
        } catch (SQLException ex) {
            return null;
        }
        
    }
    
    
}
