package com.example.gamification.dto.attendance;

public class AttendanceRequest {

    private Double latitude;
    private Double longitude;
    private String wifiName;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getWifiName() {
        return wifiName;
    }
}