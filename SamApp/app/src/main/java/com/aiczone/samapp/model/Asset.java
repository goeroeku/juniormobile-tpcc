package com.aiczone.samapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Asset implements  Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;
    public String code;
    public String name;
    public String dateOfEntry;
    public String location;
    public int qty;
    public String category;

}
