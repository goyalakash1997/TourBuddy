package com.example.asd.location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by asd on 7/30/2018.
 */

class DataParser {
    private HashMap<String,String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "akash";
        //String vicinty = "-NA-";
        //String lattitude = "";
        //String longitude = "";
        //String reference = "";
        //String id="";
        String phone="akash";
        String rating="akash";
        String address="";
        try {
            if (!((googlePlaceJson.getJSONObject("result")) ==null)) {
                placeName = googlePlaceJson.getJSONObject("result").getString("name");

            //if(!googlePlaceJson.isNull("formatted_phone_number")){
                phone=googlePlaceJson.getJSONObject("result").getString("formatted_phone_number");
           // }
            //if(!googlePlaceJson.isNull("rating")){
                rating=googlePlaceJson.getJSONObject("result").getString("rating");
                address=googlePlaceJson.getJSONObject("result").getString("formatted_address");
            }
            /*if (!googlePlaceJson.isNull("vicinty")){
                vicinty=googlePlaceJson.getString("vicinity");
            }*/
            //lattitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //longitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //reference=googlePlaceJson.getString("reference");
            //id=googlePlaceJson.getString("id");
            googlePlacesMap.put("place_name",placeName);
            googlePlacesMap.put("phone",phone);
            googlePlacesMap.put("rating",rating);
            googlePlacesMap.put("address",address);
            //googlePlacesMap.put("vicinity",vicinty);
            //googlePlacesMap.put("lattitude",lattitude);
            //googlePlacesMap.put("longitude",longitude);
            //googlePlacesMap.put("reference",reference);
            //googlePlacesMap.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;
    }

    public HashMap<String,String>parse(String jsonData) throws JSONException {
        //JSONArray jsonArray=null;
        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(jsonData);
            // jsonArray=jsonObject.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlace(jsonObject);
    }
}
