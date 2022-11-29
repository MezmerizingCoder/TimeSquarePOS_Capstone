/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.model;

/**
 *
 * @author Acer
 */
public class ServiceReport {
    String serviceId;
    Float servicePrice;
    String serviceDate;

    public ServiceReport(String serviceId, Float servicePrice, String serviceDate) {
        this.serviceId = serviceId;
        this.servicePrice = servicePrice;
        this.serviceDate = serviceDate;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
}
