package com.aiczone.contactapp;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.aiczone.contactapp.db.AppDatabase;
import com.aiczone.contactapp.model.Contact;
import com.aiczone.contactapp.utils.Helper;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText etName, etDateOfBirth, etEmail, etPhone;
    private Spinner spProfession;
    private RadioGroup rgGender;
    private RadioButton rbGender, rbMale, rbFemale;
    private Button bnSave;

    // data
    private String dateOfBirth = "";
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
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
                                etDateOfBirth.setText(Helper.formatDMY(dayOfMonth,monthOfYear+1, year));
                                dateOfBirth = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        spProfession = findViewById(R.id.spProfession);
        String[] profession = {"Mahasiswa", "Dosen", "Karyawan", "PNS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, profession);
        spProfession.setAdapter(adapter);

        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        bnSave = findViewById(R.id.bnSave);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contact == null){
                    contact = new Contact();
                    rbGender = findViewById(rgGender.getCheckedRadioButtonId());
                    contact.name = etName.getText().toString();
                    contact.email = etEmail.getText().toString();
                    contact.gender = rbGender.getText().toString();
                    contact.phone = etPhone.getText().toString();
                    contact.profession = (String) spProfession.getSelectedItem();
                    contact.dateOfBirth = dateOfBirth;
                    AppDatabase.getInstance(getApplicationContext()).contactDao().insert(contact);

                    Toast.makeText(FormActivity.this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();
                }else{
                    rbGender = findViewById(rgGender.getCheckedRadioButtonId());
                    contact.name = etName.getText().toString();
                    contact.email = etEmail.getText().toString();
                    contact.gender = rbGender.getText().toString();
                    contact.phone = etPhone.getText().toString();
                    contact.profession = (String) spProfession.getSelectedItem();
                    contact.dateOfBirth = dateOfBirth;
                    AppDatabase.getInstance(getApplicationContext()).contactDao().update(contact);

                    Toast.makeText(FormActivity.this,"Data berhasil diubah",Toast.LENGTH_SHORT).show();
                }

                finish();
//              Intent intent = new Intent(getBaseContext(), MainActivity.class);
//              startActivity(intent);
            }
        });

        //data dari Adapter
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
        if (contact != null) {
            etName.setText(contact.name);
            etEmail.setText(contact.email);
            etPhone.setText(contact.phone);
            etDateOfBirth.setText(Helper.formatDMY(contact.dateOfBirth));
            dateOfBirth = contact.dateOfBirth;
            if (contact.gender.equalsIgnoreCase("Laki-laki")) {
                rbMale.setSelected(true);
            } else {
                rbMale.setSelected(true);
            }
        }

    }
}
