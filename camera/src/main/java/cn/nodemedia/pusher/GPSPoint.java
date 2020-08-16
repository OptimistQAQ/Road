package cn.nodemedia.pusher;

public class GPSPoint {

    private double Latitude;
    private double Longitude;

    public GPSPoint(){
        super();
    }
    public GPSPoint(double lat, double lon){
        super();
        this.Latitude = lat;
        this.Longitude = lon;
    }
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
