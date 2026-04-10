package com.asif.farmeta;

import java.util.ArrayList;

public class BuyerPostModel {

    public String id, name, email, contact, location;
    public ArrayList<CropItem> items;

    public BuyerPostModel(String id, String name, String email,
                          String contact, String location,
                          ArrayList<CropItem> items) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.location = location;
        this.items = items;
    }

    public static class CropItem {
        public String crop, quantity, price;

        public CropItem(String crop, String quantity, String price) {
            this.crop = crop;
            this.quantity = quantity;
            this.price = price;
        }
    }
}