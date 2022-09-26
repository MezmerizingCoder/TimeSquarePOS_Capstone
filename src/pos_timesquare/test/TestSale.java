/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;


import pos_timesquare.controller.SaleService;
/**
 *
 * @author DUDA
 */
public class TestSale {
    
    public static void main(String[] args) {
    
     SaleService sales = new SaleService();
     
     //List<Sale> list = sales.getAllSaleDetails();
        
     //System.out.println(list.get(1).getProductId());
     //System.out.println(list.get(1).getStocks());
     
        //Sale addsale = new Sale();
        
        //addsale.setProductId(145555);
        //addsale.setStocks(100);
        
        //sales.addSale(addsale);
        //sales.deleteSaleById(1);
        
        sales.UpdateSaleById(2 , 10222, 100);
           
    }
    
}
