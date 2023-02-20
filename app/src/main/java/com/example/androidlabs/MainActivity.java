package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
    progressBar.setProgress(0);

    CatImages catImages = new CatImages();

    catImages.execute("https://cataas.com/cat?json=true");

  }


  class CatImages extends AsyncTask<String, Integer, String> {

    ProgressBar bar = findViewById(R.id.simpleProgressBar);
    OutputStream outputStream;

    ImageView image = findViewById(R.id.image);


    @Override
    protected String doInBackground(String... strings) {


      String imageUrl = strings[0];

      HttpURLConnection connection;
      InputStream is;
      InputStreamReader isr;


      try {


       //Infinite loop to display cat photos from the url
        while (true) {
          connection = (HttpURLConnection) new URL(imageUrl).openConnection();
          connection.connect();

          is = new URL(imageUrl).openStream();
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
            String filename = jsonObject.getString("_id") + ".jpeg";
            URL url = new URL("https://cataas.com" + jsonObject.getString("url"));

            //A function that handles the logic for downloading and using images on the device
            checkImageExists(filename, url);


            //update the progress bar
            for (int i = 0; i <= 100; i++) {
              try {
                publishProgress(i);
                Thread.sleep(30);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            connection.disconnect();

          } catch (JSONException err) {
            Log.d("Error", err.toString());
          }


        }
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      if (this.bar != null) {
        bar.setProgress(values[0]);
      }

    }


    public boolean checkImageExists(String filename, URL url) throws IOException {
      File file = new File(getFilesDir()
        + File.separator + filename);

      if (file.exists()) {
        //Do something
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        runOnUiThread(() -> {
          // Stuff that updates the UI
          image.setImageBitmap(myBitmap);
        });

        return true;
      } else {
        //Download the image and store on our device
        storeImage(file, url);
        return false;
      }
    }


    public void storeImage(File file, URL url) throws IOException {

      Bitmap downloadImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
      outputStream = new FileOutputStream(file);
      downloadImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      outputStream.write(bytes.toByteArray());

      Bitmap localImage = BitmapFactory.decodeFile(file.getAbsolutePath());

      // Stuff that updates the UI
      runOnUiThread(() -> {
        image.setImageBitmap(localImage);

      });
    }


  }


}
