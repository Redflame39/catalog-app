package com.makichanov.catalog_app.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.model.Category;
import com.makichanov.catalog_app.repository.CatalogItemRepository;
import com.makichanov.catalog_app.repository.CategoryRepository;

@Database(entities = {CatalogItem.class, Category.class}, version = 1)
public abstract class CatalogAppDatabase extends RoomDatabase {

    public abstract CatalogItemRepository catalogItemRepository();

    public abstract CategoryRepository categoryRepository();

}
