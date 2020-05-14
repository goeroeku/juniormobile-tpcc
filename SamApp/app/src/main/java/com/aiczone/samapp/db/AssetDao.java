package com.aiczone.samapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aiczone.samapp.model.Asset;

import java.util.List;

@Dao
public interface AssetDao {

    @Query("SELECT * FROM Asset")
    List<Asset> getAll();

    @Query("SELECT * FROM Asset WHERE id=:id")
    Asset getById(Long id);

    @Query("SELECT count(*) FROM Asset")
    Integer count();

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);
}
