package com.elroman.ces.service;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.models.parser.impl.FinanceUaParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
	final static Logger LOGGER = Logger.getLogger(WorkerService.class);

	@Autowired
	private RateSourceDao rateSourceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private RateDao rateDao;

	@Autowired
	private HttpService httpService;

	public void updateRatesFromActiveSources() {
		LOGGER.debug("ParseService: start update rate info.");
		List<RateSource> activeSources = rateSourceDao.getActiveSources();

		activeSources.stream().map(this::getRawInfoFromRateSource)
				.map(this::getParsedRatesFromRawInfo)
				.forEach(this::saveRates);

		LOGGER.debug("ParseService: finish update rate info.");
	}

	private String getRawInfoFromRateSource(RateSource rateSource) {
		return httpService.sendGetRequestWithResponse(rateSource.getSourceUrl());
	}

	private List<Rate> getParsedRatesFromRawInfo(String rawInfo) {
		List<Currency> currencyListForParse = currencyDao.getAll();
		Currency currencyTo = currencyDao.getByAlias("UAH");

		FinanceUaParser financeParser = new FinanceUaParser(rateSourceDao.getBySourceName("finance.ua"), currencyListForParse, currencyTo, rawInfo);
		return financeParser.parseToRates();
	}

	private void saveRates(List<Rate> rates) {
		rates.forEach(rate -> rateDao.save(rate));
	}
}
