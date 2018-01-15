package com.elroman.ces.models.parser.impl;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FixerIoParser extends SourceData {

	public FixerIoParser(RateSource fromSource, List<Currency> currencyListForParse, Currency currencyTo, String rawData) {
		super(fromSource, currencyListForParse, currencyTo, rawData);
	}

	@Override
	public List<Rate> parseToRates() {

		List<Rate> rates = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode financeJson = mapper.readTree(rawData);
			JsonNode currenciesNode = financeJson.path("rates");

			for (final Currency currency : currencyListForParse) {

				String rateStr = currenciesNode.path(currency.getAlias()).asText();

				if (!rateStr.isEmpty()) {
					try {
						rates.add(new Rate(currency, currencyTo, new BigDecimal(rateStr), rateSource));
					} catch (NumberFormatException ex) {
						LOGGER.error("Error convert  rate to BigDecimal, value:" + rateStr);
					}
				}
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rates;
	}
}
