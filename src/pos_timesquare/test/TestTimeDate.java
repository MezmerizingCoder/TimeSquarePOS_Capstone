/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String cwd = System.getProperty("user.dir");
        File dirAboveCws = new File(cwd).getParentFile();
        
        System.out.println(new File(cwd + "/img/product/"));

    }
}
