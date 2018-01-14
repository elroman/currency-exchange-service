package com.elroman.ces.service;

import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.RateSourceDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateSourceService {
    final static Logger LOGGER = Logger.getLogger(RateSourceService.class);

    @Autowired
    private RateSourceDao rateSourceDao;

    public void setActive(String id, boolean active) {
        RateSource rateSource = rateSourceDao.getById(id);
        rateSource.setActive(active);
        rateSourceDao.update(rateSource);
    }
}
