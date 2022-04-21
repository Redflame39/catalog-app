package com.makichanov.catalog_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Rating {

    @ColumnInfo(name = "rate")
    private Double rate;

    @ColumnInfo(name = "count")
    private double count;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

}
