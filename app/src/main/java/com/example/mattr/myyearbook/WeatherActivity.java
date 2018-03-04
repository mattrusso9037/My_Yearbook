package com.example.mattr.myyearbook;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    private final String tag = "Weather App";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mlocationRequest;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private String lon;
    private String lat;
    static Context context;
    static ImageView icon1;
    JSONObject data = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        context = getApplicationContext();
        getSupportActionBar().hide();

        WeatherCards.cityText = findViewById(R.id.cityText);

        WeatherCards.dayOneText = findViewById(R.id.dayOneText);
        WeatherCards.dayTwoText = findViewById(R.id.dayTwoText);
        WeatherCards.dayThreeText = findViewById(R.id.dayThreeText);
        WeatherCards.dayFourText = findViewById(R.id.dayFourText);
        WeatherCards.dayFiveText = findViewById(R.id.dayFiveText);

        WeatherCards.dateOneText = findViewById(R.id.dateOneText);
        WeatherCards.dateTwoText = findViewById(R.id.dateTwoText);
        WeatherCards.dateThreeText = findViewById(R.id.datethreeText);
        WeatherCards.dateFourText = findViewById(R.id.dateFourText);
        WeatherCards.dateFiveText = findViewById(R.id.dateFiveText);

        WeatherCards.decriptionOneText = findViewById(R.id.descriptionOneText);
        WeatherCards.decriptionTwoText = findViewById(R.id.descriptionTwoText);
        WeatherCards.decriptionThreeText = findViewById(R.id.descriptionThreeText);
        WeatherCards.decriptionFourText = findViewById(R.id.descriptionFourText);
        WeatherCards.decriptionFiveText = findViewById(R.id.descriptionFiveText);

        WeatherCards.tempOneText = findViewById(R.id.tempOneText);
        WeatherCards.tempTwoText = findViewById(R.id.tempTwoText);
        WeatherCards.tempThreeText = findViewById(R.id.tempThreeText);
        WeatherCards.tempFourText = findViewById(R.id.tempFourText);
        WeatherCards.tempFiveText = findViewById(R.id.tempFiveText);

        WeatherCards.imageOne = findViewById(R.id.imageViewOne);
        WeatherCards.imageTwo = findViewById(R.id.imageViewTwo);
        WeatherCards.imageThree = findViewById(R.id.imageViewThree);
        WeatherCards.imageFour = findViewById(R.id.imageViewFour);
        WeatherCards.imageFive = findViewById(R.id.imageViewFive);
        WeatherCards.imageSix = findViewById(R.id.imageViewSix);


        WeatherCards.progressBar = findViewById(R.id.progressBar);





        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).
                        addConnectionCallbacks(this).
                        addOnConnectionFailedListener(this).build();

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest,this);


                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(300000);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSION_ACCESS_FINE_LOCATION);

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest,this);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {

        lon = String.valueOf(location.getLongitude());
        lat = String.valueOf(location.getLatitude());

        //access API
        DownloadTask task = new DownloadTask();

        task.execute("http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&appid=c68afd55e680aa59cf765d11d0ab60b5");

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(tag, "connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(tag, "connection failed");

    }
    public static Context getContext() {
        return context;
    }

}
