package com.aiczone.samapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aiczone.samapp.adapter.AssetAdapter;
import com.aiczone.samapp.db.AppDatabase;
import com.aiczone.samapp.model.Asset;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvAsset;
    private AssetAdapter adapter;

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

        rvAsset = findViewById(R.id.rvAsset);
        rvAsset.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initListData();
    }

    private void initListData(){

        List<Asset> assets = AppDatabase.getInstance(this).assetDao().getAll();

        adapter = new AssetAdapter(this,assets);
        rvAsset.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListData();
    }
}
