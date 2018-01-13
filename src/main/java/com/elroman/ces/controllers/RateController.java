package com.elroman.ces.controllers;

import com.elroman.ces.models.Rate;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.service.ParseService;
import com.elroman.ces.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.InvalidParameterException;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/rate")
public class RateController {

    @Autowired
    private RateDao rateDao;

    @Autowired
    private ParseService parseService;

    @Autowired
    private RateService rateService;

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

    @RequestMapping(value = "/{currFrom}/{currTo}", method = GET)
    @ResponseBody
    public String getLatestRate(@PathVariable("currFrom") String currencyFromStr,
                                @PathVariable("currTo") String currencyToStr) {

        try {
            Rate rate = rateService.getLatestRateByCurrencyNames(currencyFromStr, currencyToStr);

            return rate.toJson();
        } catch (InvalidParameterException ex) {
            return "The exchange rate can not be found for these parameters currency from: " + currencyFromStr
                    + " currency to:" + currencyToStr;
        }
    }

    @RequestMapping(value = "/refresh", method = PUT)
    @ResponseBody
    public String refreshInfo() {

        parseService.updateInfoFromSource();

        return "Refresh rates info has done!";
    }

    @RequestMapping(method = DELETE)
    @ResponseBody
    public String delete(String id) {
        try {
            Rate rate = new Rate(id);
            rateDao.delete(rate);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Rate was successfully deleted!";
    }
}
