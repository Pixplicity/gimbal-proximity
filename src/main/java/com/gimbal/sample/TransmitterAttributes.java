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

public class TransmitterAttributes {

    private String identifier;
    private String name;
    private String ownerId;
    private String iconUrl;
    private Integer battery;
    private Integer temperature;
    private int rssi;
    private boolean depart;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public boolean isDepart() {
        return depart;
    }

    public void setDepart(boolean depart) {
        this.depart = depart;
    }

}
