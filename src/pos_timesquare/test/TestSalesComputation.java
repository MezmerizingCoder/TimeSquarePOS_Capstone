/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.ArrayList;
import java.util.List;
import pos_timesquare.controller.SalesComputationController;
/**
 *
 * @author DUDA
 */
public class TestSalesComputation {
    
    public static void main(String[] args) {
    
        SalesComputationController scom  = new SalesComputationController();
      
          
//          scom.getSumofDailySales("2022-09-01");
//          scom.getSumofMonthlySales("2022-09-01");  //Set value when the computation start
          System.out.println(scom.getSumofAnnuallySales("2015")); //Set value when the computation start  
}
}
