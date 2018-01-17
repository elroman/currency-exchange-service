package com.elroman.ces.controllers;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.dao.CurrencyDao;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyDao currencyDao;

    @RequestMapping(method = GET)
    @ResponseBody
    public String getAllCurrencies() {

        List<Currency> all = currencyDao.getAll();

        return new Gson().toJson(all);
    }

    @RequestMapping(value = "/new", method = POST)
    @ResponseBody
    public String saveNewCurrency(@RequestBody Currency currency) {
        currencyDao.save(currency);

        return "Currency has added!";
    }
}
