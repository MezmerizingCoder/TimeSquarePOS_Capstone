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
public class TesSale {
    
    public static void main(String[] args) {
    
     SaleService sales = new SaleService();
     List<Sale> list = sales.getAllSaleDetails();
        
     System.out.println(list.get(0).getProductId());
     System.out.println(list.get(0).getStocks());
    }
    
}
