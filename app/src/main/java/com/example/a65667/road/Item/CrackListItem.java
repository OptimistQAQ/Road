package com.example.a65667.road.Item;

public class CrackListItem {

    private String rc_name;
    private String rc_distance;
    private String rc_num;

    public CrackListItem(String rc_name, String rc_distance, String rc_num) {
        this.rc_name = rc_name;
        this.rc_distance = rc_distance;
        this.rc_num = rc_num;
    }

    public CrackListItem() {

    }

    public String getRc_name() {
        return rc_name;
    }

    public void setRc_num(String rc_num) {
        this.rc_num = rc_num;
    }

    public String getRc_distance() {
        return rc_distance;
    }

    public void setRc_distance(String rc_distance) {
        this.rc_distance = rc_distance;
    }

    public String getRc_num() {
        return rc_num;
    }

    public void setRc_name(String rc_name) {
        this.rc_name = rc_name;
    }
}
