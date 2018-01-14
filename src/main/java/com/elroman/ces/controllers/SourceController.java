package com.elroman.ces.controllers;

import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.RateSourceDao;
import com.elroman.ces.service.RateSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/rate-sources")
public class SourceController {
    @Autowired
    private RateSourceDao rateSourceDao;

    @Autowired
    private RateSourceService rateSourceService;

    @RequestMapping(value = "/", method = GET)
    @ResponseBody
    public String getAllSources() {

        List<RateSource> all = rateSourceDao.getAll();

        return all.toString();
    }

    @RequestMapping(value = "/set-active", method = PUT)
    @ResponseBody
    public String setActive(String id, boolean active) {

        rateSourceService.setActive(id, active);

        return "Active has updated!";
    }
}
