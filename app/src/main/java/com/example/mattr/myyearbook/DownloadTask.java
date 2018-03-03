package com.example.mattr.myyearbook;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Matt on 3/2/2018.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {



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
    protected void onPostExecute(String result) {
        //result will be string of json data
     super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);


            //first part main stuff
            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));
            Double temperature = Double.parseDouble(weatherData.getString("temp"));
            //Farenheit
            int temperatureInteger = (int) (temperature * 1.8 - 459.67);
            //place name
            String placeName = jsonObject.getString("name");

            WeatherActivity.cityText.setText(String.valueOf(placeName));
            WeatherActivity.forecast.setText(String.valueOf(temperatureInteger) + "°F");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
