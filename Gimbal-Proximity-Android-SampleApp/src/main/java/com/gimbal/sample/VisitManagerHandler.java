/**
 * Copyright (C) 2013 Gimbal, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Qualcomm Retail Solutions, Inc.
 * 
 * The following sample code illustrates various aspects of the Gimbal Android SDK.
 * 
 * The sample code herein is provided for your convenience, and has not been tested or designed to work on any
 * particular system configuration. It is provided pursuant to the License Agreement for Gimbal Manager AS IS, and your
 * use of this sample code, whether as provided or with any modification, is at your own risk. Neither Gimbal, Inc. nor
 * any affiliate takes any liability nor responsibility with respect to the sample code, and disclaims all warranties,
 * express and implied, including without limitation warranties on merchantability, fitness for a specified purpose, and
 * against infringement.
 */
package com.gimbal.sample;

import java.util.Date;
import java.util.LinkedHashMap;

import android.util.Log;

import com.gimbal.proximity.ProximityFactory;
import com.gimbal.proximity.ProximityOptions;
import com.gimbal.proximity.Visit;
import com.gimbal.proximity.VisitListener;
import com.gimbal.proximity.VisitManager;

public class VisitManagerHandler implements VisitListener {

    private static final String TAG = "VisitManagerHandler";

    private final LinkedHashMap<String, TransmitterAttributes> transmitters = new LinkedHashMap<String, TransmitterAttributes>();
    private final VisitManager visitManager = ProximityFactory.getInstance().createVisitManager();
    private ProximityTransmittersActivity activity;

    public void init(ProximityTransmittersActivity activity) {
        this.activity = activity;
    }

    public void stopScanning() {
        visitManager.stop();
    }

    public void startScanning() {
        startScanningWithOptions();
    }

    private void startScanningWithOptions() {
        visitManager.setVisitListener(this);
        ProximityOptions options = new ProximityOptions();
        options.setOption(ProximityOptions.VisitOptionSignalStrengthWindowKey,
                ProximityOptions.VisitOptionSignalStrengthWindowNone);
        visitManager.startWithOptions(options);
    }

    @Override
    public void receivedSighting(Visit visit, Date updateTime, Integer rssi) {
        Log.d(TAG, "I received a sighting! " + visit.getTransmitter().getName() + "and RSSI is:" + rssi);

        String name = visit.getTransmitter().getName();
        TransmitterAttributes attributes = new TransmitterAttributes();
        attributes.setBattery(visit.getTransmitter().getBattery());
        attributes.setIdentifier(visit.getTransmitter().getIdentifier());
        attributes.setName(visit.getTransmitter().getName());
        attributes.setTemperature(visit.getTransmitter().getTemperature());
        attributes.setRssi(rssi);
        attributes.setDepart(false);
        transmitters.put(name, attributes);
        this.activity.addDevice(transmitters);
    }

    @Override
    public void didArrive(Visit visit) {
        Log.d(TAG, "I got ARRIVE for " + visit.getTransmitter().getName());
    }

    @Override
    public void didDepart(Visit visit) {
        Log.d(TAG, "I got DEPART for " + visit.getTransmitter().getName());
        String name = visit.getTransmitter().getName();
        TransmitterAttributes attributes = new TransmitterAttributes();
        attributes.setDepart(true);
        transmitters.put(name, attributes);
        this.activity.addDevice(transmitters);
    }

}
