package com.example.a65667.road.Item;

public class IdRecordItem {

    private String icName;
    private Integer icID;

    public IdRecordItem(String icName, Integer icID) {
        this.icName = icName;
        this.icID = icID;
    }

    public String getIcName() {
        return icName;
    }

    public void setIcName(String icName) {
        this.icName = icName;
    }

    public Integer getIcID() {
        return icID;
    }

    public void setIcID(Integer icID) {
        this.icID = icID;
    }
}
