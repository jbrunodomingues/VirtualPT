package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.entity.PtClientAssociation;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationDaoImpl extends AbstractDao<PtClientAssociation> implements PtClientAssociationDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<PtClientAssociation> readAll() {
        return getSession().createCriteria(getTargetClass()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer) {
        Criteria criteria = getSession().createCriteria(getTargetClass());
        criteria.add(Restrictions.eq("personalTrainer", personalTrainer));
        return criteria.list();
    }

    @Override
    protected Class getTargetClass() {
        return PtClientAssociation.class;
    }
}