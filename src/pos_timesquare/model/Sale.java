/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.model;

/**
 *
 * @author DUDA
 */
public class Sale {
    
    private int id;
    private int productid;
    private String date; 
    private int stocks;
    
    public int getId() {
        return id;
    }
    
     public void setId(int id) {
        this.id = id;
    }
     
     public int getProductId() {
        return productid;
    }
    
     public void setProductId(int productid) {
        this.productid = productid;
    }
     public String getDate() {
        return date;
    }
    
     public void setDate(String date) {
        this.date = date;
    }
     
     public int getStocks() { 
         return stocks;
     }
     
     public void setStocks(int stocks) {
         this.stocks = stocks;
     
     }
}
