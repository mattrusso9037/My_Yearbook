package com.example.mattr.myyearbook;

/**
 * Created by Matt on 3/4/2018.
 */

public class Weather {

    private String day;
    private String date;
    private Double temperature;
    private String description;



    public Weather(String day, String date, Double temperature, String description) {
        this.day = day;
        this.date = date;
        this.temperature = temperature;
        this.description = description;
    }


    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }






}
