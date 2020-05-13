package com.aiczone.contactapp.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Contact {

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
