package com.asif.farmeta;

public class MarketModel {
    String crop, price, district, date;

    public MarketModel(String crop, String price, String district, String date){
        this.crop = crop;
        this.price = price;
        this.district = district;
        this.date = date;
    }

    public String getCrop(){ return crop; }
    public String getPrice(){ return price; }
    public String getDistrict(){ return district; }
    public String getDate(){ return date; }
}
