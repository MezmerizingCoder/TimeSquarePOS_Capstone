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
import pos_timesquare.model.Category;
import pos_timesquare.model.Variants;

/**
 *
 * @author Acer
 */
public class CategoryService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public CategoryService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "User", null);
            if (tables.next()) {
//                System.out.println("Exist");
//                
//                if(!dbmd.getColumns(null, null, "User", "username").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE User ADD username TEXT"; 
//                    stmt.executeUpdate(sql);
//                }
//                if(!dbmd.getColumns(null, null, "User", "password").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE User ADD password TEXT"; 
//                    stmt.executeUpdate(sql);
//                }
//                if(!dbmd.getColumns(null, null, "User", "name").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE User ADD name TEXT"; 
//                    stmt.executeUpdate(sql);
//                }
//                if(!dbmd.getColumns(null, null, "User", "role").next()){
//                    Statement stmt = conn.createStatement();
//                    String sql = "ALTER TABLE User ADD role TEXT"; 
//                    stmt.executeUpdate(sql);
//                }
                
            }else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Category(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " product_id INTEGER, " + 
                   " type TEXT, " + 
                   " brand TEXT, " +
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
    
    
    public List<Category> getAllCategory(){
        Connection conn = getConnection();
        List<Category> categories = new ArrayList<>();
       
        try{
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Category");
            rs = pst.executeQuery();

             while(rs.next()){

                Category category = new Category();

                category.setId(Integer.parseInt(rs.getString("id")));
                category.setProduct_id(Integer.parseInt(rs.getString("product_id")));
                category.setType(rs.getString("type"));
                category.setBrand(rs.getString("brand"));
                categories.add(category);
            }
            return categories;

        }catch (SQLException ex) {
                Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
        }
    }
}
