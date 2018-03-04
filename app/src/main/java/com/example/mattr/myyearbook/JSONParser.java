package com.example.mattr.myyearbook;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 3/4/2018.
 */

public class JSONParser {
    //day
    private List<String> dayList = new ArrayList<String>();
    //date
    private List<String> dateList = new ArrayList<String>();
    //temp
    private List<String> tempList = new ArrayList<String>();
    //description
    private List<String> descList = new ArrayList<String>();
    //icons
    private List<String> iconList = new ArrayList<>();
    private String iconUrl;

    public JSONParser() {

    }

    public void parseData(String result, Context context) {

        try {
            JSONObject forecastJSON = new JSONObject(result);
            JSONArray forecastArray = forecastJSON.getJSONArray("list");

            //city
            JSONObject cityObj = forecastJSON.getJSONObject("city");
            String city = cityObj.getString("name");


            for(int i = 0; i < forecastArray.length(); i = i + 8) {
                JSONObject dailyForecast = forecastArray.getJSONObject(i);


                JSONArray weatherJSON = dailyForecast.getJSONArray("weather");
                JSONObject description = (weatherJSON.getJSONObject(0));
                String desc = description.getString("description");
                descList.add(desc);




                JSONObject mainJSON = dailyForecast.getJSONObject("main");

                //icon
                String icon = description.getString("icon");
                iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
                iconList.add(iconUrl);
                Log.i("icon", iconUrl);

                //temp
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



            }
            //icons
            Picasso.with(context).load(iconList.get(0)).into(WeatherCards.imageOne);
            Picasso.with(context).load(iconList.get(0)).into(WeatherCards.imageSix);
            Picasso.with(context).load(iconList.get(1)).into(WeatherCards.imageTwo);
            Picasso.with(context).load(iconList.get(2)).into(WeatherCards.imageThree);
            Picasso.with(context).load(iconList.get(3)).into(WeatherCards.imageFour);
            Picasso.with(context).load(iconList.get(4)).into(WeatherCards.imageFive);

            //header
            WeatherCards.cityText.setText(city + "\n" + tempList.get(0) + "°F");


            //card 1
            WeatherCards.dayOneText.setText(dayList.get(0));
            WeatherCards.dateOneText.setText(dateList.get(0));
            WeatherCards.tempOneText.setText(tempList.get(0) + "°F");
            WeatherCards.decriptionOneText.setText(descList.get(0));
            //card 2
            WeatherCards.dayTwoText.setText(dayList.get(1));
            WeatherCards.dateTwoText.setText(dateList.get(1));
            WeatherCards.tempTwoText.setText(tempList.get(1) + "°F");
            WeatherCards.decriptionTwoText.setText(descList.get(1));
            //card 3
            WeatherCards.dayThreeText.setText(dayList.get(2));
            WeatherCards.dateThreeText.setText(dateList.get(2));
            WeatherCards.tempThreeText.setText(tempList.get(2) + "°F");
            WeatherCards.decriptionThreeText.setText(descList.get(2));
            //card 4
            WeatherCards.dayFourText.setText(dayList.get(3));
            WeatherCards.dateFourText.setText(dateList.get(3));
            WeatherCards.tempFourText.setText(tempList.get(3) + "°F");
            WeatherCards.decriptionFourText.setText(descList.get(3));
            //card 5
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
