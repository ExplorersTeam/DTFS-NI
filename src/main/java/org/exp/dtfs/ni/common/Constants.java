package org.exp.dtfs.ni.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class Constants {
    public static final String HTTP_SHCEME = "http";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String REST_SERVER_PATH = "paas/v1/cmd";

    public static final String TINY_FILE_KEY = "tiny";
    public static final String TOTAL_FILE_KEY = "total";

    public static final String SLASH_DELIMITER = "/";
    public static final String COLON = ":";
    public static final String TRANSFER_VERTICAL_DELIMITER = "\\|";
    public static final String VERTICAL_DELIMITER = "|";
    public static final String PERCENT = "%";
    public static final String COMMA_DELIMITER = ",";
    public static final String UNDERLINE_DELIMITER = "_";

    /**
     * Configuration default values.
     */
    public static final String DEFAULT_SERVICE_NAME = "DTFS";

    private Constants() {
        // Do nothing.
    }

}
