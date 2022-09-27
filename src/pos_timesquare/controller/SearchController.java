/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.*;
import java.sql.SQLException;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Sale;
/**
 *
 * @author DUDA
 */
public class SearchController {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs; 
    
    public void searchSalesByProductName(String name){
        Connection conn = getConnection();
       
        try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT Sales.productid, Product.name, Sales.id, Product.id, Sales.stocks " + " FROM Sales, Product " 
                    + "WHERE Sales.productid = Product.id AND name == ?" );
            
            pst.setString(1,name);
            
            rs = pst.executeQuery();
 
            while(rs.next()){    
                System.out.println(rs.getInt("id")); 
                System.out.println(rs.getInt("productid")); 
                System.out.println(rs.getInt("stocks")); 
          
            }
            }catch (SQLException ex) {
              System.out.print(ex.getMessage());
            }
    }
    
}
