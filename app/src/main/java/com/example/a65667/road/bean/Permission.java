package com.example.a65667.road.bean;

public class Permission {

    String uNo;
    boolean pBase;
    boolean pPhoto_seg;
    boolean pVideo_seg;
    boolean pInfo_seg;
    boolean pPermission_admin;

    public Permission(boolean pBase, boolean pPhoto_seg, boolean pVideo_seg, boolean pInfo_seg, boolean pPermission_admin){
        this.pBase = pBase;
        this.pPhoto_seg = pPhoto_seg;
        this.pVideo_seg = pVideo_seg;
        this.pInfo_seg = pInfo_seg;
        this.pPermission_admin = pPermission_admin;
    }

    public void setpVideo_seg(boolean pVideo_seg) {
        this.pVideo_seg = pVideo_seg;
    }

    public void setpPhoto_seg(boolean pPhoto_seg) {
        this.pPhoto_seg = pPhoto_seg;
    }

    public void setpPermission_admin(boolean pPermission_admin) {
        this.pPermission_admin = pPermission_admin;
    }

    public void setpInfo_seg(boolean pInfo_seg) {
        this.pInfo_seg = pInfo_seg;
    }

    public void setpBase(boolean pBase) {
        this.pBase = pBase;
    }

    public void setuNo(String uNo) {
        this.uNo = uNo;
    }

    public String getuNo() {
        return uNo;
    }
}
