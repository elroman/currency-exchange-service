package com.elroman.ces.models.dao;

import com.elroman.ces.models.RateSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

	public void deleteAll() {
		getSession().createQuery("delete from RateSource");
		return;
	}

	public RateSource getById(String id) {
		return (RateSource) getSession().get(RateSource.class, id);
	}

	public RateSource getBySourceName(String sourceName) {
		return (RateSource) getSession().createQuery(
				"from RateSource where sourceName = :sourceName")
				.setParameter("sourceName", sourceName)
				.uniqueResult();
	}

	public List<RateSource> getActiveSources() {
		return (List<RateSource>) getSession().createQuery(
				"from RateSource where active = :active")
				.setParameter("active", true)
				.list();
	}

	public List<RateSource> getAll() {
		return getSession().createQuery("from RateSource").list();
	}

	public void update(RateSource rateSource) {
		getSession().update(rateSource);
		return;
	}
}
