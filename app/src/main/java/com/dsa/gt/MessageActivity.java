package com.dsa.gt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageActivity extends AppCompatActivity {

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        bundle=getIntent().getExtras();

    }
}
