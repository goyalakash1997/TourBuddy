package com.example.asd.location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asd on 8/5/2018.
 */

public class DataParser2 {
    private HashMap<String,String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinty = "-NA-";
        String lattitude = "";
        String longitude = "";
        String reference = "";
        String id="";
        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinty")){
                vicinty=googlePlaceJson.getString("vicinity");
            }
            lattitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJson.getString("reference");
            id=googlePlaceJson.getString("place_id");
            googlePlacesMap.put("place_name",placeName);
            googlePlacesMap.put("vicinity",vicinty);
            googlePlacesMap.put("lattitude",lattitude);
            googlePlacesMap.put("longitude",longitude);
            googlePlacesMap.put("reference",reference);
            googlePlacesMap.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;
    }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String>>placeList=new ArrayList<>();
        for(int i=0;i<count;i++){
            try {
                placeList.add(getPlace((JSONObject) jsonArray.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeList;
    }
    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
