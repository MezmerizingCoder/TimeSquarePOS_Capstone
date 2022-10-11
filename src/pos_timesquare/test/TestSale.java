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
     
     List<Sale> list = sales.getAllSaleDetails();
        
//     System.out.println(list.get(2).getProductId());
//     System.out.println(list.get(2).getDate());
//     System.out.println(list.get(2).getStocks());
    
     
        Sale addsale = new Sale();
        
        addsale.setProductId(2);
        addsale.setDate("2022-10-22");
        addsale.setStocks(120);
        sales.addSale(addsale);
        
       //sales.deleteSaleById(1);
        
       //sales.UpdateSaleById(2 , 2, "9/25/22" , 100);
           
    }
    
}
