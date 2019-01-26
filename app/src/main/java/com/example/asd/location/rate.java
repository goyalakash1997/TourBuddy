package com.example.asd.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.asd.location.MainActivity.REQUEST_LOCATION_CODE;

public class rate extends AppCompatActivity {

    TextView textView;
    Button submit;
    CardView cardView;
    RatingBar ratingBar;
    ImageButton rating,calbutton;
    double lattitude, longitude;
    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    private TelephonyManager mTelephonyManager;
    private MyPhoneCallListener mListener;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        textView=findViewById(R.id.address);
        ratingBar=findViewById(R.id.ratingBar);
        //submit=findViewById(R.id.submit);
      //  rating=findViewById(R.id.ratingbutton);
       // cardView=findViewById(R.id.card);
        if(getIntent().getStringExtra("rating")!=null&&!getIntent().getStringExtra("rating").equals(""))
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
        textView.setText(getIntent().getStringExtra("address"));
        calbutton=findViewById(R.id.calbutton);
        calbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String formattedNumber = PhoneNumberUtils.formatNumber(number.getText().toString(),
                        //Locale.getDefault().getCountry());
                String formattedNumber="";
                formattedNumber=getIntent().getStringExtra("phone");
                Toast.makeText(rate.this,formattedNumber,Toast.LENGTH_LONG).show();
                if(formattedNumber==null)
                    formattedNumber="078307 72078";
                String phoneNumber = String.format("tel: %s",
                        formattedNumber);
                // Create the intent.
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                // Set the data for the intent as the phone number.
                callIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, check for phone permission,
                // and send intent.
                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    checkForPhonePermission();
                    startActivity(callIntent);
                }
            }
        });
        mTelephonyManager = (TelephonyManager)
                getSystemService(TELEPHONY_SERVICE);
      //  checkForSmsPermission();
        if (isTelephonyEnabled()) {
            // ...
            checkForPhonePermission();
            // Register the PhoneStateListener to monitor phone activity.
            mListener = new MyPhoneCallListener();
            mTelephonyManager.listen(mListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(rate.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_CODE);
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(rate.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(rate.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lattitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng friendLocation = new LatLng(Double.parseDouble(getIntent().getStringExtra("lattitude")), Double.parseDouble(getIntent().getStringExtra("longitude")));
                    Location currentUser = new Location("");
                    currentUser.setLatitude(lattitude);
                    currentUser.setLongitude(longitude);
                    Location friend=new Location("");
                    friend.setLatitude(Double.parseDouble(getIntent().getStringExtra("lattitude")));
                    friend.setLongitude(Double.parseDouble(getIntent().getStringExtra("longitude")));
                    mMap.addMarker(new MarkerOptions()
                            .position(friendLocation)
                            .title("Destination")
                            .snippet("Distance "+new DecimalFormat("#.#").format(distance(currentUser,friend)))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    LatLng current = new LatLng(lattitude, longitude);
                    mMap.addMarker(new MarkerOptions()
                            .position(current)
                            .title("Source"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lattitude, longitude), 14.0f));
                    String url = getRequestUrl(current, friendLocation);
                    TaskRequestDirections taskRequestDirections=new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                    //Toast.makeText(rate.this," "+longitude+lattitude,Toast.LENGTH_LONG).show();
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // LatLng sydney = new LatLng(-34, 151);
            }
        });
    }

    private String getRequestUrl(LatLng origin, LatLng destination) {
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode+"&"+"key=AIzaSyB0222lJRmk9gS4SqZ2mcZICl2vjmtg4lg";
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser=new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;
            PolylineOptions polylineOptions=null;
            //PolygonOptions polygonOptions = null;
            for(List<HashMap<String,String>>path: lists){
                points = new ArrayList();
                //polygonOptions=
                polylineOptions=new PolylineOptions();
                for(HashMap<String,String>point:path){
                    double lat=Double.parseDouble(point.get("lat"));
                    double lon=Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }
            if(polylineOptions!=null){
                mMap.addPolyline(polylineOptions);
            }
            else{
                Toast.makeText(rate.this,"DirectionsNot Found",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            //Log.d(TAG, getString(R.string.permission_not_granted));
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted.
        }
    }
    private boolean isTelephonyEnabled() {
        if (mTelephonyManager != null) {
            if (mTelephonyManager.getSimState() ==
                    TelephonyManager.SIM_STATE_READY) {
                return true;
            }
        }
        return false;
    }
    private class MyPhoneCallListener extends PhoneStateListener {
        private boolean returningFromOffHook = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // Define a string for the message to use in a toast.
            String message = "Hello";
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // Incoming call is ringing
                    message = message +
                            "ringing" + incomingNumber;
                    //Toast.makeText(rate.this, message,
                      //      Toast.LENGTH_SHORT).show();
                    //Log.i(TAG, message);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // Phone call is active -- off the hook
                    message = message + "offhook";
                  //  Toast.makeText(rate.this, message,
                    //        Toast.LENGTH_SHORT).show();
                    //Log.i(TAG, message);
                    returningFromOffHook = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    // Phone is idle before and after phone call.
                    // If running on version older than 19 (KitKat),
                    // restart activity when phone call ends.
                    message = message + "idle";
                    //Toast.makeText(rate.this, message,
                      //      Toast.LENGTH_SHORT).show();
                    //Log.i(TAG, message)
                    break;
                default:
                    message = message + "Phone off";
                   // Toast.makeText(rate.this, message,
                     //       Toast.LENGTH_SHORT).show();
                    //Log.i(TAG, message);
                    break;
            }
        }
    }
    private double distance(Location currentUser,Location friend){
        double theta=currentUser.getLongitude()-friend.getLongitude();
        double dist=Math.sin(deg2rad(currentUser.getLatitude()))
                *Math.sin(deg2rad(friend.getLatitude()))
                *Math.cos(deg2rad(currentUser.getLatitude()))
                *Math.cos(deg2rad(friend.getLatitude()))
                *Math.sin(deg2rad(theta));
        dist=Math.acos(dist);
        dist=deg2deg(dist);
        dist=dist*60*1.1515;
        return dist;
    }
    private double deg2rad(double deg){
        return (deg*Math.PI/180.0);
    }
    private double deg2deg(double rad){
        return (rad*100/Math.PI);
    }
}
