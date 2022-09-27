  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    
     public void addSale(Sale sale){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Sales VALUES(?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, sale.getProductId());
            pst.setInt(3, sale.getStocks());

            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void deleteSaleById(int id){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM Sales WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void UpdateSaleById(int id, int productid, int stocks){
        try {
            Connection conn = getConnection();
            Sale sale = new Sale();
            pst = conn.prepareStatement("UPDATE Sales SET productid = ? , " + "stocks = ? " + " WHERE id = ?");
            
            pst.setInt(1, productid);
            pst.setInt(2, stocks);
            pst.setInt(3, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
