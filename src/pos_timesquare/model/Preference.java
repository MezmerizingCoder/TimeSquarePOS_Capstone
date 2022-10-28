
package pos_timesquare.model;


public class Preference {
    private int id;
    private int user_id;
    private String themes;
    
    public int getId() {
        return id;
    }
    
     public void setId(int id) {
        this.id = id;
    }
     
     public int getUserId(){
         return user_id;
  
     }
     public void setUserId(int user_id){
         this.user_id = user_id;
     }
     public String getThemes(){
         return themes;
     }
     public void setThemes(String themes){
         this.themes = themes;
     }
}
