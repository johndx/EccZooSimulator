package com.test.zoo.event;

import com.test.zoo.service.ZooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

/**
 * Class that models the simulation of the Life
 * of the zoo. It is invoked periodically on a
 * Scheduled Timer and delegates to the
 * Service tier to perform any work.
 */
@Component
public class ElevatedTimeSimulator {

    private final Logger log = LoggerFactory.getLogger(ElevatedTimeSimulator.class);

    private static final long TIME_ADVANCE_HOURS = 1l;

    /* ZonedDateTime is not required, as we only need the start and
       elapsed time for the operation.
     */
    private LocalTime startTime;
    private LocalTime simElapsedTime;
    private LocalTime currentTime;
    private long ticks;


    private boolean firstInvocation = true;

    private ZooService zooService;


    /**
     * Constructor that auto wires in the
     * Spring dependencies.
     * @param zooService
     */
    public ElevatedTimeSimulator(ZooService zooService) {
        this.zooService = zooService;
    }



    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getSimElapsedTime() {
        return simElapsedTime;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public long getTicks() {
        return ticks;
    }

    /**
     * simulate the running of the zoo in elevated time.
     * fixedRate is in milliseconds and is the time
     * between invocations of the event.
     * Schedule this method for execution every 20 seconds.
     */
    @Scheduled(fixedRate = 20000)
    public void simulate() {
        if (firstInvocation){
            init();
        }
        /* Call the Service method to Age the Animals */
        zooService.ageAnimals();

        /* Maintain the Timing Data */
        ticks++;
        simElapsedTime = simElapsedTime.plusHours(TIME_ADVANCE_HOURS);
        currentTime = LocalTime.now();

        log.warn("\n Ageing event invoked for the " + ticks + " time, and elapsed simulated time is :" + simElapsedTime);
    }

    private void init() {
        LocalTime now = LocalTime.now();
        startTime = now;
        simElapsedTime = now;
        currentTime = now;
        ticks = 0L;

        firstInvocation = false;
    }

}
