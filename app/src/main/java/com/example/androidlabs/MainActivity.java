package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  BaseAdapter adapter ;
  ArrayList <Todo> todos;
  ListView listView ;

  MyDatabaseOpenHelper dbHelper;


  @Override
  public void onResume() {
    super.onResume();
    todos = new ArrayList<>();
    loadTodos();
    adapter = new BaseAdapter(this, todos);
    listView.setAdapter(adapter);
  }

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    dbHelper = new MyDatabaseOpenHelper(getApplicationContext());
    todos = new ArrayList<>();
    loadTodos();


    listView =  findViewById(R.id.listView);


     adapter = new BaseAdapter(this,todos);

    listView.setAdapter(adapter);



    listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
      Todo listItem = adapter.getItem(i);

      AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
      alert.setTitle("Are you sure you want to delete this?");
      alert.setMessage("Selected row is"+i);
      alert.setCancelable(false);
      alert.setPositiveButton("Yes", (dialog, which) -> {
        dbHelper.deleteTodo(listItem);
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
    dbHelper.addTodo(newTodo);
    etNewItem.setText("");
    adapter.notifyDataSetChanged();
  }


  public void loadTodos(){
    // Get all todos from database and add to arraylist
    List<Todo> todos = dbHelper.getTodos();
    this.todos.addAll(todos);
  }






}
