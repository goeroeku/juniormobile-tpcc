package com.aiczone.bookapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aiczone.bookapp.model.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Book")
    List<Book> getAll();

    @Query("SELECT * FROM Book WHERE id=:id")
    Book getById(Long id);

    @Query("SELECT count(*) FROM Book")
    Integer count();

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);
}
