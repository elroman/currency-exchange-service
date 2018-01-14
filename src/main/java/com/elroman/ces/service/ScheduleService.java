package com.elroman.ces.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleService {
    private final static Logger LOGGER = Logger.getLogger(ScheduleService.class);
    private final static int MIN_TIMES_PER_DAY = 2;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledTask;

    @Autowired
    private WorkerService workerService;

    public void startSchedule(Integer timesPerDay) {
        if (scheduledTask == null || scheduledTask.isCancelled()) {
            timesPerDay = Optional.ofNullable(timesPerDay).orElse(MIN_TIMES_PER_DAY);
            timesPerDay = Integer.max(timesPerDay, MIN_TIMES_PER_DAY); // if timesPerDay in param less then min times

            int secondsInDay = (24 * 60 * 60);

            int period = secondsInDay / timesPerDay;
            LOGGER.debug("ScheduleService: start schedule " + timesPerDay + " times per day");

            Runnable task = () -> workerService.updateRatesFromActiveSources();
            scheduledTask = scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
        }
    }

    public void stopSchedule() {
        if (scheduledTask != null && (!scheduledTask.isCancelled())) {
            LOGGER.debug("ScheduleService: stop scheduler");
            scheduledTask.cancel(false);
        }
    }
}
