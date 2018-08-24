package org.exp.dtfs.ni.rest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.RESTConfigs;
import org.exp.dtfs.ni.rest.service.MetricService;
import org.exp.dtfs.ni.utils.HTTPUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * RESTful service server.
 *
 * @author ChenJintong
 *
 */
public class Server {
    private static final Log LOG = LogFactory.getLog(Server.class);

    private String hostAddress = null;
    private int port = RESTConfigs.getServerPort();
    private HttpServer serv = null;

    public Server() {
        init();
    }

    public Server(int port) {
        this.port = port;
        init();
    }

    private void init() {
        try {
            this.hostAddress = InetAddress.getLocalHost().getHostAddress();
            ResourceConfig config = new ResourceConfig();
            config.packages(false, MetricService.class.getPackage().getName());
            config.register(MoxyJsonFeature.class);
            config.register(LoggingFeature.class).property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
            this.serv = GrizzlyHttpServerFactory.createHttpServer(HTTPUtils.buildURI(getHostAddress(), getPort()), config, false, null, false);
            LOG.info("REST server initialized, address is [" + getHostAddress() + Constants.COLON + getPort() + "].");
        } catch (UnknownHostException | URISyntaxException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public int getPort() {
        return port;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * Start server.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        if (null == serv) {
            throw new NullPointerException("No such server.");
        }
        if (serv.isStarted()) {
            throw new IllegalStateException("Server is still running.");
        }
        serv.start();
    }

    public static void main(String[] args) {
        Server server = 0 == args.length ? new Server() : new Server(Integer.parseInt(args[0]));
        try {
            server.start();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
