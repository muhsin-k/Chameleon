package com.ingenious.chameleon;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ProfileSettings extends ActionBarActivity {
    Button navAddProfile, navViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        navAddProfile = (Button) findViewById(R.id.btnAddProfiles);
        navAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSettings.this, AddProfile.class);
                startActivity(intent);
            }
        });
        navViewProfile = (Button) findViewById(R.id.btnViewProfiles);
        navViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSettings.this, ViewProfiles.class);
                startActivity(intent);
            }
        });

    }

}
