package com.ingenious.chameleon;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddProfile extends ActionBarActivity {
    //Filter logging
    String TAG = "android";
    EditText txtLocation;
    TextView lblLong, lblLat;
    Button saveProfile, sentMessage;
    RadioGroup rgGroup;
    RadioButton rbMode;
    DBHelper dbHelper;
    //Phone Locking
    private static final int ADMIN_INTENT = 15;
    private static final String description = "Sample Administrator description";
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        //Phone Locking
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);

        lblLat = (TextView) findViewById(R.id.lblLatitude);
        lblLong = (TextView) findViewById(R.id.lblLongitude);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        rgGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lblLat.setText(GlobalClass.LATITUDE.toString());
        lblLong.setText(GlobalClass.LONGITUDE.toString());
        //Data base Connection
        dbHelper = new DBHelper(this);
        saveProfile = (Button) findViewById(R.id.btnSaveProfile);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMode = (RadioButton) findViewById(rgGroup.getCheckedRadioButtonId());
                Log.i(TAG, "Location-->" + txtLocation.getText().toString());
                Log.i(TAG, "Mode-->" + rbMode.getText().toString());
                Log.i(TAG, "Latitude-->" + lblLat.getText().toString());
                Log.i(TAG, "Longitude-->" + lblLong.getText().toString());
                Log.i(TAG, "Insert Data-->" + dbHelper.insertProfile(txtLocation.getText().toString(), lblLat.getText().toString(), lblLong.getText().toString(), rbMode.getText().toString()));

            }
        });
//        sentMessage = (Button) findViewById(R.id.sentMessage);
//        sentMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Lock device
//                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
//                KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//                lock.reenableKeyguard();
////                wl.release();
//                Log.i(TAG, "after lock");
////                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
////                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
////                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
////                startActivityForResult(intent, ADMIN_INTENT);
////                boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);
////                if (isAdmin) {
////                    Log.i(TAG, "admin");
////                    mDevicePolicyManager.lockNow();
////                } else {
////                    Log.i(TAG, "Not an admin");
////                    Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
////                }
//
//            }
//        });
    }
}
