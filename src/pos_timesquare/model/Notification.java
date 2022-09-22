/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.model;

/**
 *
 * @author Merryjane A Alis
 */
public class Notification {
    private int id;
    private int productid;
    private String date;
    private String time;
    
    
     public int getId() {
        return id;
    }
    
     public void setId(int id) {
        this.id = id;
    }
      public int getProductId() {
            return productid;
    }

    public void setProductId(int id) {
        this.productid = id;
    }

        public String getDate() {
            return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
        public String getTime() {
            return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}