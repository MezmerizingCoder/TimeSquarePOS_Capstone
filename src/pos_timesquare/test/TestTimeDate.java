/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.model.TransactionHistory;

/**
 *
 * @author Acer
 */
public class TestTimeDate {
    public static void main(String[] args) {
//        try {
//            Files.createDirectories(Paths.get("../CreatedDirectory"));
//        } catch (IOException ex) {
//            Logger.getLogger(TestTimeDate.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String cwd = System.getProperty("user.dir");
//        File dirAboveCws = new File(cwd).getParentFile();
//        
//        System.out.println(new File(cwd + "/img/product/"));

        TransactionHistoryService ths= new TransactionHistoryService();
        
        ths.getTransactionByReceiptId(31).forEach(e -> {
            System.out.println(e.getTransactionDate());
        });
        
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        
        Date dt;
        try {
            dt = sdf.parse("90");
            sdf = new SimpleDateFormat("HH:mm");
            System.out.println(sdf.format(dt));
        } catch (ParseException ex) {
            Logger.getLogger(TestTimeDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        System.out.println( getDateDiff(new java.sql.Date(Calendar.getInstance().getTime().getTime()), new java.sql.Date(Calendar.getInstance().getTime().getTime()),TimeUnit.SECONDS) );

    }
    
    
    
}
