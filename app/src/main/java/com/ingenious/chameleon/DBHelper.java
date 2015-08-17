package com.ingenious.chameleon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Chameleon.db";
    public static final String PROFILES_TABLE_NAME = "profiles";
    public static final String PROFILES_COLUMN_ID = "id";
    public static final String PROFILES_COLUMN_LOCATION = "location";
    public static final String PROFILES_COLUMN_LATITUDE = "latitude";
    public static final String PROFILES_COLUMN_LONGITUDE= "longitude";
    public static final String PROFILES_COLUMN_MODE = "mode";
    private HashMap hp;
    String TAG = "android";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        db.execSQL("DROP TABLE IF EXISTS profiles");
        db.execSQL(
                "create table profiles " +
                        "(id integer primary key, location text,latitude text,longitude text, mode text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS profiles");
        onCreate(db);
    }

    public boolean insertProfile(String location, String latitude, String longitude, String mode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("location", location);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("mode", mode);
        db.insert("profiles", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from profiles where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROFILES_TABLE_NAME);
        return numRows;
    }

    public boolean updateProfile(Integer id, String location, String latitude, String longitude, String mode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("location", location);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("mode", mode);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteProfile(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("profiles",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }
    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select id _id,* from profiles", null);
        return res;
    }
    public Cursor getAllProfilesForComparisons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from profiles", null);
        return res;
    }


}