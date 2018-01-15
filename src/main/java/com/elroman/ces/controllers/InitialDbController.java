package com.elroman.ces.controllers;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.models.dao.RateSourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/initial-db")
public class InitialDbController {

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private RateSourceDao rateSourceDao;

    @Autowired
    private RateDao rateDao;

    @RequestMapping(value = "/fill-tables", method = PUT)
    @ResponseBody
    public String fillInfoToTables() {
        //
        // ONLY FOR FILLING TEST DB
        //

        clearTables();

        Currency currUAH = new Currency(980, "UAH", "Ukraine Hryvnia");
        Currency currEUR = new Currency(978, "EUR", "Euro");

        currencyDao.save(currUAH);
        currencyDao.save(currEUR);
        currencyDao.save(new Currency(840, "USD", "United States Dollar"));
        currencyDao.save(new Currency(643, "RUB", "Russian Rouble"));

        RateSource rs = new RateSource("finance.ua", "http://resources.finance.ua/ru/public/currency-cash.json", currUAH, true);

        rateSourceDao.save(rs);
        rateSourceDao.save(new RateSource("fixer.io", "https://api.fixer.io/latest", currEUR, false));
        rateSourceDao.save(new RateSource("NBU", "nbu.com.ua/source", currUAH, false));

        rateDao.save(new Rate(currencyDao.getById(840), currencyDao.getById(980), new BigDecimal("28.10"), rateSourceDao.getById(rs.getId())));

        return "initialisation  DB has done!";
    }

    @RequestMapping(value = "/clear-db", method = DELETE)
    @ResponseBody
    public String clearTables() {

        rateSourceDao.deleteAll();
        currencyDao.deleteAll();
        rateDao.deleteAll();

        return "clear DB has done!";
    }

}
