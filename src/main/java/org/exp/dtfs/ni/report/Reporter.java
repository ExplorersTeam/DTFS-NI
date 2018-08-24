package org.exp.dtfs.ni.report;

public interface Reporter {
    void register(ReportEntity entity);

    void remove(ReportEntity entity);

    void start();

}
