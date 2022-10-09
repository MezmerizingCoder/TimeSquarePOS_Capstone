/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.NotificationService;
import pos_timesquare.model.Notification;


/**
 *
 * @author Merryjane A Alis
 */
public class TestNotification {
   public static void main(String[] args) {
     NotificationService nfs = new NotificationService();
      Notification n = new Notification();
     List<Notification> list = nfs.getAllNotificationDetails();
        
//   System.out.println(list.get(1).getId());
//    System.out.println(list.get(1).getDate());
//   System.out.println(list.get(1).getTime());
    
    
       
        n.setProductId(5);
        n.setDate("9/27/22");
        n.setTime("06:00pm");
        
       nfs.addNotification(n);
       nfs.deleteNotificationById(3);
       nfs.UpdateNotification(2 , 2, "9/24/22" , "07:00pm");
   }
}