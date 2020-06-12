package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.snackbar.Snackbar;


public class AddActivity extends AppCompatActivity {
    private DatabaseReference database;

    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText keyword;
    private EditText arti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        keyword = findViewById(R.id.keyword);
        arti = findViewById(R.id.arti);
        btSubmit = findViewById(R.id.btSubmit);

        database = FirebaseDatabase.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(keyword.getText().toString()) && !isEmpty(arti.getText().toString()))
                    submitIsi(new Data(keyword.getText().toString(), arti.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.btSubmit), "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(
                        keyword.getWindowToken(), 0);
            }
        });
    }
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }
    private void submitIsi (Data data){
        database.child("Data").child("isi").push().setValue(data).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                keyword.setText("");
                arti.setText("");
                Snackbar.make(findViewById(R.id.btSubmit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    public  static Intent getActIntent(Activity activity){
        return new Intent(activity, AddActivity.class);
    }
}