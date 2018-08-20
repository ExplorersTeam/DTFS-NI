package org.exp.dtfs.ni.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final String HTTP_SHCEME = "http";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Delimiters.
     */
    public static final String SLASH_DELIMITER = "/";
    public static final String COLON_DELIMITER = ":";

    private Constants() {
        // Do nothing.
    }

}
