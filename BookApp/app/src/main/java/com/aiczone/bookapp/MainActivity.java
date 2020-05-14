package com.aiczone.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aiczone.bookapp.adapter.BookAdapter;
import com.aiczone.bookapp.db.AppDatabase;
import com.aiczone.bookapp.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvContact;
    private BookAdapter adapter;

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

        rvContact = findViewById(R.id.rvBook);
        rvContact.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initListData();
    }

    private void initListData(){

        List<Book> books = AppDatabase.getInstance(this).bookDao().getAll();

        adapter = new BookAdapter(this,books);
        rvContact.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListData();
    }
}
