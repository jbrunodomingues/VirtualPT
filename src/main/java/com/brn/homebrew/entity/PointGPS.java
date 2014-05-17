package com.brn.homebrew.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 9/12/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "POINT_GPS")
public class PointGPS {

    @Id
    @Column(name = "ID_POINT_GPS")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "REFID_TRACK")
//    private Track track;


    public PointGPS() {
    }

    public PointGPS(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointGPS pointGPS = (PointGPS) o;

        if (Double.compare(pointGPS.latitude, latitude) != 0) return false;
        if (Double.compare(pointGPS.longitude, longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PointGPS{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
