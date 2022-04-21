package com.makichanov.catalog_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CatalogItem {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "image")
    private String image;

    @Ignore
    private Rating rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
