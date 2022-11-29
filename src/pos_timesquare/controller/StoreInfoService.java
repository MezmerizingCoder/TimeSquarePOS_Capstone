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
import pos_timesquare.model.StoreInfo;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class StoreInfoService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public StoreInfoService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "StoreInfo", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "StoreInfo", "name").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD name TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "StoreInfo", "address").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD address TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "StoreInfo", "contactNum").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD contactNum TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "StoreInfo", "branch").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD branch TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "StoreInfo", "email").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD email TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "StoreInfo", "salesPersonDetail").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE StoreInfo ADD salesPersonDetail INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE StoreInfo(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " name TEXT, " + 
                   " address TEXT, " + 
                   " contactNum TEXT, " +
                   " branch TEXT, " +
                   " email TEXT, " +
                   "salesPersonDetail INTEGER, "+
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StoreInfoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public StoreInfo getStoreInfoDetails(){
        
        Connection conn = getConnection();
        StoreInfo temp = new StoreInfo();
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM StoreInfo");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setAddress(rs.getString("address"));
                temp.setContactNum(rs.getString("contactNum"));
                temp.setBranch(rs.getString("branch"));
                temp.setEmail(rs.getString("email"));
                temp.setSalesPersonDetail(rs.getInt("salesPersonDetail"));
                
            }
            
            conn.close();
            return temp;
            
        } catch (SQLException ex) {
            Logger.getLogger(StoreInfoService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void updateStoreInfo(int id, StoreInfo storeInfo){
        try {
            Connection conn = getConnection();
            
            if(isIdExist(id)){
                pst = conn.prepareStatement("UPDATE StoreInfo SET name=?, address=?, contactNum=?, branch=?, email=?, salesPersonDetail=?  WHERE id =" + id);
            
                pst.setString(1, storeInfo.getName());
                pst.setString(2, storeInfo.getAddress());
                pst.setString(3, storeInfo.getContactNum());
                pst.setString(4, storeInfo.getBranch());
                pst.setString(5, storeInfo.getEmail());
                pst.setInt(6, storeInfo.getSalesPersonDetail());

                pst.executeUpdate();
                
                System.out.println("Update Success");
            }else{
                pst = conn.prepareStatement("INSERT INTO StoreInfo VALUES(?, ?, ?, ?)");
            
                pst.setString(1, null);
                pst.setString(2, storeInfo.getName());
                pst.setString(3, storeInfo.getAddress());
                pst.setString(4, storeInfo.getContactNum());
                pst.setString(5, storeInfo.getBranch());
                pst.setString(6, storeInfo.getEmail());
                pst.setInt(7, storeInfo.getSalesPersonDetail());

                pst.executeUpdate();

                System.out.println("Add Success");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StoreInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isIdExist(int id){
        Connection conn = getConnection();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT 1 FROM StoreInfo WHERE id == " + id);
            rs = pst.executeQuery();
            
            if (!rs.isBeforeFirst()) {    
                return false;
            } 
            conn.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(StoreInfo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
