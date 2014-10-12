package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import org.hibernate.Criteria;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

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
        criteria.add(eq("personalTrainer", personalTrainer));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PtClientAssociation> readAllFromClient(Client client) {
        Criteria criteria = getSession().createCriteria(getTargetClass());
        criteria.add(eq("client", client));
        return criteria.list();
    }

    @Override
    protected Class getTargetClass() {
        return PtClientAssociation.class;
    }
}