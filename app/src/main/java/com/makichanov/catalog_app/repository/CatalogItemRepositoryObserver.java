package com.makichanov.catalog_app.repository;

public interface CatalogItemRepositoryObserver {

    void onCreate();

    void onUpdate();

    void onDelete();

}
