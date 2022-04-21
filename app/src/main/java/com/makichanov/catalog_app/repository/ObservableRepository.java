package com.makichanov.catalog_app.repository;

public interface ObservableRepository {

    void addObserver(CatalogItemRepositoryObserver observer);

    boolean removeObserver(CatalogItemRepositoryObserver observer);

}
