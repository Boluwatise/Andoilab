package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    String name = getIntent().getExtras().getString("name");
    String height = getIntent().getExtras().getString("height");
    String mass = getIntent().getExtras().getString("mass");

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      DetailsFragment fragmentDemo = DetailsFragment.newInstance(name, height,mass);
      ft.replace(R.id.frame_layout_alt, fragmentDemo);
      ft.commit();

    }
}
