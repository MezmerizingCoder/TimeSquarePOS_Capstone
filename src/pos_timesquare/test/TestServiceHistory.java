/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.ServiceHistoryService;
import pos_timesquare.model.ServiceHistory;

/**
 *
 * @author Administrator
 */
public class TestServiceHistory {
    public static void main(String[] args) {
        ServiceHistoryService shs = new ServiceHistoryService();
        ServiceHistory sh = new ServiceHistory();
        
      List<ServiceHistory> list = shs.getAllServiceHistoryDetails();
      
      //  System.out.println(list.get(0).getId());
     //   System.out.println(list.get(0).getCustomerName());
     //   System.out.println(list.get(0).getDefects());
     //   System.out.println(list.get(0).getPrice());
     //   System.out.println(list.get(0).getWalkInDate());
     //   System.out.println(list.get(0).getEstimateFinish());
     //   System.out.println(list.get(0).getStatus());
       
    //    sh.setCustomerName("ErickaSF");
    //    sh.setDefects("palalalaa");
    //    sh.setPrice(2690);
    //    sh.setWalkInDate("September-10-2022");
    //    sh.setEstimateFinish("September-17-2022");
    //    sh.setStatus("Done");
        
    //  shs.addServiceHistory(sh);
   //   shs.deleteServiceHistoryById(5);
   //   shs.UpdateServiceHistory(2, "PaulJustineFaustino", "sfddgdgda", 2340, "August-20-2020", "August-25-2020", "Done");

        
    }
}