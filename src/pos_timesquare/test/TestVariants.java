/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Variants;

/**
 *
 * @author Acer
 */
public class TestVariants {
    public static void main(String[] args) {
        VariantService variantService = new VariantService();
        
        List<Variants> productVariant = variantService.getProductVariants(1);
        
        productVariant.forEach(e -> {
            System.out.println(e.getName());
        });
        
    }
}
