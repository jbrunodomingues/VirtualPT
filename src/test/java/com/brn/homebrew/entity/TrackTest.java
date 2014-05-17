package com.brn.homebrew.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 9/12/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackTest {

    private long distance1;
    private long distance2;
    private Track track;

    @Before
    public void init() {
        track = new Track();
        distance1 = 1435332879;
        distance2 = 1359254526;
    }

    @Test
    public void testConstructor() {
        assertEquals(track, new Track());
    }

    @Test
    public void setTracksTest() {
        List<PointGPS> pointGPSList = new ArrayList<>();
        track.setPointGPSList(pointGPSList);
        assertEquals(pointGPSList, track.getPointGPSList());
    }

    @Test
    public void testAccumulatedDistance() {
        List<PointGPS> pointGPSList = new ArrayList<>();
        pointGPSList.add(new PointGPS(30, 30));
        pointGPSList.add(new PointGPS(40, 40));
        track.setPointGPSList(pointGPSList);
        assertEquals(distance1, track.calculateAcumulatedDistance());
        pointGPSList.add(new PointGPS(50, 50));
        long distance3 = distance1 + distance2;
        assertEquals(distance3, track.calculateAcumulatedDistance());
    }

    @Test
    public void testCalculateStraightLineDistance() {
        long distance = track.calculateHaversineDistanceMeters(30D, 30D, 40D, 40D);
        assertEquals(distance1, distance);
        distance = track.calculateHaversineDistanceMeters(40D, 40D, 50D, 50D);
        assertEquals(distance2, distance);
    }
}
