package com.brn.homebrew.entity;

import sun.security.ssl.Debug;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 9/12/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TRACK")
public class Track {

    @Id
    @Column(name = "ID_TRACK")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "REFID_TRACK")
    private List<PointGPS> pointGPSList;

    public Track() {
    }

    public void setPointGPSList(List<PointGPS> pointGPSList) {
        this.pointGPSList = pointGPSList;
    }

    public List<PointGPS> getPointGPSList() {
        return pointGPSList;
    }

    public long calculateAcumulatedDistance() {
        long acumulatedDistance = 0;
        if (pointGPSList.size() > 1) {
            for (int i = 1; i < pointGPSList.size(); i++) {
                acumulatedDistance += calculateDistanceBetween2PointsGPS(pointGPSList.get(i - 1), pointGPSList.get(i));
            }
        }
        return acumulatedDistance;
    }

    private long calculateDistanceBetween2PointsGPS(PointGPS pointGPS1, PointGPS pointGPS2) {
        return calculateHaversineDistanceMeters(pointGPS1.getLatitude(), pointGPS1.getLongitude(), pointGPS2.getLatitude(), pointGPS2.getLongitude());
    }

    public long calculateHaversineDistanceMeters(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radious of the earth
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c;
        return Math.round(distance*Math.pow(10,6));
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (pointGPSList != null ? !pointGPSList.equals(track.pointGPSList) : track.pointGPSList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pointGPSList != null ? pointGPSList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", pointGPSList=" + pointGPSList +
                '}';
    }
}
