package com.ingenious.chameleon;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class MainActivity extends ActionBarActivity implements LocationListener {
    //Log Filtering
    String TAG = "android";
    Switch profileMode;
    Button navigate;
    //DB Connection
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitLocation();
        navigate = (Button) findViewById(R.id.btnNavProfileSettings);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileSettings.class);
                startActivity(intent);
            }
        });

    }

    public void InitLocation() {
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria locationCritera = new Criteria();

        // Getting the name of the best provider
        String provider =
                locationManager.getBestProvider(locationCritera, true);
        Log.i(TAG, "Best Location Provider-->" + provider);
        // Getting Current Location From GPS
        Location location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 0, 0, this);
        Log.i(TAG, "Location-->" + location);
        if (location != null) {
            onLocationChanged(location);
        } else {
            Log.i(TAG, "Location is Null");
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Inside OnLocationChanged() Method");
        //Upadte location values using GLobal class
        GlobalClass.UpdateLatLng(location.getLatitude(), location.getLongitude());
        db = new DBHelper(this);
        Cursor cursor = db.getAllProfilesForComparisons();
        int i = 0;
        while (cursor.moveToNext()) {
            Location dbLocation = new Location("");
            dbLocation.setAltitude(Double.parseDouble(cursor.getString(2)));
            dbLocation.setLongitude(Double.parseDouble(cursor.getString(3)));
            Log.i(TAG, "Compare[" + i + "]---->" + location.distanceTo(dbLocation));
//            String msg = "Compare[" + i + "]---->" + location.distanceTo(dbLocation);
//            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            if (location.distanceTo(dbLocation) <= 1500000) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (cursor.getString(4).equals(new String("Silent")))
                    audioManager.setRingerMode(audioManager.RINGER_MODE_SILENT);
                if (cursor.getString(4).equals(new String("Vibrate"))) {
                    audioManager.setRingerMode(audioManager.RINGER_MODE_VIBRATE);
                }
                if (cursor.getString(4).equals(new String("General"))) {
                    audioManager.setRingerMode(audioManager.RINGER_MODE_NORMAL);
                }
            }
            i++;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
