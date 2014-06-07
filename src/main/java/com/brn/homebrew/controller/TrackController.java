package com.brn.homebrew.controller;

import com.brn.homebrew.entity.PointGPS;
import com.brn.homebrew.entity.Track;
import com.brn.homebrew.service.TrackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 9/15/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("tracks")
public class TrackController {

    @Autowired
    private TrackManager trackManager;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public List<Track> readAll() {
        List<Track> trackList = trackManager.read();
        for (Track track : trackList) {
            for (PointGPS pointGPS : track.getPointGPSList()) {

            }
        }
        return trackList;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Track read(@PathVariable int id) {
        return trackManager.read(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void create(@RequestBody Track track) {
        trackManager.create(track);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void update(@RequestBody Track track, @PathVariable int id) {
        trackManager.update(track);
    }

    @RequestMapping(value = "{id}/pointsGPS", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<PointGPS> readPointGPSList(@PathVariable int id) {
        return trackManager.read(id).getPointGPSList();
    }

    @RequestMapping(value = "{id}/pointsGPS", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void addPointGPSToTrack(@PathVariable int id, @RequestBody PointGPS pointGPS) {
        /*
            Maybe I should create a manager to points considering that I will have operations over them
         */
        trackManager.addPoint(id, pointGPS);
    }
}
