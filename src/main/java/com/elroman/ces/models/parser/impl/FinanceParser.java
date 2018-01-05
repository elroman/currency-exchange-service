package com.elroman.ces.models.parser.impl;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinanceParser extends SourceData {

	public FinanceParser(RateSource fromSource, List<Currency> currencyListForParse, Currency currencyTo, String rawData) {
		super(fromSource, currencyListForParse, currencyTo, rawData);
	}

	@Override
	public List<Rate> parseToRates() {

		List<Rate> rates = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode financeJson = mapper.readTree(getRawData());
			JsonNode organizationsNode = financeJson.path("organizations");

			if (organizationsNode.isArray()) {
				for (final JsonNode organizationNode : organizationsNode) {

					JsonNode currenciesNode = organizationNode.path("currencies");

					for (final Currency currency : getCurrencyListForParse()) {

						JsonNode currencyNode = currenciesNode.path(currency.getAlias());
						if (currencyNode.isObject()) {
							String rateStr = currencyNode.path("ask").asText();

							rates.add(new Rate(currency, getCurrencyTo(), new BigDecimal(rateStr), getFromSource()));

						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rates;
	}
}
