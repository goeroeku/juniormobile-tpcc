package com.aiczone.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aiczone.contactapp.adapter.ContactAdapter;
import com.aiczone.contactapp.db.AppDatabase;
import com.aiczone.contactapp.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvContact;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        rvContact = findViewById(R.id.rvContact);
        rvContact.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        dummy data
//        List<Contact> contacts = new ArrayList<>();
//        Contact contact = new Contact();
//        contact.name = "Aic";
//        contact.dateOfBirth = "01-01-2020";
//        contact.profession = "Mahasiswa";
//        contact.gender = "Laki-laki";
//        contact.email = "admin@me.id";
//        contact.phone = "0281";
//        contacts.add(contact);
//        contacts.add(contact);

        initListData();
    }

    private void initListData(){
        List<Contact> contacts = AppDatabase.getInstance(this).contactDao().getAll();

        adapter = new ContactAdapter(this,contacts);
        rvContact.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListData();
    }
}
