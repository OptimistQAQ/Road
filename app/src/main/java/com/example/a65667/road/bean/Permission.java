package com.example.a65667.road.bean;

public class Permission {

    private String uno;

    private Boolean pbase;

    private Boolean pphotoSeg;

    private Boolean pvideoSeg;

    private Boolean pinfoCheck;

    private Boolean ppermissionAdmin;

//    public Permission(String uno, Boolean pbase, Boolean pphotoSeg, Boolean pvideoSeg, Boolean pinfoCheck, Boolean ppermissionAdmin){
//        this.uno = uno;
//        this.pbase = pbase;
//        this.pphotoSeg = pphotoSeg;
//        this.pvideoSeg = pvideoSeg;
//        this.pinfoCheck = pinfoCheck;
//        this.ppermissionAdmin = ppermissionAdmin;
//    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public Boolean getPbase() {
        return pbase;
    }

    public void setPbase(Boolean pbase) {
        this.pbase = pbase;
    }

    public Boolean getPphotoSeg() {
        return pphotoSeg;
    }

    public void setPphotoSeg(Boolean pphotoSeg) {
        this.pphotoSeg = pphotoSeg;
    }

    public Boolean getPvideoSeg() {
        return pvideoSeg;
    }

    public void setPvideoSeg(Boolean pvideoSeg) {
        this.pvideoSeg = pvideoSeg;
    }

    public Boolean getPinfoCheck() {
        return pinfoCheck;
    }

    public void setPinfoCheck(Boolean pinfoCheck) {
        this.pinfoCheck = pinfoCheck;
    }

    public Boolean getPpermissionAdmin() {
        return ppermissionAdmin;
    }

    public void setPpermissionAdmin(Boolean ppermissionAdmin) {
        this.ppermissionAdmin = ppermissionAdmin;
    }

}
