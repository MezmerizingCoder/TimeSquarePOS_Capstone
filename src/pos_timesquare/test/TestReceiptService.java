/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.test;

import pos_timesquare.controller.ReceiptService;

/**
 *
 * @author Acer
 */
public class TestReceiptService {
    public static void main(String[] args) {
        ReceiptService rs = new ReceiptService();
        
        rs.getReceiptByCustomerId(1).forEach(e -> {
            System.out.println(e.getSalesPersonId());
        });
        
    }
}
