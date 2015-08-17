package com.ingenious.chameleon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewProfiles extends ActionBarActivity {
    DBHelper db;
    ListView listView;
    StringBuffer buffer;
    String TAG = "android";
    String[] NAMES = new String[]{};
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profiles);
        View backgroundimage = findViewById(R.id.view);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
        db = new DBHelper(this);
        Cursor cursor = db.getAllProfiles();
        cursor.moveToFirst();
        listView = (ListView) findViewById(R.id.listProfiles);
        String[] fromCol = new String[]{DBHelper.PROFILES_COLUMN_LOCATION, DBHelper.PROFILES_COLUMN_LATITUDE, DBHelper.PROFILES_COLUMN_LONGITUDE, DBHelper.PROFILES_COLUMN_MODE};
        int[] toView = new int[]{R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, fromCol, toView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                itemId = (int) id;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewProfiles.this);

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete ..!");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.delete);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        Log.i(TAG, "Item Deleted--->" + db.deleteProfile(itemId));

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            }
        });

    }


}
