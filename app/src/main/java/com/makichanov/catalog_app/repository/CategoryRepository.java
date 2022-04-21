package com.makichanov.catalog_app.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.makichanov.catalog_app.model.Category;

import java.util.Collection;
import java.util.List;

@Dao
public interface CategoryRepository {

    @Query("select * from Category")
    List<Category> readAll();

    @Insert
    void saveAll(Collection<Category> categories);

    @Query("delete from Category")
    void deleteAll();

}
