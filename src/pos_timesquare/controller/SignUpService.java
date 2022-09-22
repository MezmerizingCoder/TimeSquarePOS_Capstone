
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.User;

/**
 *
 * @author Kris Jurist Fajardo
 */
public class SignUpService {
     public String username;
    public String password;
  Connection connection = null;
    PreparedStatement pst = null;
  
     Connection conn = getConnection();
    public User addAccount(User user){
          
               try {
            
            pst = conn.prepareStatement("INSERT INTO User VALUES(?, ?, ?,?,?)");
            
            pst.setString(1, null);
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
              pst.setString(4, user.getName());
            pst.setString(5, user.getRole());
            
            pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    return user;
    
    }
   
   
}
    
    


