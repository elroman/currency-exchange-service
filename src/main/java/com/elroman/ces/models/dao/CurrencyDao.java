package com.elroman.ces.models.dao;

import com.elroman.ces.models.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CurrencyDao {
	private static final Logger LOGGER = LogManager.getLogger(CurrencyDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Currency currency) {
		try {
			getSession().save(currency);
		} catch (Exception ex) {
			LOGGER.error("ERROR: " + ex + " CurrencyDao: could not save new currency: " + currency);
		}
		return;
	}

	public void delete(Currency currency) {
		getSession().delete(currency);
		return;
	}

	public void deleteAll() {
		getSession().createQuery("delete from Currency");
		return;
	}

	public List<Currency> getAll() {
		return getSession().createQuery("from Currency").list();
	}

	public Currency getByAlias(String alias) {
		return (Currency) getSession().createQuery(
				"from Currency where alias = :alias")
				.setParameter("alias", alias)
				.uniqueResult();
	}

	public Currency getById(int id) {
		return (Currency) getSession().get(Currency.class, id);
	}

	public void update(Currency currency) {
		getSession().update(currency);
		return;
	}
}
