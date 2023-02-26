package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

  BaseAdapter adapter;

  ListView listView;
  ArrayList<Person> starWarsC = new ArrayList<>();

  boolean tabletView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_view);
    StarWars connection = new StarWars();
    listView = findViewById(R.id.list_view);
    FrameLayout frameLayout = findViewById(R.id.frame_layout_alt);

    if (frameLayout == null) {
      tabletView = false;
    } else {
      tabletView = true;
    }
    connection.execute("https://swapi.dev/api/people/?format=json");
  }


  class StarWars extends AsyncTask<String, Integer, List<Person>> {

    ArrayList<Person> list;

    @Override
    protected List<Person> doInBackground(String... strings) {


      String url = strings[0];

      HttpURLConnection connection;
      InputStream is;
      InputStreamReader isr;

      try {

        //Infinite loop to display cat photos from the url
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();

        is = new URL(url).openStream();
        isr = new InputStreamReader(is);

        BufferedReader reader = new BufferedReader(isr);
        StringBuilder json = new StringBuilder();
        int c;

        //While loop to get the data from the input stream reader to set up the json object
        while ((c = reader.read()) != -1) {
          json.append((char) c);
        }
        try {
          JSONObject jsonObject = new JSONObject(json.toString());

          JSONArray resultsArray = jsonObject.getJSONArray("results");
          list = new ArrayList<>();

          for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject obj = resultsArray.getJSONObject(i);
            String name = obj.getString("name");
            String height = obj.getString("height");
            String mass = obj.getString("mass");
            Person temp = new Person(name, height, mass);
            list.add(temp);

          }

          connection.disconnect();

        } catch (JSONException err) {
          Log.d("Error", err.toString());
        }


      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      return list;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(List<Person> result) {

      // do something with the response and statusCode
      starWarsC.addAll(result);
      adapter = new BaseAdapter(getApplicationContext(), starWarsC);
      listView.setAdapter(adapter);


      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          Person listItem = adapter.getItem(i);
          if (tabletView) {
            DetailsFragment fragmentDemo = DetailsFragment.newInstance(listItem.getName(), listItem.getHeight(), listItem.getMass());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_alt, fragmentDemo);
            ft.commit();
          } else {
            Intent mIntent = new Intent(getApplicationContext(), EmptyActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("name", listItem.getName());
            mBundle.putString("height", listItem.getHeight());
            mBundle.putString("mass", listItem.getMass());
            mIntent.putExtras(mBundle);
            startActivity(mIntent);
          }
        }
      });

    }


  }


}
