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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.User;
import pos_timesquare.model.Variants;

/**
 *
 * @author Acer
 */
public class VariantService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public VariantService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Variants", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Variants", "product_id").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD product_id INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Variants", "type").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Variants ADD type TEXT"; 
                    stmt.executeUpdate(sql);
                }
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Variants(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " product_id INTEGER, " + 
                   " type TEXT, " + 
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public List<Variants> getProductVariants(int product_id){
//        Connection conn = getConnection();
//        Variants variant = new Variants();
//       
//    }
}
