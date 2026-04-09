package com.asif.farmeta;

public class FertilizerModel {
    String crop, unit, date;
    double land, n, p, k;

    public FertilizerModel(String crop, double land, String unit,
                           double n, double p, double k, String date) {
        this.crop = crop;
        this.land = land;
        this.unit = unit;
        this.n = n;
        this.p = p;
        this.k = k;
        this.date = date;
    }
}