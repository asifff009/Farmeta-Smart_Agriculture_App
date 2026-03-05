package com.asif.farmeta;

public class CropModel {
    private String firstName, middleName, lastName, cropName, quantity, price, contact, address, image;

    public CropModel(String firstName, String middleName, String lastName, String cropName,
                     String quantity, String price, String contact, String address, String image){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.cropName = cropName;
        this.quantity = quantity;
        this.price = price;
        this.contact = contact;
        this.address = address;
        this.image = image;
    }

    public String getFullName(){ return firstName + " " + middleName + " " + lastName; }
    public String getCropName(){ return cropName; }
    public String getQuantity(){ return quantity; }
    public String getPrice(){ return price; }
    public String getContact(){ return contact; }
    public String getAddress(){ return address; }
    public String getImage(){ return image; }
}