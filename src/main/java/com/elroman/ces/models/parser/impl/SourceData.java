package com.elroman.ces.models.parser.impl;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.parser.DataParser;
import com.elroman.ces.service.ParseService;
import org.apache.log4j.Logger;

import java.util.List;

public abstract class SourceData implements DataParser {
	final static Logger LOGGER = Logger.getLogger(ParseService.class);

	protected RateSource rateSource;
	protected List<Currency> currencyListForParse;
	protected Currency currencyTo;
	protected String rawData;

	public SourceData(RateSource rateSource, List<Currency> currencyListForParse, Currency currencyTo, String rawData) {
		this.rateSource = rateSource;
		this.currencyListForParse = currencyListForParse;
		this.currencyTo = currencyTo;
		this.rawData = rawData;
	}

	public RateSource getRateSource() {
		return rateSource;
	}

	public List<Currency> getCurrencyListForParse() {
		return currencyListForParse;
	}

	public Currency getCurrencyTo() {
		return currencyTo;
	}

	public String getRawData() {
		return rawData;
	}

	@Override
	public String toString() {
		return "SourceData{" +
				"rateSource=" + rateSource +
				", currencyListForParse=" + currencyListForParse +
				", currencyTo=" + currencyTo +
				", rawData='" + rawData + '\'' +
				'}';
	}
}
