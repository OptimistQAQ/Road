package com.example.a65667.road;

import android.util.Log;

import java.util.ArrayList;

import cn.nodemedia.pusher.GPSPoint;
import cn.nodemedia.pusher.ShareBean;

public class RecordGPSAndUpload extends Thread {

    @Override
    public void run() {
        ShareBean.gpsPoints = new ArrayList<>();

        while (true) {
            Log.e("rgpsing", ShareBean.Latitude + " " + ShareBean.Longitude);

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
                ShareBean.gpsPoints.add(new GPSPoint(ShareBean.Latitude, ShareBean.Longitude));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
