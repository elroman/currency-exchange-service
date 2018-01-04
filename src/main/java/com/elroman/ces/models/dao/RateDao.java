package com.elroman.ces.models.dao;

import com.elroman.ces.models.Rate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RateDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Rate rate) {
		getSession().save(rate);
		return;
	}

	public void delete(Rate rate) {
		getSession().delete(rate);
		return;
	}

	public Rate getLastBySourceId(String sourceId) {
		return (Rate) getSession().createQuery(
				"from Rate where sourceId = :sourceId")
				.setParameter("sourceId", sourceId)
				.uniqueResult();
	}

	public Rate getById(String id) {
		return (Rate) getSession().get(Rate.class, id);
	}

	public void update(Rate rate) {
		getSession().update(rate);
		return;
	}
}
