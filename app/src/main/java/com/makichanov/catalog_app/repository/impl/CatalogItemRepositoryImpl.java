package com.makichanov.catalog_app.repository.impl;

import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.repository.CatalogItemCollectionRepository;
import com.makichanov.catalog_app.repository.CatalogItemRepository;
import com.makichanov.catalog_app.repository.CatalogItemRepositoryObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CatalogItemRepositoryImpl implements CatalogItemCollectionRepository {

    private final List<CatalogItem> items = new ArrayList<>();
    private List<CatalogItemRepositoryObserver> observers = new ArrayList<>();

    public CatalogItemRepositoryImpl() {
    }

    public CatalogItemRepositoryImpl(List<CatalogItem> items) {
        this.items.addAll(items);
    }

    @Override
    public List<CatalogItem> readAll() {
        return items;
    }

    @Override
    public CatalogItem read(Integer id) {
        return items.get(id);
    }

    @Override
    public void save(CatalogItem catalogItem) {
        items.add(catalogItem);
        notifyCreated();
    }

    @Override
    public void saveAll(Collection<CatalogItem> catalogItems) {
        items.addAll(catalogItems);
        notifyCreated();
    }

    @Override
    public void update(CatalogItem replacement) {
        //CatalogItem catalogItem = items.set(replacement);
        notifyUpdated();
    }

    @Override
    public void replaceAll(Collection<CatalogItem> catalogItems) {
        items.clear();
        items.addAll(catalogItems);
        notifyUpdated();
    }

    @Override
    public void deleteAll() {
        items.clear();
    }

    public void addObserver(CatalogItemRepositoryObserver observer) {
        observers.add(observer);
    }

    public boolean removeObserver(CatalogItemRepositoryObserver observer) {
        return observers.remove(observer);
    }

    private void notifyCreated() {
        for (CatalogItemRepositoryObserver observer : observers) {
            observer.onCreate();
        }
    }

    private void notifyUpdated() {
        for (CatalogItemRepositoryObserver observer : observers) {
            observer.onUpdate();
        }
    }

    private void notifyDeleted() {
        for (CatalogItemRepositoryObserver observer : observers) {
            observer.onDelete();
        }
    }

}
