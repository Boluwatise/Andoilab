package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_activity);
        Button call = findViewById(R.id.btn1);
        Button thank = findViewById(R.id.btn2);
        TextView wel = findViewById(R.id.textView9);
        Intent fromMain = getIntent();
        wel.setText("Welcome "+fromMain.getStringExtra("name")+"!");


        call.setOnClickListener(click ->{
            Intent goToMain = new Intent( NameActivity.this, MainActivity.class);
            startActivityForResult ( goToMain,0);

        });

        thank.setOnClickListener(click ->{
            Intent goToMain = new Intent( NameActivity.this, MainActivity.class);
            startActivityForResult ( goToMain ,1);

        });


    }









}
