package com.example.a65667.road.bean;


public class User {
    private Integer uno;

    private String uname;

    private String unickName;

    private String upassword;

    private String utitle;

    private String uprofilePhoto;

    private Integer utotalTime;

    private Integer utotalDistance;

    private Integer utotalLine;

    public Integer getUno() {
        return uno;
    }

    public void setUno(Integer uno) {
        this.uno = uno;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getUnickName() {
        return unickName;
    }

    public void setUnickName(String unickName) {
        this.unickName = unickName == null ? null : unickName.trim();
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword == null ? null : upassword.trim();
    }

    public String getUtitle() {
        return utitle;
    }

    public void setUtitle(String utitle) {
        this.utitle = utitle == null ? null : utitle.trim();
    }

    public String getUprofilePhoto() {
        return uprofilePhoto;
    }

    public void setUprofilePhoto(String uprofilePhoto) {
        this.uprofilePhoto = uprofilePhoto == null ? null : uprofilePhoto.trim();
    }

    public Integer getUtotalTime() {
        return utotalTime;
    }

    public void setUtotalTime(Integer utotalTime) {
        this.utotalTime = utotalTime;
    }

    public Integer getUtotalDistance() {
        return utotalDistance;
    }

    public void setUtotalDistance(Integer utotalDistance) {
        this.utotalDistance = utotalDistance;
    }

    public Integer getUtotalLine() {
        return utotalLine;
    }

    public void setUtotalLine(Integer utotalLine) {
        this.utotalLine = utotalLine;
    }
}