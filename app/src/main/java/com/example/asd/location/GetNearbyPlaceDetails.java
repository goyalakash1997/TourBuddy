package com.example.asd.location;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asd on 7/30/2018.
 */

class GetNearbyPlaceDetails extends AsyncTask<Object,String,String> {
    String googlePlacesData;
    //GoogleMap mMap;
    //TextView text;
    String url;
    String lat="",lon="";
    private Context context;


    GetNearbyPlaceDetails(Context context){
        this.context=context.getApplicationContext();
    }
    @Override
    protected String doInBackground(Object... objects) {
        //mMap=
        //text = (TextView) objects[0];
        url = (String) objects[0];
        lat= (String) objects[1];
        lon= (String) objects[2];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
       // HashMap<String,String> nearByPlaceList=null;
        HashMap<String,String>placeDetails=null;
        DataParser parser=new DataParser();
        try {
            placeDetails=parser.parse(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showNearbyPlaces(placeDetails);
        //text.setText(s);
    }
    //public AsyncResponse delegate = null;

    /*public GetNearbyPlaceDetails(AsyncResponse delegate){
        this.delegate = delegate;
    }*/
    private void showNearbyPlaces(HashMap<String,String>placeDetails){
        String s="";
        //for(int i=0;i<getNearbyPlaces.size();i++){
        //MarkerOptions markerOptions=new MarkerOptions();
        //HashMap<String,String>googlePlace=getNearbyPlaces.get(0);
        //String placename=googlePlace.get("place_name");
        //String vicinity=googlePlace.get("vicinity");
        //String placeId=googlePlace.get("id");
        //s=s+placename+" "+vicinity+" "+placeId+"\n";
        //}
       //text.setText(placeDetails.get("place_name")+" "+placeDetails.get("phone")+" "+placeDetails.get("rating"));
        s=placeDetails.get("place_name")+" "+placeDetails.get("phone")+" "+placeDetails.get("rating");
        //MainActivity obj=new MainActivity();
       // delegate.processFinish(s);
        Intent intent=new Intent(context, rate.class);
        //intent.putExtra("EXTRA_SESSION_ID2",s);
        intent.putExtra("phone",placeDetails.get("phone"));
        intent.putExtra("rating",placeDetails.get("rating"));
        intent.putExtra("lattitude",lat);
        intent.putExtra("longitude",lon);
        intent.putExtra("address",placeDetails.get("address"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
