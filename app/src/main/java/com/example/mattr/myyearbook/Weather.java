package com.example.mattr.myyearbook;

/**
 * Created by Matt on 3/4/2018.
 */

public class Weather {

    private String day;
    private String date;
    private String high;
    private String low;
    private String description;
    private String iconUrl;


    public Weather(String day, String date, String high, String low, String description, String iconUrl) {
        this.day = day;
        this.date = date;
        this.high = high;
        this.low = low;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    public String getHigh() {
        return high;
    }
    public String getLow() {
        return low;
    }
    public String getDay() {
        return day;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getIconUrl() {return iconUrl;}


    @Override
    public String toString() {
        return "Weather{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", description='" + description + '\'' + ", icon " + iconUrl +
                '}';
    }

}
