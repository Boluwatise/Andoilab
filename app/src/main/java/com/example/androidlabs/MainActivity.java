package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences share = null;
    EditText type;

    @Override
    protected void onPause() {
        super.onPause();
        saveShared(type.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_activity);

        share = getSharedPreferences("file", MODE_PRIVATE);
        String saved = share.getString("Reserve name", "");
        type = findViewById(R.id.edit);
        type.setText(saved);


        Button press = findViewById(R.id.next);

        press.setOnClickListener(click -> {
            Intent goToNext = new Intent(MainActivity.this, NameActivity.class);
            goToNext.putExtra("name", type.getText().toString());
            startActivityForResult(goToNext, 0);

        });

    }


    private void saveShared(String save) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Reserve name", save);
        editor.commit();
    }
}