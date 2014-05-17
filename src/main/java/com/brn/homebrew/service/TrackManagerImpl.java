package com.brn.homebrew.service;

import com.brn.homebrew.dao.TrackDAO;
import com.brn.homebrew.entity.PointGPS;
import com.brn.homebrew.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bruno
 * Date: 12/5/13
 * Time: 10:46 PM
 */
@Service
public class TrackManagerImpl implements TrackManager {

    @Autowired
    private TrackDAO trackDAO;

    @Override
    @Transactional
    public void addPoint(int id, PointGPS pointGPS) {
        Track track = trackDAO.read(id);
        track.getPointGPSList().add(pointGPS);
        trackDAO.update(track);
    }

    @Override
    @Transactional
    public void create(Track track) {
        trackDAO.create(track);
    }

    @Override
    public List<Track> read() {
        List<Track> trackList = trackDAO.read();
        return trackList;
    }

    @Override
    public Track read(int id) {
        return trackDAO.read(id);
    }

    @Override
    public void update(Track track) {
        trackDAO.update(track);
    }
}
