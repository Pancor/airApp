package pl.pancor.android.air;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.pancor.android.air.nearest_station.NearestStationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent nearestStationActivity = new Intent(this, NearestStationActivity.class);
        nearestStationActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nearestStationActivity);
        finish();
    }
}
