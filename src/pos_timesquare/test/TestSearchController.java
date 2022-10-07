/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import pos_timesquare.controller.SearchController;


/**
 *
 * @author DUDA
 */
public class TestSearchController {
    
    public static void main(String[] args) {
    
        SearchController sc = new SearchController();

        sc.searchSalesByProductName("Rolex");
//        sc.searchProductByName("Rolex");
       
    }
    
}
