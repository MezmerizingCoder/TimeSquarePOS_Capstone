/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.repo;

import java.sql.*;


/**
 *
 * @author Acer
 */
public class UserRepo {
    static Connection conn = null;
    
    
    public static Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:TimeSquareDB.db");
            System.out.println("Connection Success!");
            return con;
        }catch(Exception e){
            System.out.println("Connection Failed!");
            return null;
        }
    }
    
}
