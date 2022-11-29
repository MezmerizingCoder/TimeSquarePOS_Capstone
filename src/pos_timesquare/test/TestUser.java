/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        adduser.setImage("/img/profile/profile1.jpg");
        adduser.setHourWorked(14);
        adduser.setGender("male");
        
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        
        adduser.setBirthdate(date);
        
//        user.updateUser(3, adduser);
        
//        List<User> list = user.getAllUserDetails();
//        list.forEach(e -> {
////            java.sql.Date date2=new java.sql.Date(e.getBirthdate().getDate());
//            System.out.println(e.getBirthdate().getDate());
//        });
//        
        System.out.println(user.getUserById(3).getBirthdate());
        
        
        LocalDate date2 = Instant.ofEpochMilli(user.getUserById(3).getBirthdate().getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
        
        System.out.println( Period.between( date2, 
                date.toLocalDate() ).getYears());
        
        
//        System.out.println(String.valueOf(Period.between( date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
//                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears()));

//        System.out.println(list);
//        System.out.println(user.getUserById(3).getUsername());

//        Date membershipDate;
//        try {
//            membershipDate = new SimpleDateFormat("MMMM d, yyyy").parse(adduser.getMembershipDate());
//            System.out.println(membershipDate);
//           
//        } catch (ParseException ex) {
//            
//        }

        try {
            
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date3 = format.parse(user.getUserById(8).getMembershipDate());
            
            String membershipDate = format.format(date3);
            System.out.println(date3); // Sat Jan 02 00:00:00 GMT 2010
        } catch (ParseException ex) {
            
        }
        
    }
    
}
