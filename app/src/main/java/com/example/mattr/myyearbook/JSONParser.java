package com.example.mattr.myyearbook;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import static com.example.mattr.myyearbook.WeatherActivity.cityText;
import static com.example.mattr.myyearbook.WeatherActivity.weatherList;

/**
 * Created by Matt on 3/4/2018.
 */

public class JSONParser {

    private String iconUrl;
    private double tempDblMin;

    public JSONParser() {

    }

    public void parseData(String result, Context context) {

        try {

            JSONObject forecastJSON = new JSONObject(result);
            JSONArray forecastArray = forecastJSON.getJSONArray("list");

            //city
            JSONObject cityObj = forecastJSON.getJSONObject("city");
            String city = cityObj.getString("name");


            for(int i = 0; i < forecastArray.length(); i++) {


                JSONObject dailyForecast = forecastArray.getJSONObject(i);

                //date
                long date = dailyForecast.getLong("dt");
                date = date * 1000;

                //time
                SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                String time = time_format.format(date);
                Log.i("time", time);

                JSONArray weatherJSON = dailyForecast.getJSONArray("weather");
                JSONObject description = (weatherJSON.getJSONObject(0));
                String desc = description.getString("description");
                JSONObject mainJSON = dailyForecast.getJSONObject("main");

                if (isMin(time)) {
                    //low
                    Double tempMin = mainJSON.getDouble("temp_min");
                     tempDblMin = (tempMin * 1.8 - 459.67);

                }

                if (isMidday(time)) {

                    //day
                    SimpleDateFormat day_format = new SimpleDateFormat("EEEE");
                    String day = day_format.format(date);

                    //date
                    SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yy");
                    String dateFormat = date_format.format(date);

                    //icon
                    String icon = description.getString("icon");
                    iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
                    Log.i("icon", iconUrl);

                    //high
                    Double tempMax = mainJSON.getDouble("temp_max");
                    double tempDblMax = (double) (tempMax * 1.8 - 459.67);

                    NumberFormat tempFormat = new DecimalFormat("#0.0");

                    Weather weather = new Weather(day, dateFormat, tempFormat.format(tempDblMax), tempFormat.format(tempDblMin), desc, iconUrl);
                    Log.i("weather", weather.toString());
                    weatherList.add(weather);
                }
            }

            cityText.setText(city);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMidday(String time) {

        if (time.contains("11:00:00")) {
            Log.i("bool", "middaytrue");
            return true;
        }
        Log.i("midday", "false");
        return false;

    }
    private boolean isMin(String time) {
        if (time.contains("05:00:00")) {
            Log.i("bool", "ismintrue");

            return true;
        }
        return false;
    }


}
