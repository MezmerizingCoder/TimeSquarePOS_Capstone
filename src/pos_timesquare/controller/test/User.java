/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller.test;

import pos_timesquare.controller.UserService;

/**
 *
 * @author Acer
 */
public class User {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserService user = new UserService();
        
        user.getUserById(1);
        
        System.out.println(user.getUserById(1).getName());
    }
    
}
