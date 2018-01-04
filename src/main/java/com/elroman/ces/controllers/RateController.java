package com.elroman.ces.controllers;

import com.elroman.ces.models.Rate;
import com.elroman.ces.models.dao.RateDao;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/rate")
public class RateController {

	@Autowired
	private RateDao rateDao;

	@RequestMapping(method = DELETE)
	@ResponseBody
	public String delete(String id) {
		try {
			Rate rate = new Rate(id);
			rateDao.delete(rate);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "Rate successfully deleted!";
	}

	@RequestMapping(method = PUT)
	@ResponseBody
	public String addRate(@RequestBody Rate rate) {
		try {
			rateDao.save(rate);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "Rate successfully saved!";
	}

	@RequestMapping(method = GET)
	@ResponseBody
	public String getById(String id) {
		Rate rate;
		try {
			rate = rateDao.getById(id);
		} catch (Exception ex) {
			return "Rate not found";
		}
		return rate.toJson();
	}

	@RequestMapping(value = "/source")
	@ResponseBody
	public String lastBySource(String id) {
		Rate rate;
		try {
			rate = rateDao.getLastBySourceId(id);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return rate.toString();
	}
}
