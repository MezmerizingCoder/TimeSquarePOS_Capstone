/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Acer
 */
public class DatabaseConnection {
    
    static Connection conn = null;
    
    public static Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:TimeSquareDB.db");
            System.out.println("Connection Success!");
            return conn;
        }catch(Exception e){
            System.out.println("Connection Failed!");
            return null;
        }
    }
    
}
