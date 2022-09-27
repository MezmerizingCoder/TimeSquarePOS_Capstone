/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import java.util.List;
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
  //   List<TransactionHistory> list = ths.getAllTransactionHistoryDetails();
        
   // System.out.println(list.get(0).getProdcutid());
   //  System.out.println(list.get(0).getTransactionDate());
   //  System.out.println(list.get(0).getOrders());
   //  System.out.println(list.get(0).getTotalPrice());
   
       th.setProductId(13);
       th.setTransactionDate("October-20-2022");
       th.setOrders(3);
       th.setTotalPrice(3200);
       
       ths.addTransaction(th);
       ths.deleteTransactionById(2);
       ths.UpdateTransaction(3, 10, "October-7-2022", 30, 5400);
      //  
   
    }
}
