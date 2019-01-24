package com.example.asd.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asd on 8/5/2018.
 */

public class WordAdapter extends ArrayAdapter<HashMap<String,String>> {
    private Context mContext;
    int mresource;
    private int mColorResourceId;
    public WordAdapter(Context context, int resource, ArrayList<HashMap<String,String>> words) {
        super(context, 0, words);
        mContext=context;
        mresource=resource;
        //mColorResourceId=colorResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mresource, parent, false);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.details_item, parent, false);
        }
        String name=getItem(position).get("place_name");
        TextView text=listItemView.findViewById(R.id.text_view);
        text.setText(name);
        //int image=getItem(position).getmImageResourceId();
        //ImageView imageView=listItemView.findViewById(R.id.image2);
        //imageView.setImageResource(image);
        //int color= ContextCompat.getColor(getContext(),mColorResourceId);
        //View text_container=listItemView.findViewById(R.id.text_container);
        //text_container.setBackgroundColor(color);
        return listItemView;
    }
}

