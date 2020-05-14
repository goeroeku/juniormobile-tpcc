package com.aiczone.bookapp;

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

import com.aiczone.bookapp.db.AppDatabase;
import com.aiczone.bookapp.model.Book;
import com.aiczone.bookapp.utils.Helper;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText etTitle, etDateOfIssue, etISBN;
    private Spinner spCategory;
    private Button bnSave;

    // data
    private String dateOfIssue = "";
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.setFinishOnTouchOutside(false);

        etTitle = findViewById(R.id.etTitle);
        etISBN = findViewById(R.id.etISBN);
        etDateOfIssue = findViewById(R.id.etDateOfIssue);
        etDateOfIssue.setOnClickListener(new View.OnClickListener() {
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
                                etDateOfIssue.setText(Helper.formatDMY(dayOfMonth,monthOfYear+1, year));
                                dateOfIssue = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        spCategory = findViewById(R.id.spCategory);
        String[] profession = {"Religi", "Bahasa", "Komik", "Komputer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profession);
        spCategory.setAdapter(adapter);

        bnSave = findViewById(R.id.bnSave);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(book == null){
                    book = new Book();
                    book.title = etTitle.getText().toString();
                    book.isbn = etISBN.getText().toString();
                    book.category = (String) spCategory.getSelectedItem();
                    book.dateOfIssue = dateOfIssue;
                    AppDatabase.getInstance(getApplicationContext()).bookDao().insert(book);

                    Toast.makeText(FormActivity.this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();
                }else{
                    book.title = etTitle.getText().toString();
                    book.isbn = etISBN.getText().toString();
                    book.category = (String) spCategory.getSelectedItem();
                    book.dateOfIssue = dateOfIssue;
                    AppDatabase.getInstance(getApplicationContext()).bookDao().update(book);

                    Toast.makeText(FormActivity.this,"Data berhasil diubah",Toast.LENGTH_SHORT).show();
                }

                finish();
//              Intent intent = new Intent(getBaseContext(), MainActivity.class);
//              startActivity(intent);
            }
        });

        //data dari Adapter
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        if (book != null) {
            etTitle.setText(book.title);
            etISBN.setText(book.isbn);
            etDateOfIssue.setText(Helper.formatDMY(book.dateOfIssue));
            dateOfIssue = book.dateOfIssue;
            for(int i=0; i < adapter.getCount(); i++) {
                if(book.category.trim().equals(adapter.getItem(i).toString())){
                    spCategory.setSelection(i);
                    break;
                }
            }
        }

    }
}
