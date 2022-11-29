/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_timesquare.controller.CustomerService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.model.Customer;
import pos_timesquare.view.MainFrame;
import static pos_timesquare.view.MainFrame.jLabel76;

/**
 *
 * @author Acer
 */
public class TestCustomerService {
    public static void main(String[] args) {
        CustomerService cs = new CustomerService();
        
//        cs.getAllCustomer();
        
//        Customer customer = new Customer();
//        customer.setName("Faustino");
//        customer.setAddress("167 wawa");
//        customer.setBirthDate("Aug 20 2000");
//        customer.setContactNum("09999999");
//        customer.setGender("Male");
//        customer.setImage("");
//        customer.setMembershipDate("Date now");
//        customer.setStatus("Active");
//        
//        cs.addCustomer(customer);
//
//        ReceiptService rs = new ReceiptService();
//        
//        System.out.println(rs.getReceiptByCustomerId(1));


//        System.out.println(cs.getCustomerById(1));
        Customer customer = cs.getCustomerById(1);
        System.out.println(customer);
        
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
            Date date3;
            date3 = format.parse(customer.getBirthDate());

//                    String membershipDate = format2.format(date3);
            System.out.println(date3);

            long millis = System.currentTimeMillis();        
            java.sql.Date currentDate = new java.sql.Date(millis);        

            LocalDate date = Instant.ofEpochMilli(new java.sql.Date(date3.getTime()).getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            System.out.println(String.valueOf(Period.between(date, currentDate.toLocalDate()).getYears()));


        } catch (ParseException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
