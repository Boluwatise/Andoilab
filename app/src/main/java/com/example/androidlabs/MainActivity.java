package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
  BaseAdapter adapter ;
  ArrayList <Todo> todos = new ArrayList<>();;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Todo firstTodo = new Todo("Finish Lab 4",false);
    Todo secondTodo = new Todo("Cook dinner",false);
    Todo thirdTodo = new Todo("Brush Teeth",true);
    Todo fourthTodo = new Todo("Do Laundry",false);
    todos = new ArrayList<>();
    todos.add(firstTodo);
    todos.add(secondTodo);
    todos.add(thirdTodo);
    todos.add(fourthTodo);


     adapter = new BaseAdapter(this,todos);
      ListView listView =  findViewById(R.id.listView);
    listView.setAdapter(adapter);


    listView.setOnItemClickListener((adapterView, view, i, l) -> {
      AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
      alert.setMessage("The item index is "+i);
      alert.setCancelable(true);
      alert.show();

    });

    listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
      Todo listItem = adapter.getItem(i);

      AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
      alert.setMessage("Are you sure you want to delete this?");
      alert.setCancelable(false);
      alert.setPositiveButton("Yes", (dialog, which) -> {
        adapter.remove(listItem);
        adapter.notifyDataSetChanged();
      });
      alert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

      alert.show();


      return false;
    });

    }

  public void onAddItem(View v) {
    EditText etNewItem = findViewById(R.id.editNewItem);
    boolean urgent = ((SwitchCompat) findViewById(R.id.urgent)).isChecked();
    Todo newTodo = new Todo (etNewItem.getText().toString(),urgent);
    adapter.add(newTodo);
    etNewItem.setText("");
    adapter.notifyDataSetChanged();
  }


}
