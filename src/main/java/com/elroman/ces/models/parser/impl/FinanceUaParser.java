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

public class FinanceUaParser extends SourceData {

	public FinanceUaParser(RateSource fromSource, List<Currency> currencyListForParse, Currency currencyTo, String rawData) {
		super(fromSource, currencyListForParse, currencyTo, rawData);
	}

	@Override
	public List<Rate> parseToRates() {

		List<Rate> rates = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode financeJson = mapper.readTree(rawData);
			JsonNode organizationsNode = financeJson.path("organizations");

			if (organizationsNode.isArray()) {
				for (final JsonNode organizationNode : organizationsNode) {

					String organizationId = organizationNode.path("id").asText();

					// TODO now i check PRIVAT_BANK RATES only
					if(!organizationId.equals("7oiylpmiow8iy1sma7w")) continue;

					JsonNode currenciesNode = organizationNode.path("currencies");

					for (final Currency currency : currencyListForParse) {

						JsonNode currencyNode = currenciesNode.path(currency.getAlias());
						if (currencyNode.isObject()) {
							String rateStr = currencyNode.path("ask").asText();

							rates.add(new Rate(currency, currencyTo, new BigDecimal(rateStr), rateSource));
						}
					}
				}
			}
		} catch (IOException ex) {
			LOGGER.error("Problem with parsing raw data: "+ ex);
		}

		return rates;
	}
}
