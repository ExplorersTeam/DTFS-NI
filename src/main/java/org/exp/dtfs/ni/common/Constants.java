package org.exp.dtfs.ni.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final String HTTP_SHCEME = "http";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String REST_SERVER_PATH = "paas/v1/cmd";

    /**
     * Delimiters.
     */
    public static final String SLASH_DELIMITER = "/";
    public static final String COLON_DELIMITER = ":";
    public static final String TRANSFER_VERTICAL_DELIMITER = "\\|";

    /**
     * Configuration default values.
     */
    public static final String DEFAULT_ADDR_STR = "127.0.0.1";
    public static final String DEFAULT_SERVICE_NAME = "dtfs";
    public static final String DEFAULT_FILE_PATH = "/tmp/";

    private Constants() {
        // Do nothing.
    }

}
