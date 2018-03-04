package com.example.mattr.myyearbook;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


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
    protected void onPreExecute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WeatherCards.progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onPostExecute(String result) {
        //result will be string of json data
     super.onPostExecute(result);

        try {
            JSONObject forecastJSON = new JSONObject(result);
            JSONArray forecastArray = forecastJSON.getJSONArray("list");

            //city
            JSONObject cityObj = forecastJSON.getJSONObject("city");
            String city = cityObj.getString("name");
            WeatherCards.cityText.setText(city);


            //day
            List<String> dayList = new ArrayList<String>();
            //date
            List<String> dateList = new ArrayList<String>();
            //temp
            List<String> tempList = new ArrayList<String>();
            //description
            List<String> descList = new ArrayList<String>();


            for(int i = 4; i < forecastArray.length(); i = i + 8) {
                JSONObject dailyForecast = forecastArray.getJSONObject(i);


                JSONArray weatherJSON = dailyForecast.getJSONArray("weather");
                JSONObject description = (weatherJSON.getJSONObject(0));
                String desc = description.getString("description");
                descList.add(desc);

                //temp
                JSONObject mainJSON = dailyForecast.getJSONObject("main");
                Double temp = mainJSON.getDouble("temp");
                int tempInt = (int) (temp * 1.8 - 459.67);
                tempList.add(String.valueOf(tempInt));

                //date
                long date = dailyForecast.getLong("dt");
                date = date*1000;

                //day
                SimpleDateFormat day_format = new SimpleDateFormat("EEEE");
                String displayText = day_format.format(date);
                dayList.add(displayText);


                //date
                SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yy");
                String dateText = date_format.format(date);
                dateList.add(dateText);

//                for (int j = 0; j < WeatherCards.dayArray.length; j++) {
//                    WeatherCards.dayArray[j].setText(dayList.get(i));
//                }

            }

            WeatherCards.dayOneText.setText(dayList.get(0));
            WeatherCards.dateOneText.setText(dateList.get(0));
            WeatherCards.tempOneText.setText(tempList.get(0) + "°F");
            WeatherCards.decriptionOneText.setText(descList.get(0));

            WeatherCards.dayTwoText.setText(dayList.get(1));
            WeatherCards.dateTwoText.setText(dateList.get(1));
            WeatherCards.tempTwoText.setText(tempList.get(1) + "°F");
            WeatherCards.decriptionTwoText.setText(descList.get(1));

            WeatherCards.dayThreeText.setText(dayList.get(2));
            WeatherCards.dateThreeText.setText(dateList.get(2));
            WeatherCards.tempThreeText.setText(tempList.get(2) + "°F");
            WeatherCards.decriptionThreeText.setText(descList.get(2));

            WeatherCards.dayFourText.setText(dayList.get(3));
            WeatherCards.dateFourText.setText(dateList.get(3));
            WeatherCards.tempFourText.setText(tempList.get(3) + "°F");
            WeatherCards.decriptionFourText.setText(descList.get(3));

            WeatherCards.dayFiveText.setText(dayList.get(4));
            WeatherCards.dateFiveText.setText(dateList.get(4));
            WeatherCards.tempFiveText.setText(tempList.get(4) + "°F");
            WeatherCards.decriptionFiveText.setText(descList.get(4));

            WeatherCards.progressBar.setVisibility(View.GONE);

            Log.i("date", dateList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
