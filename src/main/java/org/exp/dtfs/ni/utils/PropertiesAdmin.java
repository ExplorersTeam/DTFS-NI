package org.exp.dtfs.ni.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Operations for properties file type.
 * 
 * @author ChenJintong
 *
 */
public class PropertiesAdmin {
    private static final Log LOG = LogFactory.getLog(PropertiesAdmin.class);

    private Properties properties = new Properties();

    public PropertiesAdmin(String path) {
        try (InputStream in = new FileInputStream(path)) {
            properties.load(in);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

}
