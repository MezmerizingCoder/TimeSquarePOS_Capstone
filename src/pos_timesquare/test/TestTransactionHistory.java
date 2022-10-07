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
   
       th.setProductId(17);
       th.setTransactionDate("October-20-2022");
       th.setOrders(12);
       th.setTotalPrice(2002);
       
       ths.addTransactionHistory(th);
       ths.deleteTransactionHistoryById(1);
       ths.UpdateTransactionHistory(3, 10, "October-9-2022", 30, 5300);
      //  
   
    }
}
