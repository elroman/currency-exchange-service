package com.elroman.ces.controllers;

import com.elroman.ces.models.Rate;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.service.WorkerService;
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

    private RateDao rateDao;

    private WorkerService workerService;

    private RateService rateService;

    @Autowired
    public RateController(RateDao rateDao, WorkerService workerService, RateService rateService) {
        this.rateDao = rateDao;
        this.workerService = workerService;
        this.rateService = rateService;
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

    @RequestMapping(value = "/{currFrom}/{currTo}", method = GET)
    @ResponseBody
    public String getLatestRate(@PathVariable("currFrom") String currencyFromStr,
                                @PathVariable("currTo") String currencyToStr) {

        try {
            Rate rate = rateService.getLatestRateByCurrencyNames(currencyFromStr, currencyToStr);

            return rate.toJson();
        } catch (InvalidParameterException ex) {
            return "The exchange rate can not be found for these parameters: currency from = " + currencyFromStr
                    + ", currency to =" + currencyToStr;
        }
    }

    @RequestMapping(value = "/{currFrom}/{currTo}/{dateTo}", method = GET)
    @ResponseBody
    public String getLatestRateByDate(@PathVariable("currFrom") String currencyFromStr,
                                      @PathVariable("currTo") String currencyToStr,
                                      @PathVariable("dateTo") String dateToStr) {

        try {
            Rate rate = rateService.getLatestRateByDate(currencyFromStr, currencyToStr, dateToStr);

            return rate.toJson();
        } catch (InvalidParameterException ex) {
            return "The exchange rate can not be found for these parameters: currency from = " + currencyFromStr
                    + ", currency to =" + currencyToStr
                    + ", date to =" + dateToStr;
        }
    }

    @RequestMapping(value = "/{currFrom}/{currTo}/{dateTo}/{timeTo}", method = GET)
    @ResponseBody
    public String getLatestRateByTime(@PathVariable("currFrom") String currencyFromStr,
                                      @PathVariable("currTo") String currencyToStr,
                                      @PathVariable("dateTo") String dateToStr,
                                      @PathVariable("timeTo") String timeToStr) {

        try {
            Rate rate = rateService.getLatestRateByTime(currencyFromStr, currencyToStr, dateToStr, timeToStr);

            return rate.toJson();
        } catch (InvalidParameterException ex) {
            return "The exchange rate can not be found for these parameters: currency from = " + currencyFromStr
                    + ", currency to =" + currencyToStr
                    + ", date to =" + dateToStr;
        }
    }

    @RequestMapping(value = "/refresh", method = GET)
    @ResponseBody
    public String refreshInfo() {

        workerService.updateRatesFromActiveSources();

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
