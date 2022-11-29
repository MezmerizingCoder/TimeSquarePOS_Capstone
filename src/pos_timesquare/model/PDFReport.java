/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.model;

/**
 *
 * @author Acer
 */
public class PDFReport {
    String productName;
    int productItems;
    float productPrice;
    String productDate;

    public PDFReport(String productName, int productItems, float productPrice, String productDate) {
        this.productName = productName;
        this.productItems = productItems;
        this.productPrice = productPrice;
        this.productDate = productDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductItems() {
        return productItems;
    }

    public void setProductItems(int productItems) {
        this.productItems = productItems;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }
}
