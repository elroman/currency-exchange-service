package com.elroman.ces.service;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.models.parser.impl.ParserFactory;
import com.elroman.ces.models.parser.impl.SourceData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

		activeSources.stream().map(this::getParsedRatesForRateSource)
				.forEach(this::saveRates);

		LOGGER.debug("ParseService: finish update rate info.");
	}

	private List<Rate> getParsedRatesForRateSource(RateSource rateSource) {
		String rawInfo = httpService.sendGetRequestWithResponse(rateSource.getSourceUrl());

		return getParsedRatesFromRawInfo(rateSource, rawInfo);
	}

	private List<Rate> getParsedRatesFromRawInfo(RateSource rateSource, String rawInfo) {
		List<Rate> rates= new ArrayList<>();

		List<Currency> currencyListForParse = currencyDao.getAll();
		SourceData parser = ParserFactory.getParser(rateSource, currencyListForParse, rateSource.getDefaultCurrencyTo(), rawInfo);

		if (parser!=null) {
			rates = parser.parseToRates();
		}

		return rates;
	}

	private void saveRates(List<Rate> rates) {
		rates.forEach(rate -> rateDao.save(rate));
	}
}
