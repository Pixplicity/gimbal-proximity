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

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransmitterListAdapter extends BaseAdapter {

    private static final String TAG = "TransmitterListAdapter";

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private int position;
    private LinkedHashMap<String, TransmitterAttributes> mEntries = new LinkedHashMap<String, TransmitterAttributes>();

    public TransmitterListAdapter(Context context, Activity activity, VisitManagerHandler manager) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;

        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.list_proximitytransmitters, parent, false);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        TextView tempText = (TextView) itemView.findViewById(R.id.temperatureTextField);
        ImageView batteryImg = (ImageView) itemView.findViewById(R.id.batteryImageView);

        ArrayList<TransmitterAttributes> arrayofAllTransmitter = new ArrayList<TransmitterAttributes>(
                mEntries.values());
        if (position >= arrayofAllTransmitter.size()) {
            return itemView;
        }

        TransmitterAttributes transmitter = arrayofAllTransmitter.get(position);
        Log.d(TAG, "Position is " + position + "Transmitter name is " + transmitter.getName());

        if (transmitter.isDepart() == true) {
            // if (!currentTransmitter.containsKey(transmitter.getName())) {
            float alphavalue = 0.3f;
            itemView.setAlpha(alphavalue);
        } else {
            itemView.setAlpha(1);
        }

        titleText.setText(transmitter.getName());
        if (transmitter.getTemperature() != null) {
            tempText.setText(Integer.toString(transmitter.getTemperature()) + "\u2109");
        } else {
            tempText.setText("???" + "\u2109");
        }
        int battery = 0;
        if (transmitter.getBattery() != null) {
            battery = transmitter.getBattery();
        }
        switch (battery) {
        case 3:
            batteryImg.setImageResource(R.drawable.battery_full);
            break;
        case 2:
            batteryImg.setImageResource(R.drawable.battery_high);
            break;
        case 1:
            batteryImg.setImageResource(R.drawable.battery_med);
            break;

        default:
            batteryImg.setImageResource(R.drawable.battery_low);
            break;
        }

        titleText.setText(transmitter.getName());
        final ProgressBar bar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        int progress = 0;
        int maxProgress = bar.getMax();
        int rssi = transmitter.getRssi();
        Log.d("TransmitterListAdapter", "RSSI for " + transmitter.getName() + "is: " + rssi
                + " maxProgress is " + maxProgress);
        progress = progressValue(rssi);

        bar.setProgress(progress);

        return itemView;
    }

    public synchronized void addTransmitters(LinkedHashMap<String, TransmitterAttributes> entries) {
        mEntries = entries;
        super.notifyDataSetChanged();
    }

    public synchronized void removeTransmitters() {
        mEntries.clear();
        super.notifyDataSetChanged();
    }

    public void upDateEntries(LinkedHashMap<String, TransmitterAttributes> entries) {
        mEntries = entries;
        super.notifyDataSetChanged();
    }

    public int progressValue(int rssi) {
        double progressValue = 0;
        if (rssi >= -60) {
            progressValue = 100;
        }
        else if (rssi <= -90) {
            progressValue = 10;
        }
        else {
            double range = -60.0 - -90.0;
            double percentage = (-60.0 - rssi) / range;
            progressValue = (1 - percentage) * 100;
        }

        int value = (int) progressValue;
        Log.d("TransmitterListAdapter", "int progressvalue is :" + value);

        return value;
    }

}
