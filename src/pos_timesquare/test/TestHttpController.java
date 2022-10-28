/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import pos_timesquare.controller.HttpController;

/**
 *
 * @author Acer
 */
public class TestHttpController {
    public static void main(String[] args) {
        try{
//            HttpController.sendHttpGETRequest("http://localhost:8080/api/email");
            HttpController.postHttpRequest("http://localhost:8080/api/test");
//            HttpController.doughnutChart();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
