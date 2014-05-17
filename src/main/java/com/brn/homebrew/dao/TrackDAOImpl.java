package com.brn.homebrew.dao;

import com.brn.homebrew.entity.PointGPS;
import com.brn.homebrew.entity.Track;
import com.brn.homebrew.infrastructure.NoDataFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bruno
 * Date: 12/5/13
 * Time: 10:52 PM
 */
@Repository
public class TrackDAOImpl implements TrackDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addPoint(PointGPS pointGPS) {
        this.sessionFactory.getCurrentSession().save(pointGPS);
    }

    @Override
    public void create(Track track) {
        this.sessionFactory.getCurrentSession().save(track);
    }

    @Override
    public List<Track> read() {
        List<Track> trackList = this.sessionFactory.getCurrentSession().createCriteria(Track.class).list();
        return trackList;
    }

    @Override
    public Track read(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Track.class);
        criteria.add(Restrictions.eq("id", id));
        List<Track> list = criteria.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            throw new NoDataFoundException("exception_no_track_for_id");
        }
    }

    @Override
    public void update(Track track) {
        read(track.getId());
        this.sessionFactory.getCurrentSession().save(track);
    }
}
