package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseAdapter extends ArrayAdapter<Todo> {

  public BaseAdapter(@NonNull Context context, ArrayList<Todo> arrayList) {
    super(context,0, arrayList);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    View currentItemView = convertView;

    if (currentItemView == null) {
      currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
    }

    Todo currentNumberPosition = getItem(position);
    TextView textView1 = currentItemView.findViewById(R.id.label);
    textView1.setText(""+currentNumberPosition.getName());


    if(currentNumberPosition.getUrgent()){
      currentItemView.setBackgroundColor(Color.RED);
      textView1.setTextColor(Color.WHITE);
    }else {
      currentItemView.setBackgroundColor(Color.WHITE);
      textView1.setTextColor(Color.BLACK);
    }

    return currentItemView;
  }


  @Override
  public int getCount() {
    return super.getCount();
  }

  @Nullable
  @Override
  public Todo getItem(int position) {
    return super.getItem(position);
  }

  @Override
  public long getItemId(int position) {
    return super.getItemId(position);
  }



}
