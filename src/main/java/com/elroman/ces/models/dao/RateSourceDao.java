package com.elroman.ces.models.dao;

import com.elroman.ces.models.RateSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RateSourceDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(RateSource rateSource) {
		getSession().save(rateSource);
		return;
	}

	public void delete(RateSource rateSource) {
		getSession().delete(rateSource);
		return;
	}

	public RateSource getById(String id) {
		return (RateSource) getSession().load(RateSource.class, id);
	}

	public RateSource getByName(String name) {
		return (RateSource) getSession().load(RateSource.class, name);
	}

	public void update(RateSource rateSource) {
		getSession().update(rateSource);
		return;
	}
}
