package com.makichanov.catalog_app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.makichanov.catalog_app.CatalogAppContext;
import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.model.Category;
import com.makichanov.catalog_app.provider.impl.ApiDataProvider;
import com.makichanov.catalog_app.repository.CatalogItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {

    private CatalogAppContext catalogAppContext = CatalogAppContext.getInstance(null);
    private CatalogItemRepository repository = catalogAppContext.getCatalogItemRepository();
    private ApiDataProvider provider = new ApiDataProvider();
    private MutableLiveData<List<CatalogItem>> catalogItems;
    private MutableLiveData<List<String>> categoriesLiveData;

    public LiveData<List<CatalogItem>> getCatalogItems(String category) {
        if (catalogItems == null) {
            catalogItems = new MutableLiveData<>();
        }
        loadCatalogItems(category);
        catalogItems.setValue(repository.readAll());
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItem> catalogItems) {
        repository.replaceAll(catalogItems);
        this.catalogItems.setValue(catalogItems);
    }

    public LiveData<List<String>> getCategories() {
        if (categoriesLiveData == null) {
            categoriesLiveData = new MutableLiveData<>();
        }
        List<String> categories = loadCategories();
        categoriesLiveData.setValue(categories);
        return categoriesLiveData;
    }

    public String getSelectedCategory(int selectedCategoryId) {
        return categoriesLiveData.getValue().get(selectedCategoryId);
    }

    private void loadCatalogItems(String category) {
        List<CatalogItem> catalogItems = provider.getByCategory(category);
        repository.replaceAll(catalogItems);
    }

    private List<String> loadCategories() {
        List<String> categoriesStrings = new ArrayList<>();
        List<Category> categoriesList =  provider.getCategories();
        for (Category c : categoriesList) {
            categoriesStrings.add(c.getName());
        }
        return categoriesStrings;
    }



}