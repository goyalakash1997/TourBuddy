package com.example.asd.location;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asd on 8/5/2018.
 */

public class GetNearbyPlaceData extends AsyncTask<Object,String,String> {
    //MainActivity y;
    String googlePlacesData;
    int se;
    //GoogleMap mMap;
    //TextView text;
    ArrayList<HashMap<String,String>> result=new ArrayList<>();
    String url;
    Context context;
    GetNearbyPlaceData(Context context){
        this.context=context.getApplicationContext();
    }

    @Override
    protected String doInBackground(Object... objects) {
        //mMap=
        //text = (TextView) objects[0];
        url = (String) objects[0];
        se= (int) objects[1];
        //result= (ArrayList<String>) objects[1];
        //y= (MainActivity) objects[1];
        //mContext= (Context) objects[3];
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
        List<HashMap<String, String>> nearByPlaceList = null;
        //HashMap<String,String>placeDetails=null;
        DataParser2 parser = new DataParser2();
        nearByPlaceList = parser.parse(s);
        showNearbyPlaces(nearByPlaceList);
        //text.setText(s);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> getNearbyPlaces) {
        //String s="";
        for (int i = 0; i < getNearbyPlaces.size(); i++) {
            //MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String, String> googlePlace = getNearbyPlaces.get(i);
            //String placename=googlePlace.get("place_name");
            //String vicinity=googlePlace.get("vicinity");
            String placeId = googlePlace.get("id");
            //String url2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&fields=name,rating,formatted_phone_number&key=AIzaSyARh_AUhuwmVsJwro3iPPs0pqu8hJD8xIo";
            //Object dataTransfer[] = new Object[1];
            //dataTransfer[0] = text;
            //dataTransfer[0] = url2;
            //GetNearbyPlaceDetails getNearbyPlacesData = new GetNearbyPlaceDetails();
            //getNearbyPlacesData.execute(dataTransfer);
            //s=s+placename+" "+vicinity+" "+placeId+"\n";
            //}
            //text.setText(s);
            //y.calling(result);
            result.add(googlePlace);
        }
        //new Details(result);
        if(se==1){
        Intent intent=new Intent(context,Details.class);
        intent.putExtra("EXTRA_SESSION_ID",result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);}
        else if(se==2)
        {
            Intent intent=new Intent(context,Details2.class);
            intent.putExtra("EXTRA_SESSION_ID",result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else if(se==3){
            Intent intent=new Intent(context,shoppingdetails.class);
            intent.putExtra("EXTRA_SESSION_ID",result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else {
            Intent intent=new Intent(context,toiletsdetails.class);
            intent.putExtra("EXTRA_SESSION_ID",result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        //y.calling(result);
    }
}

