package org.exp.dtfs.ni.report;

import java.util.concurrent.TimeUnit;

public class ReportEntity {
    private Runnable thread;
    private long period;
    private TimeUnit timeUnit;

    public ReportEntity(Runnable thread, int period, TimeUnit timeUnit) {
        this.thread = thread;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    public Runnable getThread() {
        return thread;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

}
