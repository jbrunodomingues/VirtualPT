package com.brn.homebrew.dao;

import com.brn.homebrew.entity.PointGPS;
import com.brn.homebrew.entity.Track;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bruno
 * Date: 12/5/13
 * Time: 10:53 PM
 */
public interface TrackDAO {
    void addPoint(PointGPS pointGPS);

    void create(Track track);

    List<Track> read();

    Track read(int id);

    void update(Track track);
}
