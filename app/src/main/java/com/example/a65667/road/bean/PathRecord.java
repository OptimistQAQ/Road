package com.example.a65667.road.bean;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于记录一条轨迹，包括起点、终点、轨迹中间点、距离、耗时、平均速度、时间
 *
 */

public class PathRecord {
    private AMapLocation mStartPoint;
    private AMapLocation mEndPoint;
    private List<AMapLocation> mPathLinePoints = new ArrayList<AMapLocation>();
    private String mDistance;
    private String mDuration;
    private String mAveragespeed;
    private String mData;
    private int mId = 0;

    public PathRecord(){

    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public AMapLocation getStartPoint(){
        return mStartPoint;
    }

    public void setStartPoint(AMapLocation startpoint) {
        this.mStartPoint = startpoint;
    }

    public AMapLocation getEndPoint() {
        return mEndPoint;
    }

    public void setEndPoint(AMapLocation endpoint) {
        this.mEndPoint = endpoint;
    }

    public List<AMapLocation> getPathline() {
        return mPathLinePoints;
    }

    public void setPathline(List<AMapLocation> pathline) {
        this.mPathLinePoints = pathline;
    }

    public String getmDistance() {
        return mDistance;
    }

    public void setmDistance(String distance) {
        this.mDistance = distance;
    }

    public String getmDuration() {
        return mDuration;
    }

    public void setmDuration(String duration) {
        this.mDuration = duration;
    }

    public String getmAveragespeed() {
        return mAveragespeed;
    }

    public void setmAveragespeed(String averagespeed) {
        this.mAveragespeed = averagespeed;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String data) {
        this.mData = data;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record.append("距离：").append(getmDistance()).append("m, ");
        record.append("耗时：").append(getmDuration()).append("s, ");
        double km = Double.parseDouble(getmDistance()) / Double.parseDouble(getmDuration());
        record.append("平均速度：").append(String.format("%.2f", km)).append("m/s");
        return super.toString();
    }
}
