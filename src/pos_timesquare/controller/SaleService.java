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
    
    public SaleService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Sales", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Sales", "id").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Sales ADD id INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Sales", "productid").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Sales ADD productid INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Sales", "date").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Sales ADD date TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Sales", "stocks").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Sales ADD stocks INTEGER"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Sales(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " productid INTEGER, " + 
                   " date TEXT, " + 
                   " stocks INTEGER, " + 
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTicketsService.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    public List<Sale> getAllSaleDetails(){

        Connection conn = getConnection();
        List<Sale> sales = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, productid, date, stocks FROM Sales");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Sale sale = new Sale();
                sale.setId(Integer.parseInt(rs.getString("id")));
                sale.setProductId(Integer.parseInt(rs.getString("productid")));
                sale.setDate(rs.getString("date"));
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
            
            pst = conn.prepareStatement("INSERT INTO Sales VALUES(?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, sale.getProductId());
            pst.setString(3, sale.getDate());
            pst.setInt(4, sale.getStocks());

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
     
     public void UpdateSaleById(int id, int productid, String date, int stocks){
        try {
            Connection conn = getConnection();
            Sale sale = new Sale();
            pst = conn.prepareStatement("UPDATE Sales SET productid = ? , date = ? , " + "stocks = ? " + " WHERE id = ?");
            
            pst.setInt(1, productid);
            pst.setString(2, date);
            pst.setInt(3, stocks);
            pst.setInt(4, id);
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
