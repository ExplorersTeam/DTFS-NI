package org.exp.dtfs.ni.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ambari.server.api.resources.ResourceInstance;
import org.apache.ambari.server.api.resources.ResourceInstanceFactory;
import org.apache.ambari.server.api.resources.ResourceInstanceFactoryImpl;
import org.apache.ambari.server.controller.spi.Resource.Type;

/**
 * 
 * Operations to Ambari Server.
 * 
 * @author ChenJintong
 * @remark For test.
 *
 */
public class AmbariUtils {
    private static final ResourceInstanceFactory INSTANCE_FACTORY = new ResourceInstanceFactoryImpl();

    private AmbariUtils() {
        // Do nothing.
    }

    public static String getClusterMetrics(String name) {
        Map<Type, String> params = new HashMap<>();
        params.put(Type.Cluster, name);
        ResourceInstance instance = INSTANCE_FACTORY.createResource(Type.Cluster, params);
        Set<String> properties = instance.getQuery().getProperties();
        return properties.toString();
    }

}
