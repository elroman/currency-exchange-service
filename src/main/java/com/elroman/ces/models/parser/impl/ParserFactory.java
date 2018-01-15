package com.elroman.ces.models.parser.impl;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.RateSource;

import java.util.List;

public class ParserFactory {
	public static SourceData getParser(final RateSource fromSource, final List<Currency> currencyListForParse, final Currency currencyTo, final String rawData) {
		String sourceName = fromSource.getSourceName();

		switch (sourceName) {
			case "finance.ua":
				return new FinanceUaParser(fromSource, currencyListForParse, currencyTo, rawData);
			case "fixer.io":
				return new FixerIoParser(fromSource, currencyListForParse, currencyTo, rawData);
			default:
				return null;
		}
	}
}
