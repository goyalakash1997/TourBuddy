package com.example.asd.location;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by asd on 1/24/2019.
 */

public class retrieve extends AsyncTask<Void,Void,String>{
    String info;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public String address="",result="",rating="";

    @Override
    protected void onPreExecute() {
        info="http://robustious-forties.000webhostapp.com/retrieve.php";
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            URL url=new URL(info);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(rating,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response="";
            while((response=bufferedReader.readLine())!=null){
                result+=response;
            }
            try {
                jsonObject=new JSONObject(result);
                jsonArray=jsonObject.getJSONArray("server_response");
                int count=0;
                while (count<jsonArray.length()){
                    JSONObject jo=jsonArray.getJSONObject(count);
                    if(jo.getString("address")!=""||jo.getString("rating")!=""){
                        address+=jo.getString("address");
                        rating+=jo.getString("rating");
                        if(count>=1){
                            address+="$";
                            rating+="$";
                        }
                        count++;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            inputStream.close();
            bufferedReader.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    protected void onPostExecute(String s) {

    }
    public String re(){
        return result;
    }
    public String ra(){
        return rating;
    }
}
