package com.asif.farmeta;

public class CropModel {
    String name;
    String image;

    public CropModel(String name, String image){
        this.name = name;
        this.image = image;
    }

    public String getName(){ return name; }
    public String getImage(){ return image; }
}