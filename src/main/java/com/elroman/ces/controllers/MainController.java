package com.elroman.ces.controllers;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MainController {

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private RateSourceDao rateSourceDao;

	@Autowired
	private RateDao rateDao;

	@Autowired
	private ParseService parseService;

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return " MainController ";
	}

	@RequestMapping(value = "/initial-db", method = GET)
	@ResponseBody
	public String initialDB() {

		RateSource rs = new RateSource("finance.ua", "http://resources.finance.ua/ru/public/currency-cash.json", true);

		rateSourceDao.save(rs);
		rateSourceDao.save(new RateSource("fixer.io", "fixer.io/source", false));
		rateSourceDao.save(new RateSource("NBU", "nbu.com.ua/source", false));

		currencyDao.save(new Currency(980, "UAH", "Ukraine Hryvnia"));
		currencyDao.save(new Currency(840, "USD", "United States Dollar"));
		currencyDao.save(new Currency(978, "EUR", "Euro"));
		currencyDao.save(new Currency(643, "RUB", "Russian Rouble"));

		rateDao.save(new Rate(currencyDao.getById(840), currencyDao.getById(980), new BigDecimal("28.10"), rateSourceDao.getById(rs.getId())));

		return "initial DB done!";
	}

	@RequestMapping(value = "/refresh", method = GET)
	@ResponseBody
	public String refreshInfo() {

		parseService.updateInfoFromSource();

		return "refreshInfo done!";
	}

}
