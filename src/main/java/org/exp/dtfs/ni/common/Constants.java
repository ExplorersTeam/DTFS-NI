package org.exp.dtfs.ni.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final String HTTP_SHCEME = "http";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String REST_SERVER_PATH = "paas/v1/cmd";

    public static final String TINY_FILE_KEY = "tiny";
    public static final String TOTAL_FILE_KEY = "total";

    public static final String SLASH_DELIMITER = "/";
    public static final String COLON = ":";
    public static final String TRANSFER_VERTICAL_DELIMITER = "\\|";
    public static final String PERCENT = "%";
    public static final String MEMORY_UNIT = "MB";

    /**
     * Configuration default values.
     */
    public static final String DEFAULT_ADDR_STR = "127.0.0.1";
    public static final String DEFAULT_SERVICE_NAME = "DTFS";
    public static final String DEFAULT_FILE_PATH = "/tmp/";

    private Constants() {
        // Do nothing.
    }

}
