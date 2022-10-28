
package pos_timesquare.test;

import java.util.List;
import pos_timesquare.controller.PreferenceService;
import pos_timesquare.model.Preference;


public class TestPreference {
    public static void main(String[] args) {
     PreferenceService ps = new PreferenceService();
    Preference p = new Preference();
  List<Preference> list = ps.getAllPreferenceDetails();
 //System.out.println(list.get(1).getId());
 // System.out.println(list.get(1).getUserId());
 // System.out.println(list.get(1).getThemes());
  
    p.setThemes("Dark");
 // ps.addPreference(p);
  ps.deletePreferenceById(4);
  //ps.updatePreference( 0, "Dark");
    }
}
