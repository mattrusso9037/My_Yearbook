package com.example.mattr.myyearbook;

import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.mattr.myyearbook.WeatherActivity.progressBar;


/**
 * Created by Matt on 3/2/2018.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {
JSONParser jsonParser = new JSONParser();

    @Override
    protected String doInBackground(String...urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            //from user
            url = new URL(urls[0]);
            //open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            //read input stream
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            //when data finishes reading data will = -1
            while (data != -1) {
                char current = (char) data;

                result += current;
                data = reader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onPostExecute(String result) {
        //result will be string of json data
     super.onPostExecute(result);
    jsonParser.parseData(result, WeatherActivity.getContext());
        progressBar.setVisibility(View.INVISIBLE);



    }


}
