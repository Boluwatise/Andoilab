package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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



  private DrawerLayout mDrawer;
  private Toolbar toolbar;
  private NavigationView nvDrawer;

  // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Set a Toolbar to replace the ActionBar.
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // This will display an Up icon (<-), we will replace it with hamburger later
    // Find our drawer view
    mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerToggle = setupDrawerToggle();

    // Setup toggle to display hamburger icon with nice animation
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerToggle.syncState();

    // Tie DrawerLayout events to the ActionBarToggle
    mDrawer.addDrawerListener(drawerToggle);

    // Find our drawer view
    mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    nvDrawer = (NavigationView) findViewById(R.id.nvView);
    // Setup drawer view
    setupDrawerContent(nvDrawer);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_info, menu);
    return true;
  }

  public void toastMessage(MenuItem item){
    Toast.makeText(this, "You clicked on "+item.getTitle(), Toast.LENGTH_LONG).show();
  }


  private ActionBarDrawerToggle setupDrawerToggle() {
    // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
    // and will not render the hamburger icon without it.
    return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
      new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
          selectDrawerItem(menuItem);
          return true;
        }
      });
  }

  public void selectDrawerItem(MenuItem menuItem) {
    // Create a new fragment and specify the fragment to show based on nav item clicked
    Fragment fragment = null;
    Class fragmentClass;
    switch(menuItem.getItemId()) {
      case R.id.fragment_host:
        fragmentClass = HostFragment.class;
        break;
      case R.id.nav_second_fragment:
        fragmentClass = DadJokes.class;
        break;
      case R.id.nav_third_fragment:
        fragmentClass =HostFragment.class;
        finishAffinity();
        break;
      default:
        fragmentClass = HostFragment.class;
    }

    try {
      fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Insert the fragment by replacing any existing fragment
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    // Highlight the selected item has been done by NavigationView
    menuItem.setChecked(true);
    // Set action bar title
    setTitle(menuItem.getTitle());
    // Close the navigation drawer
    mDrawer.closeDrawers();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // The action bar home/up action should open or close the drawer.
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }




}
