package com.example.emergencylightapp;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LightActivity extends AppCompatActivity implements SensorEventListener {


    //    Declaring layout elements
    private TextView textViewLuxValue, textViewTorchMode, textViewLuxThreshold;
    private ImageView imgViewTorchOn, imgViewTorchOff;
    private SeekBar seekBarLuxThreshold;

    //    Declaring sensor elements
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float changedValues, luxThreshold = 15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        initializedViews();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        seekBarLuxThreshold.setProgress(((int) luxThreshold));
        seekBarLuxThreshold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewLuxThreshold.setText("Lux Threshold : " + progress);
                luxThreshold = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        textViewLuxValue.setText("LUX Value: " + changedValues);

        if (changedValues < luxThreshold) {

            flashSwitch(true);
            textViewTorchMode.setText("Flash Light is On");
            imgViewTorchOff.setVisibility(View.GONE);
            imgViewTorchOn.setVisibility(View.VISIBLE);

        } else {

            flashSwitch(false);
            textViewTorchMode.setText("Flash Light is Off");
            imgViewTorchOff.setVisibility(View.VISIBLE);
            imgViewTorchOn.setVisibility(View.GONE);
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
        imgViewTorchOn = findViewById(R.id.imgViewTorchOn);
        imgViewTorchOff = findViewById(R.id.imgViewTorchOff);
        textViewLuxThreshold = findViewById(R.id.textViewLuxThreshold);
        seekBarLuxThreshold = findViewById(R.id.seekBarLuxThreshold);

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