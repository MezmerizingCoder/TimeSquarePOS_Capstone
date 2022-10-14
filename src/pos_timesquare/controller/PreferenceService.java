
package pos_timesquare.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Preference;


public class PreferenceService {
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public PreferenceService(){
        
       Connection conn = getConnection();
        try{
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Preference", null);
            
            if(tables.next())
            {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Preference","user_id").next()){
                Statement stmt  = conn.createStatement();
                String sql = "ALTER TABLE Preference ADD user_id INTEGER";
                stmt.executeUpdate(sql);  
            }
            if(!dbmd.getColumns(null, null, "Preference","themes").next()){
                Statement stmt  = conn.createStatement();
                String sql = "ALTER TABLE Preference ADD themes TEXT";
                stmt.executeUpdate(sql);
            
        }
            }
        
        else{
                System.out.println("Not Exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Preference ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                "user_id INTEGER NOT NULL\n"+
                "REFERENCES User ( id ) MATCH FULL,"+
                "themes TEXT,";
                
                stmt.executeUpdate(sql); 
                System.out.println("Created table in given database...");
                conn.close();
                }
        conn.close();
    }
    catch (SQLException ex){
    Logger.getLogger(Preference.class.getName()).log(Level.SEVERE,null,ex);

    }
         
}
    public List<Preference> getAllPreferenceDetails(){
    
    Connection conn = getConnection();
    List<Preference> preference = new ArrayList<>();
    
    
     try{
     
        System.out.println("Getting data");
        pst = conn.prepareStatement("SELECT id, user_id, themes FROM Preference");
        rs = pst.executeQuery();
        
        while(rs.next()){
             
               Preference preferences =  new Preference();
                preferences.setId(Integer.parseInt(rs.getString("id")));
                preferences.setUserId(Integer.parseInt(rs.getString("user_id")));
                preferences.setThemes(rs.getString("themes"));
               
               preference.add(preferences);
        }
            conn.close();
            return preference;
            
            }catch (SQLException ex) {
            Logger.getLogger(PreferenceService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
    }
            
    }
    
    public Preference getPreferenceById(int id){
         Connection conn = getConnection();
        Preference preference = new Preference();
         
    try {
         
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT id, user_id, themes FROM Preference WHERE id ==" + id);
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                preference.setId(Integer.parseInt(rs.getString("id")));
                preference.setUserId(Integer.parseInt(rs.getString("user_Id")));
                preference.setThemes(rs.getString("themes"));
                
               
            }
            conn.close();
            return preference;  
            
            }catch (SQLException ex) {
              return null;
            }
    }
    
      public void addPreference(Preference preference){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Preference VALUES(?, ?, ?)");
            
            pst.setString(1, null);
            pst.setInt(2, preference.getUserId());
            pst.setString(3, preference.getThemes());
        
         pst.executeUpdate();
            
            System.out.println("Add Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceService.class.getName()).log(Level.SEVERE, null, ex);}
}
      
      public void deletePreferenceById(int id){
        try {
           Connection conn = getConnection();
            
            pst = conn.prepareStatement("DELETE FROM Preference WHERE id = ?");
            
            pst.setInt(1, id);
            
            pst.executeUpdate();
            
            System.out.println("Delete Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      }
      
      public void updatePreference(int user_id, String themes )
      {
          try{
          Connection conn = getConnection();
          Preference preference = new Preference();
            pst = conn.prepareStatement("UPDATE Preference SET themes =?  WHERE user_id =?");
            
            pst.setString(1, themes);
            pst.setInt(2, user_id);
       
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceService.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
      }
}
    
