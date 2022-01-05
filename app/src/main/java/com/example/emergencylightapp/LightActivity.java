package com.example.emergencylightapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class LightActivity extends AppCompatActivity implements SensorEventListener {


    //    Declaring layout elements
    private TextView textViewLuxValue, textViewTorchMode;

    //    Declaring sensor elements
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float changedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        initializedViews();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }



    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent SensorEvent) {
        changedValues = SensorEvent.values[0];
        textViewLuxValue.setText(String.valueOf(changedValues));

        if (changedValues < 15) {

            flashSwitch(true);
            textViewTorchMode.setText("Flash Light is On");

        } else {

            flashSwitch(false);
            textViewTorchMode.setText("Flash Light is Off");
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        flashSwitch(false);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //    Initializing layout elements.
    private void initializedViews() {
        textViewLuxValue = findViewById(R.id.txtViewLuxValue);
        textViewTorchMode = findViewById(R.id.txtViewTorchMode);

    }

    //    Flash Light switch method
    @RequiresApi(api = Build.VERSION_CODES.M)

    private void flashSwitch(boolean input) {

        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String cameraId;
            String cameraVendor = null;

            try {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, input);

            } catch (CameraAccessException e) {

                e.printStackTrace();
            }
        }
    }

}