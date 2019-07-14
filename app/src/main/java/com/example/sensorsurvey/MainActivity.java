package com.example.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
private  Sensor mSensorProximity;
private Sensor mSensorLight;


private TextView mTextSensorLight;
private TextView mTextSensorProximity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

mTextSensorLight =  findViewById(R.id.label_light);
mTextSensorProximity =  findViewById(R.id.label_proximity);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

String sensor_error = getResources().getString(R.string.error_no_sensor);

if(mSensorLight == null){
    mTextSensorLight.setText(sensor_error);
}

if(mSensorProximity== null){
    mTextSensorProximity.setText(sensor_error);

}



        List<Sensor> sensorList =
                mSensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList) {

                sensorText.append(currentSensor.getName())
                        .append(System.getProperty("line.separator"));
            //TextView sensorTextView = (TextView) findViewById(R.id.sensor_list);
            //sensorTextView.setText(sensorText);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch(sensorType){

            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue));
                break;

            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue));
                break;


                default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
