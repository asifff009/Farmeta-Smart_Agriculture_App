package com.asif.farmeta;

public class OrderModel {

    private String cropName;
    private String quantity;
    private String price;
    private String farmerName;

    public OrderModel(String cropName, String quantity, String price, String farmerName) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.price = price;
        this.farmerName = farmerName;
    }

    public String getCropName() { return cropName; }
    public String getQuantity() { return quantity; }
    public String getPrice() { return price; }
    public String getFarmerName() { return farmerName; }
}
