package com.example.emergencylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    Declaring layout elements
    private Button btnTurnOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Initializing the layout elements with initializedViews method
        initializedViews();

//        Navigating to LightActivity
        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LightActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initializedViews() {
        btnTurnOn = findViewById(R.id.btnTurnOn);
    }
}