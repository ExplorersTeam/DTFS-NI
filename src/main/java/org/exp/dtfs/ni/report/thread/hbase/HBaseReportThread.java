package org.exp.dtfs.ni.report.thread.hbase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.report.thread.ReportThread;

public abstract class HBaseReportThread extends ReportThread {
    protected static final Log LOG = LogFactory.getLog(HBaseReportThread.class);

    protected static final String HBASE_SERVER_KEY = "hbase_server_";
    protected static final String HBASE_STATUS_CODE = "HBASE_STATUS";

}
