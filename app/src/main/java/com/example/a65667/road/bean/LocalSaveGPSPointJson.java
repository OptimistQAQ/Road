package com.example.a65667.road.bean;

import java.util.List;

import cn.nodemedia.pusher.GPSPoint;

public class LocalSaveGPSPointJson {
    private List<GPSPoint> gpsPoints;

    public LocalSaveGPSPointJson(){

    }
    public LocalSaveGPSPointJson(List<GPSPoint> gpsPoints){
        this.gpsPoints = gpsPoints;
    }

    public List<GPSPoint> getGpsPoints() {
        return gpsPoints;
    }

    public void setGpsPoints(List<GPSPoint> gpsPoints) {
        this.gpsPoints = gpsPoints;
    }
}
