/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.model.Category;

/**
 *
 * @author Acer
 */
public class TestCategory {
    
    public static <T>List<T> removeDuplicates(List<T> list)
    {
  
        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();
  
        // Add the elements to set
        set.addAll(list);
  
        // Clear the list
        list.clear();
  
        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);
  
        // return the list
        return list;
    }
    
    public static void main(String[] args) {    
        CategoryService categoryService = new CategoryService();
        
        List<String> brands = new ArrayList();
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            brands.add(e.getBrand());
            type.add(e.getType());
        });
        
        List<String> sortedBrands = removeDuplicates(brands);
        List<String> sortedType = removeDuplicates(type);
        
        System.out.println(brands);
        System.out.println(type);

    }
}
