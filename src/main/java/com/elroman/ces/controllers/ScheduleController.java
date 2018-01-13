package com.elroman.ces.controllers;

import com.elroman.ces.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/start", method = GET)
    @ResponseBody
    public String startSchedule(Integer timesPerDay) {

        scheduleService.startSchedule(timesPerDay);

        return "Scheduler has been started!";
    }

    @RequestMapping("/stop")
    @ResponseBody
    public String stopSchedule() {

        scheduleService.stopSchedule();

        return "Scheduler has been stop!";
    }
}
