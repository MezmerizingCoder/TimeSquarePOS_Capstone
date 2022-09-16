/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.sql.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Sale;
/**
 *
 * @author DUDA
 */
public class SaleService {
    
Connection conn = null;
    PreparedStatement pst;
    ResultSet rs; 
    public List<Sale> getAllSaleDetails(){

        Connection conn = getConnection();
        List<Sale> sales = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productid, stocks FROM Sales");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Sale sale = new Sale();
                sale.setId(Integer.parseInt(rs.getString("id")));
                sale.setProductId(Integer.parseInt(rs.getString("productid")));
                sale.setStocks(Integer.parseInt(rs.getString("stocks")));
                
                sales.add(sale);
            }
            return sales;
            
        } catch (SQLException ex) {
            return null;
        }
    }

}
