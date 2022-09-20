/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.ProductService;
import pos_timesquare.model.Product;

/**
 *
 * @author Cassandra
 */
public class TestProduct {
    
    public static void main(String[] args) {
        ProductService product = new ProductService();
        List<Product> list = product.getAllProductDetails();
        
        System.out.println(list);
        
     
        
    }
    
}
