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

Connection conn = getConnection();

public User getMatchAccount(User user){
     
    try{
        pst = conn.prepareStatement(" SELECT * FROM User WHERE username=? AND password=?");
        pst.setString(1, user.getUsername());
        pst.setString(2, user.getPassword());
        rs = pst.executeQuery();
        
        if(rs.next()){
           
            System.out.println("Username and Password are matched!");
        }        
        else{
            System.out.println("Username and Password not match!");
        }
            conn.close();
    }

    catch(SQLException ex){
      Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex); 
    }  
        return user;
}

public User getUserType(User role){
    Connection conn = getConnection();
    try{
        pst = conn.prepareStatement(" SELECT * FROM User WHERE username=? and password=? and role=?");
        pst.setString(1, role.getUsername());
        pst.setString(2, role.getPassword());
        pst.setString(3, role.getRole());
        rs = pst.executeQuery();
        
        if(rs.next()){
            System.out.println("You logged in " + rs.getString("username") + " as an " +  rs.getString("role"));
           
                if(role.getRole().equals("admin") || role.getRole().equals("ADMIN") || role.getRole().equals("Admin") )
                {    
                    System.out.println("Redirect to " + rs.getString("role") +  " page");
                }
                if(role.getRole().equals("employee") || role.getRole().equals("Employee") || role.getRole().equals("EMPLOYEE"))
                {
                    System.out.println("Redirect to " + rs.getString("role") +  " page");
                }             
        } 
        else{
            System.out.println("Sorry! Incorrect UserType. Can't log you in. Please try again");
        }
                    conn.close();
    }

    catch(SQLException ex){
      Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex); 
    }  
    
    return role;
}
}
