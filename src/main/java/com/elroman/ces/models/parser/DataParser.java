package com.elroman.ces.models.parser;

import com.elroman.ces.models.Rate;

import java.util.List;


public interface DataParser {
	public List<Rate> parseToRates();
}
