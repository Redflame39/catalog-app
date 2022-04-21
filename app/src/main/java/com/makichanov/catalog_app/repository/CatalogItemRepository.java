package com.makichanov.catalog_app.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.makichanov.catalog_app.model.CatalogItem;

import java.util.Collection;
import java.util.List;

@Dao
public interface CatalogItemRepository {

    @Query("select * from CatalogItem")
    List<CatalogItem> readAll();

    @Query("select * from CatalogItem where id = :id")
    CatalogItem read(Integer id);

    @Insert
    void save(CatalogItem catalogItem);

    @Insert
    void saveAll(Collection<CatalogItem> catalogItems);

    @Update
    void update(CatalogItem replacement);

    @Update
    void replaceAll(Collection<CatalogItem> catalogItems);

    //@Delete
    //void delete (Integer id);

    @Query("delete from CatalogItem")
    void deleteAll();

}
