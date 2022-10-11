/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
/**
 *
 * @author DUDA
 */
public class SalesComputationController {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs; 
   
    
    public void getSumofDailySales(String date){
        Connection conn = getConnection();
        
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT sum(totalprice), transactionDate " + " FROM TransactionHistory " + 
                                        "WHERE transactionDate = ?  " );
              
            pst.setString(1,date);
            
            rs = pst.executeQuery();
 
            while(rs.next()){
                ;
                System.out.println(rs.getFloat("sum(totalprice)"));
 
            }
            
        }catch (SQLException ex) {
              System.out.print(ex.getMessage());
            }
    }
    
    public void getSumofMonthlySales(String date){
        Connection conn = getConnection();
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT transactionDate, strftime('%Y-%m-%d',transactionDate, 'start of month', '+1 month', '-1 day'), sum(totalprice) " + " FROM TransactionHistory " + 
                                        "WHERE transactionDate >= ? GROUP BY strftime('%Y-%m-%d',transactionDate, 'start of month', '+1 month', '-1 day')" );
                   
            pst.setString(1,date);
            
            rs = pst.executeQuery();
 
            while(rs.next()){
                System.out.println(rs.getString("transactionDate"));
                System.out.println(rs.getFloat("sum(totalprice)"));
            }
        
        }catch (SQLException ex) {
              System.out.print(ex.getMessage());
            }
    }
    
    public void getSumofAnnuallySales(String date){
        Connection conn = getConnection();
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT transactionDate, strftime('%Y-%m-%d',transactionDate, 'start of year', '+1 year', '-1 day'), sum(totalprice) " + " FROM TransactionHistory " + 
                                        "WHERE transactionDate >= ? GROUP BY strftime('%Y-%m-%d',transactionDate, 'start of year', '+1 year', '-1 day')" );
                   
            pst.setString(1,date);
            
            rs = pst.executeQuery();
 
            while(rs.next()){
                System.out.println(rs.getString("transactionDate"));
                System.out.println(rs.getFloat("sum(totalprice)"));
            }
        
        }catch (SQLException ex) {
              System.out.print(ex.getMessage());
            }
    }
    
}
