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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class ProximityBluetoothSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_bluetooth_settings);
    }
    
    public void onSwitchToggle(View view) {
        boolean on = ((Switch) view).isChecked();
        String toggleMsg = null;
        if (on) {
            toggleMsg = "Bluetooth scanning enabled.";
        } else {
            toggleMsg = "Bluetooth scanning disabled.";
        }
        Toast toast = Toast.makeText(getApplicationContext(), toggleMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
