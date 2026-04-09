package com.asif.farmeta;

public class BuyerModel {

    String name, crop, quantity, price, contact, location;

    public BuyerModel(String name, String crop, String quantity,
                      String price, String contact, String location) {
        this.name = name;
        this.crop = crop;
        this.quantity = quantity;
        this.price = price;
        this.contact = contact;
        this.location = location;
    }

    public String getName() { return name; }
    public String getCrop() { return crop; }
    public String getQuantity() { return quantity; }
    public String getPrice() { return price; }
    public String getContact() { return contact; }
    public String getLocation() { return location; }
}