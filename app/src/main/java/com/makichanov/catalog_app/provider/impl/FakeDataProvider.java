package com.makichanov.catalog_app.provider.impl;

import com.github.javafaker.Faker;
import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.provider.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class FakeDataProvider implements DataProvider {

    public List<CatalogItem> getCatalogItemsSync() {
        List<CatalogItem> catalogItems = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            CatalogItem catalogItem = new CatalogItem();
            catalogItem.setTitle(faker.name().name());
            catalogItems.add(catalogItem);
        }
        return catalogItems;
    }
}
