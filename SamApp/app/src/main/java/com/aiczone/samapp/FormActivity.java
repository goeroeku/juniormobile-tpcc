package com.aiczone.samapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aiczone.samapp.db.AppDatabase;
import com.aiczone.samapp.model.Asset;
import com.aiczone.samapp.utils.Helper;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText etCode, etName, etDateOfEntry, etLocation, etQty;
    private Spinner spCategory;

    // data
    private String dateOfEntry = "";
    private Asset asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.setFinishOnTouchOutside(false);

        etCode = findViewById(R.id.et_code);
        etName = findViewById(R.id.et_name);
        etLocation = findViewById(R.id.et_location);
        etQty = findViewById(R.id.et_qty);
        etDateOfEntry = findViewById(R.id.et_date_of_entry);
        etDateOfEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etDateOfEntry.setText(Helper.formatDMY(dayOfMonth, monthOfYear + 1, year));
                                dateOfEntry = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        spCategory = findViewById(R.id.spCategory);
        String[] profession = {"Aset Lancar", "Aset Tetap", "Asset Tetap TB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profession);
        spCategory.setAdapter(adapter);

        Button bnSave = findViewById(R.id.bnSave);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (asset == null) {
                    asset = new Asset();
                    asset.code = etCode.getText().toString();
                    asset.name = etName.getText().toString();
                    asset.category = (String) spCategory.getSelectedItem();
                    asset.location = etLocation.getText().toString();
                    asset.qty = Integer.parseInt(etQty.getText().toString());
                    asset.dateOfEntry = dateOfEntry;
                    AppDatabase.getInstance(getApplicationContext()).assetDao().insert(asset);

                    Toast.makeText(FormActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    asset.code = etCode.getText().toString();
                    asset.name = etName.getText().toString();
                    asset.category = (String) spCategory.getSelectedItem();
                    asset.location = etLocation.getText().toString();
                    asset.qty = Integer.parseInt(etQty.getText().toString());
                    asset.dateOfEntry = dateOfEntry;
                    AppDatabase.getInstance(getApplicationContext()).assetDao().update(asset);

                    Toast.makeText(FormActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });

        //data dari Adapter
        Intent intent = getIntent();
        asset = (Asset) intent.getSerializableExtra("asset");
        if (asset != null) {
            etCode.setText(asset.code);
            etName.setText(asset.name);
            etLocation.setText(asset.location);
            etQty.setText(String.valueOf(asset.qty));
            etDateOfEntry.setText(Helper.formatDMY(asset.dateOfEntry));
            dateOfEntry = asset.dateOfEntry;
            for (int i = 0; i < adapter.getCount(); i++) {
                if (asset.category.trim().equals(adapter.getItem(i))) {
                    spCategory.setSelection(i);
                    break;
                }
            }
        }

    }
}
