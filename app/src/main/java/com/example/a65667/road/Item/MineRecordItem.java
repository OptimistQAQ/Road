package com.example.a65667.road.Item;

public class MineRecordItem {

    private String dataTime;
    private String lastTime;
    private String holeCount;
    private String crackCount;
    private String travel;
    private String lno;

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