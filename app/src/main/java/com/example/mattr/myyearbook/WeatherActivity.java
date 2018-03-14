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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ListItemClickListener {

    private final String tag = "Weather App";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mlocationRequest;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private String lon;
    private String lat;
    static Context context;
    static TextView cityText;
    static ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayoutManager layoutManager;
    static List<Weather> weatherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);
        cityText = findViewById(R.id.cityText);

        context = getApplicationContext();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(weatherList, this);
        recyclerView.setAdapter(adapter);

            adapter.clear();

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
    Toast toast;
    @Override
    public void onListItemClick(int clickedItemIndex) {
//        if (toast != null){
//            toast.cancel();
//        }
//        toast.makeText(this, String.valueOf(clickedItemIndex), Toast.LENGTH_SHORT).show();

    }


}
