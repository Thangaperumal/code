package com.cqecom.cms.services.pageSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerSessionsCounter {

    private static final Logger log = LoggerFactory.getLogger(SchedulerSessionsCounter.class);

    private static int sessions;

    static {
        log.error("### init");
        sessions = 0;
    }


    public static synchronized int inc() {
        return ++sessions;
    }

    public static synchronized int dec() {
        return --sessions;
    }

}
