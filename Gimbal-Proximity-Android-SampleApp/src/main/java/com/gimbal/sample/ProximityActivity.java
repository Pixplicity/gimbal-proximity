/**
 * Copyright (C) 2013 Gimbal, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Gimbal, Inc.
 * 
 * The following sample code illustrates various aspects of the Gimbal Android SDK.
 * 
 * The sample code herein is provided for your convenience, and has not been tested or designed to
 * work on any
 * particular system configuration. It is provided pursuant to the License Agreement for Gimbal
 * Manager AS IS, and your
 * use of this sample code, whether as provided or with any modification, is at your own risk.
 * Neither Qualcomm Retail
 * Solutions, Inc. nor any affiliate takes any liability nor responsibility with respect to the
 * sample code, and
 * disclaims all warranties, express and implied, including without limitation warranties on
 * merchantability, fitness
 * for a specified purpose, and against infringement.
 */
package com.gimbal.sample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.gimbal.logging.GimbalLogConfig;
import com.gimbal.logging.GimbalLogLevel;
import com.gimbal.proximity.Proximity;
import com.gimbal.proximity.ProximityError;
import com.gimbal.proximity.ProximityListener;

public class ProximityActivity extends Activity implements ProximityListener {

    private static final String PROXIMITY_APP_ID = "274f18f1016e681194f92c796428836aee8e4a5ae2925cca796393290d0c01d1";
    private static final String PROXIMITY_APP_SECRET = "1c3104e7c1c67ea8906cdf3ad9f57a0d82eeb4107472d80906a5a1174e2879b4";

    private static final String PROXIMITY_SERVICE_ENABLED_KEY = "proximity.service.enabled";

    private static final String TAG = ProximityActivity.class.getSimpleName();

    private final static int REQUEST_ENABLE_BT = 1;
    private Switch enableProximitySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "CODENAME: " + Build.VERSION.CODENAME);
        Log.d(TAG, "INCREMENTAL: " + Build.VERSION.INCREMENTAL);
        Log.d(TAG, "RELEASE: " + Build.VERSION.RELEASE);
        Log.d(TAG, "SDK_INT: " + Build.VERSION.SDK_INT);

        View view = View.inflate(this, R.layout.activity_proximity, null);
        setContentView(view);

        initializeProximity();

        String proximityServiceEnabled = getUserPreference(PROXIMITY_SERVICE_ENABLED_KEY);
        if (proximityServiceEnabled != null && Boolean.valueOf(proximityServiceEnabled)) {
            startProximityService();
        }

        enableProximitySwitch = (Switch) view.findViewById(R.id.enableProximitySwitch);
        enableProximitySwitch
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            saveUserPreferrence(PROXIMITY_SERVICE_ENABLED_KEY, String.valueOf(true));
                            startProximityService();
                        }
                    }

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "received callback for activity result with request code " + requestCode
                + " , result code " + resultCode + " , data " + data);

        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "enabled BT. Start oauth session.");
            saveUserPreferrence(PROXIMITY_SERVICE_ENABLED_KEY, String.valueOf(true));
            startProximityService();
        }
        else if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            enableProximitySwitch.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    private void initializeProximity() {
        Log.d(TAG, "initializeProximity");

        GimbalLogConfig.setLogLevel(GimbalLogLevel.INFO);
        GimbalLogConfig.enableFileLogging(this.getApplicationContext());

        Proximity.initialize(this, PROXIMITY_APP_ID, PROXIMITY_APP_SECRET);
        Proximity.optimizeWithApplicationLifecycle(getApplication());
    }

    private void startProximityService() {
        Log.d(ProximityActivity.class.getSimpleName(), "startSession");
        Proximity.startService(this);
    }

    @Override
    public void serviceStarted() {
        Log.d(ProximityActivity.class.getSimpleName(), "serviceStarted");
        showTransmitters();
    }

    @Override
    public void startServiceFailed(int errorCode, String message) {
        showToastMessage("Service Failed");
        Log.e("ProximityActivity", "serviceFailed because of " + message);
        if (errorCode == ProximityError.PROXIMITY_BLUETOOTH_IS_OFF.getCode()) {
            turnONBluetooth();
        }
    }

    private void turnONBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void saveUserPreferrence(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getUserPreference(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void showTransmitters() {
        Log.d(TAG, "session started callback" + ProximityTransmittersActivity.class);

        Intent intent = new Intent(
                "com.example.sampleproximityusingapplication.ProximityTransmittersActivity");
        startActivity(intent);
        finish();
    }

    void showAddTransmitters() {
        Log.d(ProximityActivity.class.getSimpleName(), "session started callback"
                + ProximityTransmittersActivity.class);

        Intent intent = new Intent(
                "com.example.sampleproximityusingapplication.ProximityTransmittersActivity");
        startActivity(intent);
        finish();
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
