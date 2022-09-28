/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import pos_timesquare.controller.LoginFunctionService;
import pos_timesquare.model.User;

/**
 *
 * @author Administrator
 */
public class TestLoginFunction {
    public static void main(String[] args) {

     LoginFunctionService ls = new LoginFunctionService();
  //   User u = new User();
     
     
   //  u.setUsername("pj");
   //  u.setPassword("paul123");
   // u.setRole("employee");
   //  ls.getMatchAccount("ericka", "1226");
     ls.getMatchAccount("pj", "paul123");
   //ls.getMatchAccount(u);
   // ls.getUserType(u);
    }
}
