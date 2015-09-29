package no.hig.thesupersimplestepcounter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author uthor Jørgen Ellefsrud
 * Followed tutorial by Java Experience (https://www.youtube.com/watch?v=pDz8y5B8GsE)
 * @version $Date$
 *
 */
public class MainCounter extends Activity implements SensorEventListener {
    //initiate variables.
    private SensorManager sensorManager;
    private TextView counter;
    private boolean activityStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_counter);
        //initiate new counter and identify it to the textview with the id count.
        counter = (TextView) findViewById(R.id.count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        //Set to register new steps, initiate sensor. If sensor is busy or unavailable toast an error message.
        activityStep = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        //stops recording steps
        super.onPause();
        activityStep = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityStep) {
            counter.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
