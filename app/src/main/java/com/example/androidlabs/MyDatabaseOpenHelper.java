package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

  // Database Info
  public static final String DATABASE_NAME = "todoDatabase";

  public static final int DATABASE_VERSION = 1;

  // Table Names
  private static final String TABLE_NAME = "todos";

  // Todo_tbl  Columns
  private static final String KEY_TODO_ID = "id";
  private static final String KEY_TODO = "todo";
  private static final String KEY_URGENCY = "urgency";


  public MyDatabaseOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_TB = "CREATE TABLE " + TABLE_NAME + " (" + KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TODO + " TEXT NOT NULL," + KEY_URGENCY + " INTEGER NOT NULL )";
    try {
      db.execSQL(CREATE_TB);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      String DROP_TB = "DROP TABLE IF EXISTS " + TABLE_NAME;
      db.execSQL(DROP_TB);
      onCreate(db);
    }

  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }


  //CRUD operations


  // Insert a todo_item into the database
  public void addTodo(Todo todo) {
    // Create and/or open the database for writing
    SQLiteDatabase db = getWritableDatabase();

    // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
    // consistency of the database.
    db.beginTransaction();
    try {
      // The user might already exist in the database (i.e. the same user created multiple posts).
      int urgent = todo.getUrgent()==true ? 1 : 0;

      ContentValues values = new ContentValues();
      values.put(KEY_TODO, todo.getName());
      values.put(KEY_URGENCY, urgent);

      // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
      db.insertOrThrow(TABLE_NAME, null, values);
      db.setTransactionSuccessful();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.endTransaction();
    }
  }

  public void deleteTodo(Todo todo) {
    String sql_delete = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_TODO + " LIKE '" + todo.getName()+"'";
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(sql_delete);

  }


  public ArrayList<Todo> getTodos() {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<Todo> todoList = new ArrayList<>();
    String query = "SELECT *  FROM " + TABLE_NAME;
    Cursor cursor = db.rawQuery(query, null);
    printCursor(cursor);
    int count=0;
    while (cursor.moveToNext()) {
      String todoName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TODO));
      count++;

      boolean urgent = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_URGENCY)) == 1 ? true : false;
      Todo todo = new Todo(todoName, urgent);
      todoList.add(todo);
      printCursor(cursor);
      Log.i("Logs","Todoname: "+todoName+" urgency:"+urgent);

    }
    Log.i("Logs","Number of results in the cursor "+count);


    cursor.close();
    return todoList;
  }

  public void printCursor(Cursor c){
    SQLiteDatabase db = getReadableDatabase();
    Log.i("Logs","DB version "+db.getVersion());
    Log.i("Logs","Column Count"+c.getColumnCount());
    for (int i = 0; i < c.getColumnCount(); i++) {
      Log.i("Logs","Column Names "+ c.getColumnName(i));

    }

  }


}
