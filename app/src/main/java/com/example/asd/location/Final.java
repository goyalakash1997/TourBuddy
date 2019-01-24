package com.example.asd.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Final extends AppCompatActivity {

    String s;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        t=findViewById(R.id.text);
        s=getIntent().getStringExtra("EXTRA_SESSION_ID2");
        t.setText(s);
    }
}
