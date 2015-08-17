package com.ingenious.chameleon;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by muhzi on 7/22/2015.
 */
public class GlobalClass {
    public static Double LATITUDE = null;
    public static Double LONGITUDE = null;

    public static void UpdateLatLng(Double Latitude, Double Longitude) {
        LATITUDE = Latitude;
        LONGITUDE = Longitude;

    }
}
