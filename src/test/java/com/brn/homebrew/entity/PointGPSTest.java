package com.brn.homebrew.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 9/12/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class PointGPSTest {
    @Test
    public void testConstructor() {
        PointGPS pointGPS = new PointGPS();
        assertEquals(pointGPS, new PointGPS());
    }

    @Test
    public void testSettersAndGetters() {
        double latitude = 9999999;
        double longitude = 99999999;
        PointGPS pointGPS = new PointGPS(latitude, 99999999);
        assertEquals(pointGPS.getLatitude(), latitude, 0);
        assertEquals(pointGPS.getLongitude(), longitude, 0);
        long latitude2 = 8888;
        long longitude2 = 9999;
        pointGPS.setLatitude(latitude2);
        pointGPS.setLongitude(longitude2);
        assertEquals(pointGPS.getLatitude(), latitude2, 0);
        assertEquals(pointGPS.getLongitude(), longitude2, 0);
    }
}
