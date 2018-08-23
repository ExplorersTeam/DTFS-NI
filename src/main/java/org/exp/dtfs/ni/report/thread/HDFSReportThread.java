package org.exp.dtfs.ni.report.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HDFSReportThread extends ReportThread {
    protected static final Log LOG = LogFactory.getLog(HDFSReportThread.class);

    protected static final String HDFS_SERVER_KEY = "hdfs_server_";
    protected static final String HDFS_STATUS_CODE = "HDFS_STATUS";

}
