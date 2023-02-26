package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseAdapter extends ArrayAdapter<Person> implements Filterable {



  public BaseAdapter(@NonNull Context context, ArrayList<Person> arrayList) {
    super(context,0, arrayList);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    View currentItemView = convertView;

    if (currentItemView == null) {
      currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item , parent, false);
    }

    Person currentNumberPosition = getItem(position);
    TextView textView1 = currentItemView.findViewById(R.id.list_name);
    textView1.setText(currentNumberPosition.getName());

    return currentItemView;
  }


  @Override
  public int getCount() {
    return super.getCount();
  }

  @Nullable
  @Override
  public Person getItem(int position) {
    return super.getItem(position);
  }

  @Override
  public long getItemId(int position) {
    return super.getItemId(position);
  }



}
