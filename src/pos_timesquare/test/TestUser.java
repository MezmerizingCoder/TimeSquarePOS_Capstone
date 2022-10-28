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

        User adduser = new User();
        
        adduser.setUsername("admin");
        adduser.setPassword("12add");
        adduser.setName("paul justine");
        adduser.setRole("employee");
        adduser.setAddress("iba, wawa");
        adduser.setMembershipDate("10/16/22");
        adduser.setImage("src/asdfa/fsad");
        adduser.setHourWorked(14);
        
        user.addUser(adduser);
        
        List<User> list = user.getAllUserDetails();
        list.forEach(e -> {
            System.out.println(e);
        });
//        
//        System.out.println(list);
//        System.out.println(user.getUserById(3).getUsername());
        
        
    }
    
}
