package com.example.asd.location;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Details extends AppCompatActivity {

    /*private String words=null;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        words= getIntent().getStringExtra("EXTRA_SESSION_ID");
        Toast.makeText(Details.this,words+" hi",Toast.LENGTH_LONG).show();
        textView =findViewById(R.id.text);
        textView.setText(words);
    }*/
    ArrayList<HashMap<String,String>> words =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details);
        //words= getIntent().getStringArrayListExtra("EXTRA_SESSION_ID");
        words=(ArrayList<HashMap<String, String>>)getIntent().getSerializableExtra("EXTRA_SESSION_ID");
        WordAdapter adapter = new WordAdapter(this,R.layout.details_item, words);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s=words.get(i).get("id");
               /* Intent intent=new Intent(Details.this,rate.class);
                intent.putExtra("lattitude",words.get(i).get("lattitude"));
                intent.putExtra("longitude",words.get(i).get("longitude"));
                //intent.putExtra("address",words.get(i).get("place_name")+":"+"   "+words.get(i).get("vicinity"));
                startActivity(intent);*/
                String url="https://maps.googleapis.com/maps/api/place/details/json?placeid=" + s+ "&fields=name,rating,formatted_phone_number,formatted_address&key=AIzaSyBR73yq-VPLMC2xLuo8aFMpnlILhjUASJU";
                Object dataTransfer[] = new Object[3];
                // dataTransfer[0] = text;
                dataTransfer[0] = url;
                dataTransfer[1]=words.get(i).get("lattitude");
                dataTransfer[2]=words.get(i).get("longitude");
                GetNearbyPlaceDetails nearbyPlaceDetails=new GetNearbyPlaceDetails(Details.this);
                nearbyPlaceDetails.execute(dataTransfer);
            }
        });
    }
}
