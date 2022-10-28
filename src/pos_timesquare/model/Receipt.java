/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.model;

/**
 *
 * @author Acer
 */
public class Receipt {

    int id;
    String product;
    int stocks;

    
    float price;
    
    public Receipt(){}

    public Receipt(String product, int stocks, float price) {
        this.product = product;
        this.stocks = stocks;
        this.price = price;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    
    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
   
   
   
    
}
