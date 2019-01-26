package com.example.asd.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Details2 extends AppCompatActivity {
    ArrayList<HashMap<String,String>> words =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details);
        words=(ArrayList<HashMap<String, String>>)getIntent().getSerializableExtra("EXTRA_SESSION_ID");
        WordAdapter2 adapter = new WordAdapter2(this,R.layout.details_item2, words);

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
                String url="https://maps.googleapis.com/maps/api/place/details/json?placeid=" + s+ "&fields=name,rating,formatted_phone_number,formatted_address&key=AIzaSyBR1zWS3xmW7UHyKlXKVajMpyO17Prj6SE";
                Object dataTransfer[] = new Object[3];
                // dataTransfer[0] = text;
                dataTransfer[0] = url;
                dataTransfer[1]=words.get(i).get("lattitude");
                dataTransfer[2]=words.get(i).get("longitude");
                GetNearbyPlaceDetails nearbyPlaceDetails=new GetNearbyPlaceDetails(Details2.this);
                nearbyPlaceDetails.execute(dataTransfer);
            }
        });
    }
}
