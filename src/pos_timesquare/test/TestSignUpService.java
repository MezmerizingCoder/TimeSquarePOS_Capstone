
package pos_timesquare.test;
import pos_timesquare.controller.SignUpService;
import pos_timesquare.model.User;
/**
 *
 * @author Kris Jurist Fajardo
 */
public class TestSignUpService {
     public static void main(String[] args) {
         SignUpService sign = new SignUpService();
         User adduser = new User();
    
     adduser.setUsername("dean");
     adduser.setPassword("winchester");
         adduser.setName("DEAN");
           adduser.setRole("role");
     sign.addAccount(adduser);
     
     }
}
