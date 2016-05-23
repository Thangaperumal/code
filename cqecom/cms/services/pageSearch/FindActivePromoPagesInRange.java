package com.cqecom.cms.services.pageSearch;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Searches for promo pages which are active in a given dates range
 */
public class FindActivePromoPagesInRange extends FindActivePromoPages {

    private final Logger log = LoggerFactory.getLogger(FindActivePromoPagesInRange.class);

    private Date rangeStart;

    private Date rangeEnd;

    public FindActivePromoPagesInRange(String sitewidePromoPagesPath, Integer timezoneOffset,
                                       Date rangeStart, Date rangeEnd) {
        super(sitewidePromoPagesPath, timezoneOffset, false);
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        log.debug("date range: {} to {}", rangeStart ,rangeEnd);
    }

    @Override
    protected String getQuery() {
        String startString = DateFormatUtils.format(rangeStart, DATE_FORMAT);
        String endString = DateFormatUtils.format(rangeEnd, DATE_FORMAT);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("/jcr:root").append(getSitewidePromoPagesPath())
                .append("//element(*, cq:Page)")
                .append("[jcr:content/@startdate <= xs:dateTime('").append(endString).append("') and ")
                .append("jcr:content/@enddate >= xs:dateTime('").append(startString).append("')]");
        return queryBuilder.toString();
    }
}
