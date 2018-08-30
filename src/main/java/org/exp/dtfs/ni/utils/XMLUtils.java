package org.exp.dtfs.ni.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * XML analysis.
 *
 * @author ZhangQingliang
 *
 */
// Demo xxx.xml:
// <configuration>
// <property>
// <name>kafka.props.compression.type</name>
// <value>gzip</value>
// </property>
// <property>
// <name>kafka.props.max.request.size</name>
// <value>5242880</value>
// </property>
// </configuration>
public class XMLUtils {
    private static final Log LOG = LogFactory.getLog(XMLUtils.class);

    private static String confFilename = "src/main/resources/dtfs-ni-kafka-site.xml";

    @SuppressWarnings("rawtypes")
    private static final Map<String, String> init() {
        Map<String, String> properties = new HashMap<>();
        String key = null;
        String value = null;
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File(confFilename));
            Element configuration = document.getRootElement();
            Iterator configurationIt = configuration.elementIterator();
            while (configurationIt.hasNext()) {
                Element property = (Element) configurationIt.next();
                Iterator propertyIt = property.elementIterator();
                while (propertyIt.hasNext()) {
                    Element child = (Element) propertyIt.next();
                    String nodeName = child.getName();
                    if (nodeName.equals("name")) {
                        key = child.getStringValue();
                    } else if (nodeName.equals("value")) {
                        value = child.getStringValue();
                    }
                    properties.put(key, value);
                }
            }
        } catch (DocumentException e) {
            LOG.error(e.toString());
        }
        return properties;
    }

    public static void setConf(String filePath) {
        confFilename = filePath;
    }

    public static String getTrimmed(String name, String defaultValue) {
        Map<String, String> properties = init();
        String result = properties.getOrDefault(name, defaultValue);
        return result.trim();
    }

    public static int getInt(String name, int defaultValue) {
        Map<String, String> properties = init();
        String result = properties.get(name);
        return result == null ? defaultValue : Integer.parseInt(result.trim());
    }

    public static void main(String[] args) {
        setConf("src/main/resources/dtfs-ni-kafka-site.xml");
        String strValue1 = getTrimmed("kafka.props.bootstrap.servers", "111111");
        String strValue2 = getTrimmed("kafka.props.bootstrap.servers12", "222222");
        int intValue1 = getInt("kafka.props.retries", 333333);
        int intValue2 = getInt("kafka.props.retries12", 444444);
        LOG.info(strValue1);
        LOG.info(strValue2);
        LOG.info(Integer.toString(intValue1));
        LOG.info(Integer.toString(intValue2));
    }
}
