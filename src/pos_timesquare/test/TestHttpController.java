/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_timesquare.controller.HttpController;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.model.TransactionHistory;
import static pos_timesquare.view.MainFrame.jYearChooser1;

/**
 *
 * @author Acer
 */
public class TestHttpController {
    public static void main(String[] args) {
        
            //        try{
////            HttpController.sendHttpGETRequest("http://localhost:8080/api/email");
//            HttpController.postHttpRequest("http://localhost:8080/api/test");
////            HttpController.doughnutChart();
//        }catch(Exception e){
//            System.out.println(e);
//        }

//            Calendar cal = Calendar.getInstance();
//            cal.setTime(e.getTransactionDate());
//            int month = cal.get(Calendar.MONTH);
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//            int year = cal.get(Calendar.YEAR);

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        TransactionHistoryService ths = new TransactionHistoryService();
        
        List<TransactionHistory> th = ths.getAllTransactionHistoryDetails();
        ProductService ps = new ProductService();
        
        th.forEach(e -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            for(int i = 1; i <= 7 ; i++){
                cal.add(Calendar.DATE, -1);
                java.sql.Date date2 = new java.sql.Date(cal.getTime().getTime());

//                System.out.println(date2.toString());
//                System.out.println(e.getTransactionDate().toString());
                
                if(date2.toString().equals(e.getTransactionDate().toString())){
                    System.out.println(ps.getProductById(e.getProductId()).getName());
                }
            }
        });

//        Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//        for(int i = 1; i <= 7 ; i++){
//            cal.add(Calendar.DATE, -1);
//            java.sql.Date date2 = new java.sql.Date(cal.getTime().getTime());
//
//            System.out.println(date2);
//        }

//        try {
//            String dt = "2022-11-07";  // Start date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar c = Calendar.getInstance();
//            c.setTime(sdf.parse(dt));
//            c.add(Calendar.DATE, 1);  // number of days to add
//            dt = sdf.format(c.getTime()); 
//            System.out.println(dt);
//        } catch (ParseException ex) {
//            Logger.getLogger(TestHttpController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        

    }
}
