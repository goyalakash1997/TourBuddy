package com.example.asd.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity  {

    private FusedLocationProviderClient client;
    private Button button;
    private TextView text;
    private double lattitude;
    private double longitude;
    public static final int REQUEST_LOCATION_CODE = 99;
    //GetNearbyPlaceDetails asyncTask =new GetNearbyPlaceDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestPermission();
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_CODE);
        }
        text = findViewById(R.id.text);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lattitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(lattitude, longitude, 1);
                        if (addresses.size() > 0) {
                            text.setText("Welcome To " + addresses.get(0).getLocality());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        client = LocationServices.getFusedLocationProviderClient(this);
       // button = findViewById(R.id.button);
        text = findViewById(R.id.text);

    }
    public void Search(View view){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    //lattitude=location.getLatitude();
                    //longitude=location.getLongitude();
                    //text.setText(lattitude+"     "+longitude);
                    //String url2="https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJn2Mt3hN3dDkRWizrx9jyxiU&fields=name,rating,formatted_phone_number&key=AIzaSyARh_AUhuwmVsJwro3iPPs0pqu8hJD8xIo";
                    //Context context=Details.this;
                    String url2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lattitude + "," + longitude + "&rankby=distance&type=police&key=AIzaSyB-iHv1FpKboJnfJLIjMoF2ajeB5hThFSY";
                    Object dataTransfer[] = new Object[2];
                   // dataTransfer[0] = text;
                    dataTransfer[0] = url2;
                    dataTransfer[1]=1;
                    //dataTransfer[2]=MainActivity.this;
                    GetNearbyPlaceData getNearbyPlacesData = new GetNearbyPlaceData(MainActivity.this);
                  // getNearbyPlacesData.delegate = this;
                    getNearbyPlacesData.execute(dataTransfer);
                    //Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
                    //asyncTask.delegate=this;
                }
            }
        });
//        Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
    }
    public void Search3(View view){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    //lattitude=location.getLatitude();
                    //longitude=location.getLongitude();
                    //text.setText(lattitude+"     "+longitude);
                    //String url2="https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJn2Mt3hN3dDkRWizrx9jyxiU&fields=name,rating,formatted_phone_number&key=AIzaSyARh_AUhuwmVsJwro3iPPs0pqu8hJD8xIo";
                    //Context context=Details.this;
                    String url2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lattitude + "," + longitude + "&rankby=distance&type=atm&key=AIzaSyB-iHv1FpKboJnfJLIjMoF2ajeB5hThFSY";
                    Object dataTransfer[] = new Object[2];
                    // dataTransfer[0] = text;
                    dataTransfer[0] = url2;
                    dataTransfer[1]=3;
                    //dataTransfer[2]=MainActivity.this;
                    GetNearbyPlaceData getNearbyPlacesData = new GetNearbyPlaceData(MainActivity.this);
                    // getNearbyPlacesData.delegate = this;
                    getNearbyPlacesData.execute(dataTransfer);
                    //Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
                    //asyncTask.delegate=this;
                }
            }
        });
    }
    public void Search4(View view){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    //lattitude=location.getLatitude();
                    //longitude=location.getLongitude();
                    //text.setText(lattitude+"     "+longitude);
                    //String url2="https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJn2Mt3hN3dDkRWizrx9jyxiU&fields=name,rating,formatted_phone_number&key=AIzaSyARh_AUhuwmVsJwro3iPPs0pqu8hJD8xIo";
                    //Context context=Details.this;
                    String url2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lattitude + "," + longitude + "&rankby=distance&type=toilet&key=AIzaSyDy1puX1VnZvSdCESUffD-8-DG7kBAqlxs";
                    Object dataTransfer[] = new Object[2];
                    // dataTransfer[0] = text;
                    dataTransfer[0] = url2;
                    dataTransfer[1]=4;
                    //dataTransfer[2]=MainActivity.this;
                    GetNearbyPlaceData getNearbyPlacesData = new GetNearbyPlaceData(MainActivity.this);
                    // getNearbyPlacesData.delegate = this;
                    getNearbyPlacesData.execute(dataTransfer);
                    //Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
                    //asyncTask.delegate=this;
                }
            }
        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }
    public void Search2(View view){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                  //  lattitude=location.getLatitude();
                   // longitude=location.getLongitude();
                   // text.setText(lattitude+"     "+longitude);
                    //String url2="https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJn2Mt3hN3dDkRWizrx9jyxiU&fields=name,rating,formatted_phone_number&key=AIzaSyARh_AUhuwmVsJwro3iPPs0pqu8hJD8xIo";
                    //Context context=Details.this;
                    String url2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lattitude + "," + longitude + "&rankby=distance&type=hospital&key=AIzaSyCRf1BmhhoXgK3KrygCtjNUChNBHJPLSa8";
                    Object dataTransfer[] = new Object[2];
                    // dataTransfer[0] = text;
                    dataTransfer[0] = url2;
                    dataTransfer[1]=2;
                    //dataTransfer[2]=MainActivity.this;
                    GetNearbyPlaceData getNearbyPlacesData = new GetNearbyPlaceData(MainActivity.this);
                    // getNearbyPlacesData.delegate = this;
                    getNearbyPlacesData.execute(dataTransfer);
                    //Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
                    //asyncTask.delegate=this;
                }
            }
        });
//        Toast.makeText(this, lattitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
    }

    /*@Override
    public void processFinish(String output) {

        text.setText(output);
    }*/
}
