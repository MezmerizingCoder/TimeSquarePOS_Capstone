/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.SaleService;
import pos_timesquare.model.Sale;
/**
 *
 * @author DUDA
 */
public class TestSale {
    
    public static void main(String[] args) {
    
     SaleService sales = new SaleService();
     
     //List<Sale> list = sales.getAllSaleDetails();
        
     //System.out.println(list.get(0).getProductId());
     //System.out.println(list.get(0).getStocks());
     
        //Sale addsale = new Sale();
        
        //addsale.setProductId(13444);
        //addsale.setStocks(50);
        
        //sales.addSale(addsale);
        //sales.deleteSaleById(1);
        
        Sale update = new Sale();
       
        sales.UpdateSale(2 , 10222, 100);
        
        
    }
    
}
