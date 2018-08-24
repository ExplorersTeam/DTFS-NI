package org.exp.dtfs.ni.report.thread.dtfs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.report.thread.ReportThread;

public abstract class DTFSReportThread extends ReportThread {
    protected static final Log LOG = LogFactory.getLog(DTFSReportThread.class);

    protected static final String DTFS_SERVER_KEY = "ctdfs_server_";
    protected static final String DTFS_STATUS_CODE = "CTDFS_STATUS";

}
