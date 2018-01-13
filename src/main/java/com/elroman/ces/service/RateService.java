package com.elroman.ces.service;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class RateService {
    final static Logger LOGGER = Logger.getLogger(RateService.class);

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private RateDao rateDao;

    public Rate getLatestRateByCurrencyNames(String currencyFromStr, String currencyToStr)
            throws InvalidParameterException {

        LOGGER.debug("RateService: start getting the latest rate.");

        Currency currencyFrom = currencyDao.getByAlias(currencyFromStr);
        Currency currencyTo = currencyDao.getByAlias(currencyToStr);

        Rate rate = Optional.ofNullable(rateDao.getLastByCurrencies(currencyFrom, currencyTo))
                .orElse(rateDao.getLastByCurrencies(currencyTo, currencyFrom));

        LOGGER.debug("ParseService: finish getting the latest rate.");

        return Optional.ofNullable(rate).orElseThrow(InvalidParameterException::new);
    }
}
