package org.exp.dtfs.ni.report.thread;

public abstract class ReportThread implements Runnable {
    public abstract void work();

    @Override
    public void run() {
        work();
    }

}
