/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.ServiceTicketsService;
import pos_timesquare.model.ServiceTickets;

/**
 *
 * @author Administrator
 */
public class TestServiceTickets {
    public static void main(String[] args) {
        ServiceTicketsService sts = new ServiceTicketsService();
        ServiceTickets st = new ServiceTickets();
        
   //   List<ServiceTickets> list = shs.getAllServiceHistoryDetails();
      
      //  System.out.println(list.get(0).getId());
     //   System.out.println(list.get(0).getCustomerName());
     //   System.out.println(list.get(0).getDefects());
     //   System.out.println(list.get(0).getPrice());
     //   System.out.println(list.get(0).getWalkInDate());
     //   System.out.println(list.get(0).getEstimateFinish());
     //   System.out.println(list.get(0).getStatus());
       
       st.setCustomerName("EraSF");
       st.setDefects("palalalaa");
       st.setPrice(2690);
       st.setWalkInDate("September-20-2022");
       st.setEstimateFinish("September-27-2022");
      st.setStatus("Done");
        
     sts.addServiceTickets(st);
     sts.deleteServiceTicketsById(1);
     sts.UpdateServiceTickets(2, "PaulJustineFaustino", "sfddgdgda", 2340, "August-20-2020", "August-25-2020", "Done");

        
    }
}