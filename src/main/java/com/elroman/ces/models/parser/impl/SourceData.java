package com.elroman.ces.models.parser.impl;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.parser.DataParser;

import java.util.List;

public abstract class SourceData implements DataParser {

	private RateSource fromSource;
	private List<Currency> currencyListForParse;
	private Currency currencyTo;
	private String rawData;

	public SourceData(RateSource fromSource, List<Currency> currencyListForParse, Currency currencyTo, String rawData) {
		this.fromSource = fromSource;
		this.currencyListForParse = currencyListForParse;
		this.currencyTo = currencyTo;
		this.rawData = rawData;
	}

	public RateSource getFromSource() {
		return fromSource;
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
				"fromSource=" + fromSource +
				", currencyListForParse=" + currencyListForParse +
				", currencyTo=" + currencyTo +
				", rawData='" + rawData + '\'' +
				'}';
	}
}
