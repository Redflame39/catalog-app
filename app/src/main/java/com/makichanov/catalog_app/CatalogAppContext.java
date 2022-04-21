package com.makichanov.catalog_app;

import android.content.Context;

import androidx.room.Room;

import com.makichanov.catalog_app.db.CatalogAppDatabase;
import com.makichanov.catalog_app.repository.CatalogItemCollectionRepository;
import com.makichanov.catalog_app.repository.CatalogItemRepository;
import com.makichanov.catalog_app.repository.CategoryRepository;
import com.makichanov.catalog_app.repository.impl.CatalogItemRepositoryImpl;

public class CatalogAppContext {

    private static final CatalogAppContext instance = new CatalogAppContext();
    private static Context applicationContext;
    private final CatalogItemCollectionRepository catalogItemRepository;
    private CatalogAppDatabase database ;

    private CatalogAppContext() {
        catalogItemRepository = new CatalogItemRepositoryImpl();
    }

    public static CatalogAppContext getInstance(Context context) {
        if (applicationContext == null) {
            applicationContext = context;
        }
        return instance;
    }

    public CatalogItemCollectionRepository getCatalogItemRepository() {
        return catalogItemRepository;
    }

    public CatalogItemRepository getDbRepository() {
        if (database == null) {
            database = Room.databaseBuilder(applicationContext, CatalogAppDatabase.class,
                    "catalog_db").build();
        }
        return database.catalogItemRepository();
    }

    public CategoryRepository getCategoryRepository() {
        if (database == null) {
            database = Room.databaseBuilder(applicationContext, CatalogAppDatabase.class,
                    "catalog_db").build();
        }
        return database.categoryRepository();
    }
}
