/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.model.TransactionHistory;

/**
 *
 * @author Administrator
 */
public class TestTransactionHistory {
    public static void main(String[] args) {
        TransactionHistoryService ths = new TransactionHistoryService();
        TransactionHistory th = new TransactionHistory();
        List<TransactionHistory> list = ths.getAllTransactionHistoryDetails();
        
        

        list.forEach(e->{
            System.out.println(e.getTransactionDate());
            System.out.println(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            if(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString().equals( e.getTransactionDate().toString())){
                System.out.println(e.getTransactionDate());
            }
            
            System.out.println(e.getStatus());
        });
        
//        System.out.println(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        
//        System.out.println(ths.getTransactionByDate(new java.sql.Date(Calendar.getInstance().getTime().getTime())).size());
     
        
//     System.out.println(list.get(0).getProductId());
//     System.out.println(list.get(0).getTransactionDate());
//     System.out.println(list.get(0).getOrders());
//     System.out.println(list.get(0).getTotalPrice());
   
//       th.setProductId(7);
//       th.setTransactionDate("2023-01-02");
//       th.setOrders(12);
//       th.setTotalPrice(6000);
//       
//       ths.addTransactionHistory(th);
//       ths.deleteTransactionHistoryById(1);
//       ths.UpdateTransactionHistory(3, 10, "October-9-2022", 30, 5300);
      //  
      
      String str="2015-03-31";  
      
      java.sql.Date sqldate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {  
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(str);
            System.out.println(date1.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(TestTransactionHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
}
