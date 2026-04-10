package com.asif.farmeta;

public class BuyerPostModel {

    String name, email, contact, location, crop, quantity, price;

    public BuyerPostModel(String name, String email, String contact,
                          String location, String crop,
                          String quantity, String price) {

        this.name = name;
        this.email = email;
        this.contact = contact;
        this.location = location;
        this.crop = crop;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getLocation() { return location; }
    public String getCrop() { return crop; }
    public String getQuantity() { return quantity; }
    public String getPrice() { return price; }
}