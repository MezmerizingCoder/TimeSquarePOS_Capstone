/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Reports;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class ReportService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public ReportService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Report", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Report", "type").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Report ADD username TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Report", "date").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Report ADD password TEXT"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Report(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " type TEXT, " + 
                   " date TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Reports> getAllReports(){
        
        Connection conn = getConnection();
        List<Reports> reports = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Report");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Reports report = new Reports();
                report.setId(rs.getInt("id"));
                report.setType(rs.getString("type"));
                report.setDate(rs.getString("date"));
                
                reports.add(report);
            }
            conn.close();
            
            return reports;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void addReport(Reports report){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Report VALUES(?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, report.getType());
            pst.setString(3, report.getDate());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
