/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.model;

/**
 *
 * @author Administrator
 */
public class ServiceTickets {
    private int id;
    private String customerName;
    private String defects;
    private float price;
    private String walkInDate;
    private String estimateFinish;
    private String status;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDefects() {
        return defects;
    }

    public void setDefects(String defects) {
        this.defects = defects;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWalkInDate() {
        return walkInDate;
    }

    public void setWalkInDate(String walkInDate) {
        this.walkInDate = walkInDate;
    }
    
    public String getEstimateFinish() {
        return estimateFinish;
    }

    public void setEstimateFinish(String estimateFinish) {
        this.estimateFinish = estimateFinish;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}