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
    
    product.getDeletedProduct().forEach(e -> {
        System.out.println(e.getName());
    });
    
//    Product p = new Product();
//   List<Product> list = product.getAllProductDetails();
//   
//    System.out.println(list.get(0).getId());
//    System.out.println(list.get(0).getBarcode());
//    System.out.println(list.get(0).getName());
//    System.out.println(list.get(0).getColor());
//    System.out.println(list.get(0).getSize());
//    System.out.println(list.get(0).getStocks());
//    System.out.println(list.get(0).getPrice());
    
//<<<<<<< HEAD (77c3a0e) - Edit Variant
//        System.out.println(list);
        
//        Product test = new Product();
//        test.setId(1);
//        test.setBarcode(12345);
//        test.setName("Test Update");
//        test.setPrice(20000);
//        test.setColor("red");
//        test.setStocks(2);
//        test.setSize("small");
//        
//        product.updateProduct(1, test);

//=======
   
//  p.setBarcode("243");
//  p.setName("Citizen");
//  p.setColor("silver");
//  p.setSize("small");
//  p.setStocks(12);
//   p.setPrice(25000);
//   
//   product.addProduct(p);
//   product.deleteProductById(3);
//   product.UpdateProduct(2, "324","Rolex", "silver", "large", 23, 50000  );
   
//>>>>>>> origin/master (fd87b54) - 
    }
    
}
