/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import jdk.jfr.Timestamp;
import pos_timesquare.controller.NotificationService;
import pos_timesquare.model.Notification;


/**
 *
 * @author Merryjane A Alis
 */
public class TestNotification {
   public static void main(String[] args) throws ParseException {
     NotificationService nfs = new NotificationService();
      Notification n = new Notification();
     List<Notification> list = nfs.getAllNotificationDetails();
        
    System.out.println(list.get(0).getId());
    System.out.println(list.get(0).getDate());
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    Date parsedDate = dateFormat.parse(list.get(0).getDate());
    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    
    System.out.println(timestamp);
    
    
    DateFormat formatter = new SimpleDateFormat("hh:mm a");
    String time = formatter.format(new Date(timestamp.getTime()));
    System.out.println(time);
    
    
    System.out.println(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
       
//        n.setProductId(1);
//        n.setDate(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString());
//        
//       nfs.addNotification(n);
//       nfs.deleteNotificationById(3);
//       nfs.UpdateNotification(2 , 2, "9/24/22" , "07:00pm");
   }
}