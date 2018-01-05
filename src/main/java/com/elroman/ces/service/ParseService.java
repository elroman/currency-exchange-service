package com.elroman.ces.service;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.CurrencyDao;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.models.parser.impl.FinanceParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class ParseService {

	@Autowired
	private RateSourceDao rateSourceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private RateDao rateDao;

	public void updateInfoFromSource() {
		List<RateSource> activeSources = rateSourceDao.getActiveSources();

		activeSources.stream().map(this::getRawInfoFromRateSource)
				.map(this::getParsedRatesFromRawInfo)
				.forEach(this::saveRates);

		System.out.println("ParseService : updateInfoFromSource!!!!!! ");
	}

	private String getRawInfoFromRateSource(RateSource rateSource) {
		URL file = getClass().getClassLoader().getResource("test_data_finance_ua_privat.json");

		ObjectMapper mapper = new ObjectMapper();
		try {

			JsonNode root = mapper.readTree(file);
			return root.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private List<Rate> getParsedRatesFromRawInfo(String rawInfo)  {
		List<Currency> currencyListForParse = currencyDao.getAll();
		Currency currencyTo = currencyDao.getByAlias("UAH");

		FinanceParser financeParser = new FinanceParser(rateSourceDao.getBySourceName("finance.ua"), currencyListForParse, currencyTo, rawInfo);
		return financeParser.parseToRates();
	}

	private void saveRates(List<Rate> rates)  {
		rates.forEach(rate -> rateDao.save(rate));
	}

}
