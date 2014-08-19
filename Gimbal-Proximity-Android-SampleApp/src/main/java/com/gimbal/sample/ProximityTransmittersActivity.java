/**
 * Copyright (C) 2013 Gimbal, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Qualcomm
 * Retail Solutions, Inc.
 * 
 * The following sample code illustrates various aspects of the Gimbal Android
 * SDK.
 * 
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided pursuant to the License Agreement for Gimbal Manager AS IS, and your
 * use of this sample code, whether as provided or with any modification, is at
 * your own risk. Neither Gimbal, Inc. nor any affiliate
 * takes any liability nor responsibility with respect to the sample code, and
 * disclaims all warranties, express and implied, including without limitation
 * warranties on merchantability, fitness for a specified purpose, and against
 * infringement.
 */
package com.gimbal.sample;

import java.util.LinkedHashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class ProximityTransmittersActivity extends ListActivity {

    private VisitManagerHandler manager;
    private TransmitterListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(ProximityTransmittersActivity.class.getSimpleName(), "onCreate()");
        setContentView(R.layout.transmitter_list_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.proximitytransmitters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return false;
        }
    }
    

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ProximityTransmittersActivity.class.getSimpleName(), "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ProximityTransmittersActivity.class.getSimpleName(), "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ProximityTransmittersActivity.class.getSimpleName(), "onResume()");

        setProgressBarVisibility(true);
     
        if(manager == null){
            manager = new VisitManagerHandler();
            manager.init(this);
            adapter = new TransmitterListAdapter(this, this, manager);
            setListAdapter(adapter);
            manager.startScanning();
        }

        ImageButton imageButtonRefresh = (ImageButton) findViewById(R.id.imageButton_refresh);

        imageButtonRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter.removeTransmitters();
            	adapter.notifyDataSetChanged();
			}
        
        });

    }

    
    protected synchronized void addDevice(final LinkedHashMap<String, TransmitterAttributes> entries) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	adapter.addTransmitters(entries);
            	adapter.notifyDataSetChanged();
            }
        });
    }
}
