package com.asif.farmeta;

public class CropModel {

    String name, category, location, farmer, price, quantity;

    public CropModel(String name, String category, String location,
                     String farmer, String price, String quantity) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.farmer = farmer;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getFarmer() { return farmer; }
    public String getPrice() { return price; }
    public String getQuantity() { return quantity; }
}
