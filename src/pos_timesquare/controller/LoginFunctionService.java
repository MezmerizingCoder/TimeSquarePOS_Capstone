/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.User;

/**
 *
 * @author Administrator
 */
public class LoginFunctionService {
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs;



public User getMatchAccount(String username, String password){
    Connection conn = getConnection();
    User user = null;
    try {
            pst = conn.prepareStatement("SELECT * FROM User WHERE username =? and password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            if(rs.next()){
                user = new User();
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
                user.setContactNum(rs.getString("contactNum"));
                user.setStatus(rs.getInt("status"));
                
                System.out.println("Username and password are match. You logged " + rs.getString("name")+ " as an "+ rs.getString("role"));
            
                conn.close();
            
                return user;
            }
            else if(username.isEmpty() && password.isEmpty()){
                System.out.println("Please enter username and password");
            }
            else{
                System.out.println("Username and password are not match. Can't log you in.");
            }
            
            
           return null;
          
        } catch (SQLException ex) {
            return null;
        }
 
}
}