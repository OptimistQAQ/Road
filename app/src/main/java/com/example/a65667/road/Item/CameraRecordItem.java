package com.example.a65667.road.Item;

public class CameraRecordItem {
    private String dataTime;
    private String lastTime;
    private String holeCount;
    private String crackCount;
    private String travel;
    private String lno;
    private String videoUrl;
    private String lprocessState;

    public void setLprocessState(String lprocessState) {
        this.lprocessState = lprocessState;
    }

    public String getLprocessState() {
        return lprocessState;
    }


    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setLno(String lno) {
        this.lno = lno;
    }

    public String getLno() {
        return lno;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getHoleCount() {
        return holeCount;
    }

    public void setHoleCount(String holeCount) {
        this.holeCount = holeCount;
    }

    public String getCrackCount() {
        return crackCount;
    }

    public void setCrackCount(String crackCount) {
        this.crackCount = crackCount;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }
}
