package com.elroman.ces.models.dao;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public class RateDao {
	final static Logger LOGGER = Logger.getLogger(RateDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Rate getLastByCurrencies(Currency currencyFrom, Currency currencyTo) {
		return (Rate) getSession().createQuery(
				"FROM Rate " +
						"WHERE currencyFrom = :currencyFrom " +
						"AND currencyTo = :currencyTo " +
						"ORDER BY rateTime DESC ")
				.setParameter("currencyFrom", currencyFrom)
				.setParameter("currencyTo", currencyTo)
				.setMaxResults(1)
				.uniqueResult();
	}

	public Rate getLastByDateTime(Currency currencyFrom, Currency currencyTo, Date dateTo) {
		return (Rate) getSession().createQuery(
				"FROM Rate " +
						"WHERE currencyFrom = :currencyFrom " +
						"AND currencyTo = :currencyTo " +
						"AND rateTime <= :dateTo " +
						"ORDER BY rateTime DESC ")
				.setParameter("currencyFrom", currencyFrom)
				.setParameter("currencyTo", currencyTo)
				.setParameter("dateTo", dateTo)
				.setMaxResults(1)
				.uniqueResult();
	}

	public void save(Rate rate) {
		getSession().save(rate);
		return;
	}

	public void delete(Rate rate) {
		getSession().delete(rate);
		return;
	}

	public void deleteAll() {
		getSession().createQuery("delete from Rate");
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
