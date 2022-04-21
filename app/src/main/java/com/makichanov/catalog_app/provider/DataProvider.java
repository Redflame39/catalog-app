package com.makichanov.catalog_app.provider;

import com.makichanov.catalog_app.model.CatalogItem;

import java.util.List;

public interface DataProvider {

    List<CatalogItem> getCatalogItemsSync();
}
