package com.example.emergencylightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //    Declaring layout elements
    private Button btnTurnOn;

    private TextView txtViewAbout;

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

        txtViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Long tap to view About the developers", Toast.LENGTH_SHORT).show();
            }
        });

        txtViewAbout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "This app is developed by CST students", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void initializedViews() {
        btnTurnOn = findViewById(R.id.btnTurnOn);
        txtViewAbout = findViewById(R.id.txtViewAbout);
    }
}