package com.example.a65667.road.bean;

public class CrackDetail {
    private Integer uno;
    private String lno;
    private String con;
    private Double lon;
    private Double lat;
    private String frame;
    private String source_img;
    private String seg_img;

    public void setCon(String con) {
        this.con = con;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLno(String lno) {
        this.lno = lno;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setSeg_img(String seg_img) {
        this.seg_img = seg_img;
    }

    public void setSource_img(String source_img) {
        this.source_img = source_img;
    }

    public void setUno(Integer uno) {
        this.uno = uno;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Integer getUno() {
        return uno;
    }

    public String getCon() {
        return con;
    }

    public String getFrame() {
        return frame;
    }

    public String getLno() {
        return lno;
    }

    public String getSeg_img() {
        return seg_img;
    }

    public String getSource_img() {
        return source_img;
    }
}
