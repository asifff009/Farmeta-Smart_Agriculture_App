package com.asif.farmeta;

public class CropModel {
    private String firstName, lastName, cropName, type, quantity, price, contact, address, image;

    public CropModel(String firstName, String lastName, String cropName,
                     String quantity, String price, String contact,
                     String address, String image, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.cropName = cropName;
        this.quantity = quantity;
        this.price = price;
        this.contact = contact;
        this.address = address;
        this.image = image;
        this.type = type;
    }

    public String getFullName(){ return firstName + " " + lastName; }
    public String getCropName(){ return cropName; }
    public String getType(){ return type; }
    public String getQuantity(){ return quantity; }
    public String getPrice(){ return price; }
    public String getContact(){ return contact; }
    public String getAddress(){ return address; }
    public String getImage(){ return image; }
}