package com.elroman.ces.controllers;

import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.service.RateSourceService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/rate-sources")
public class SourceController {
    @Autowired
    private RateSourceDao rateSourceDao;

    @Autowired
    private RateSourceService rateSourceService;

    @RequestMapping(method = GET)
    @ResponseBody
    public String getAllSources() {

        List<RateSource> all = rateSourceDao.getAll();

        return new Gson().toJson(all);
    }

    @RequestMapping(value = "/set-active", method = PUT)
    @ResponseBody
    public String setActive(String id, boolean active) {

        rateSourceService.setActive(id, active);

        return "Active has updated!";
    }

    @RequestMapping(value = "/new", method = POST)
    @ResponseBody
    public String saveNewSource(@RequestBody RateSource rateSource) {
        rateSourceDao.save(rateSource);

        return "RateSource has added!";
    }
}
