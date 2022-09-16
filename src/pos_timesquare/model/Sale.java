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
     
     public int getStocks() { 
         return stocks;
     }
     
     public void setStocks(int stocks) {
         this.stocks = stocks;
     
     }
}
