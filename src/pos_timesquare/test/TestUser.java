/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.UserService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class TestUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserService user = new UserService();
        List<User> list = user.getAllUserDetails();
        
        System.out.println(list);
        System.out.println(user.getUserById(1).getUsername());
        
        VariantService var = new VariantService();
        
    }
    
}
