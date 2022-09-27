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
    public static void main (String[] args){
    ProductService product = new ProductService();
    //List<Product> list = product.getAllProductDetails();
    Product addProduct = new Product();
    
        //System.out.println(list);
       addProduct.setName("casio");
       addProduct.setBarcode(01234);
       addProduct.setSize("Medium");
       addProduct.setColor("Gold");
       addProduct.setStocks(23);
       addProduct.setPrice(1200);
       
       product.addProduct(addProduct);
       product.deleteProductById(1);
       
        //System.out.println(list);
        //System.out.println(product.getProductById(2).getBarcode());
    }
    
}