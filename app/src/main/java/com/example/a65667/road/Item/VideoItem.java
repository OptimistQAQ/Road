package com.example.a65667.road.Item;

import java.util.Date;

public class VideoItem {

    private String fileName;
    private String fileUrl;
    private Date date;
    private Integer duration;   //持续时间
    private String uriNumber;
    private String fileImg;

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }

    public String getFileImg() {
        return fileImg;
    }

    public String getUriNumber() {
        return uriNumber;
    }

    public void setUriNumber(String uriNumber) {
        this.uriNumber = uriNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }
}
