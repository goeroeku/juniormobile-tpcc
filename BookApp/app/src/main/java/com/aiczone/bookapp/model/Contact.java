package com.aiczone.bookapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contact implements  Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;
    public String name;
    public String dateOfBirth;
    public String profession;
    public String gender;
    public String email;
    public String phone;

}
