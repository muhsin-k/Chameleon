package com.ingenious.chameleon;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by muhzi on 7/21/2015.
 */
public class SmsReceiver extends BroadcastReceiver implements LocationListener {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    String TAG = "android";
    Geocoder geocoder;
    List<Address> addresses;

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    Log.i(TAG, "Message-->" + message);
                    Log.i(TAG, "Number--->" + senderNum);
                    Log.i(TAG, "Code-->" + message.substring(0, 9));
                    String Code = new String("CHAMELEON");
                    if (message.substring(0, 9).equals(Code)) {
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        try {
                            // Getting LocationManager object from System Service LOCATION_SERVICE
                            geocoder = new Geocoder(context, Locale.getDefault());
                            addresses = geocoder.getFromLocation(GlobalClass.LATITUDE, GlobalClass.LONGITUDE, 1);
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();
                            String Message = "ChameLeon Alert:\n The Phone is located near " + address + "," + city + "," + state + "," + country + ".";
                            Log.i(TAG, "Message-->" + Message);
                            Log.i(TAG, "Reciver Number" + senderNum.substring(3, 13));
                            SmsManager smsManager = SmsManager.getDefault();

                            smsManager.sendTextMessage(senderNum, null, Message, null, null);
                            Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.i(TAG, "Error..........!!!!!!!!!" + e.toString());
                            Toast.makeText(context, "SMS sending failed.", Toast.LENGTH_LONG).show();
                        }

                    }
                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

    @Override
    public void onLocationChanged(Location location) {

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