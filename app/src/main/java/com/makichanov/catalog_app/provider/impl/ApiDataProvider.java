package com.makichanov.catalog_app.provider.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Cat;
import com.makichanov.catalog_app.CatalogAppContext;
import com.makichanov.catalog_app.db.CatalogAppDatabase;
import com.makichanov.catalog_app.model.CatalogItem;
import com.makichanov.catalog_app.model.Category;
import com.makichanov.catalog_app.repository.CatalogItemRepository;
import com.makichanov.catalog_app.repository.CategoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiDataProvider {

    public List<CatalogItem> getCatalogItems() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fakestoreapi.com/products")
                .build();

        Call call = client.newCall(request);
        CountDownLatch latch = new CountDownLatch(1);
        List<CatalogItem> catalogItems = new ArrayList<>();
        call.enqueue(new Callback() {
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogAppContext catalogAppContext = CatalogAppContext.getInstance(null);
            CatalogItemRepository repository = catalogAppContext.getCatalogItemRepository();

            public void onResponse(Call call, Response response) throws IOException {
                String jsonResponse = response.body().string();
                List<CatalogItem> responseItems = objectMapper.readValue(jsonResponse,
                        new TypeReference<List<CatalogItem>>() { });

                catalogItems.addAll(responseItems);
                latch.countDown();
            }

            public void onFailure(Call call, IOException e) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return catalogItems;
    }

    public List<Category> getCategories() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fakestoreapi.com/products/categories")
                .build();

        Call call = client.newCall(request);
        List<Category> categories = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                CategoryRepository categoryRepository = CatalogAppContext.getInstance(null)
                        .getCategoryRepository();
                List<Category> categoryList = categoryRepository.readAll();
                categories.addAll(categoryList);
                latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonResponse = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> responseItems = objectMapper.readValue(jsonResponse,
                        new TypeReference<List<String>>() { });

                List<Category> categoryList = new ArrayList<>();
                for (String s : responseItems) {
                    Category c = new Category(s);
                    categoryList.add(c);
                }
                categories.addAll(categoryList);
                CategoryRepository categoryRepository = CatalogAppContext.getInstance(null)
                        .getCategoryRepository();
                categoryRepository.deleteAll();
;                categoryRepository.saveAll(categoryList);
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.e("Error", "Caught an exception while waiting for api response");
        }
        return categories;
    }

    public List<CatalogItem> getByCategory(String category) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://fakestoreapi.com/products/category/%s", category);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        List<CatalogItem> catalogItems = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                CatalogAppContext catalogAppContext = CatalogAppContext.getInstance(null);
                CatalogItemRepository dbRepository = catalogAppContext.getDbRepository();
                List<CatalogItem> dbItems = dbRepository.readAll();
                catalogItems.addAll(dbItems);
                latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonResponse = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                List<CatalogItem> responseItems = objectMapper.readValue(jsonResponse,
                        new TypeReference<List<CatalogItem>>() { });

                catalogItems.addAll(responseItems);

                CatalogItemRepository dbRepository = CatalogAppContext.getInstance(null)
                        .getDbRepository();
                dbRepository.deleteAll();
                dbRepository.saveAll(responseItems);

                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return catalogItems;
    }
}
