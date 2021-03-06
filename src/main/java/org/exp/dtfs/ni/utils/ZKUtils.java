package org.exp.dtfs.ni.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.exp.dtfs.ni.conf.HBaseConfigs;
import org.exp.dtfs.ni.conf.ZKConfigs;

/**
 * 
 * Operations to ZooKeeper.
 *
 */
public class ZKUtils {
    private static final Log LOG = LogFactory.getLog(ZKUtils.class);

    private static CuratorFramework curator = null;

    static {
        Builder builder = CuratorFrameworkFactory.builder();
        builder.connectString(HBaseConfigs.getZKQuorum());
        builder.connectionTimeoutMs(ZKConfigs.getZKConnectionTimeoutMS());
        builder.sessionTimeoutMs(HBaseConfigs.getZKSessionTimeoutMS());// session超时时间
                                                                       // ms
        builder.retryPolicy(new ExponentialBackoffRetry(ZKConfigs.getZKExponentialBackoffRetryBaseSleepTimeMS(), ZKConfigs.getZKMaxRetries(),
                ZKConfigs.getZKExponentialBackoffRetryMaxSleepTimeMS()));// 重试策略
        curator = builder.build();// 通过工厂创建连接
        curator.start();
        LOG.info("Curator initialized, state is [" + curator.getState().name() + "].");
    }

    private ZKUtils() {
        // Do nothing.
    }

    /**
     * Create ZNode with path.
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static String create(String path) throws Exception {
        LOG.info("Create ZNode, path is [" + path + "].");
        return curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
    }

    /**
     * Create ZNode with path and data.
     * 创建节点，creatingParentsIfNeeded()方法的意思是如果父节点不存在，则在创建节点的同时创建父节点；
     * withMode()方法指定创建的节点类型，跟原生的Zookeeper API一样，不设置默认为PERSISTENT类型。
     * 
     * @param path
     * @param data
     *            内容信息
     * @return
     * @throws Exception
     */
    public static String create(String path, byte[] data) throws Exception {
        LOG.info("Create ZNode, path is [" + path + "], and data is [" + new String(data) + "].");
        return curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data);
    }

    /**
     * Get ZNode data.
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] get(String path) throws Exception {
        LOG.info("Get ZNode data, path is [" + path + "].");
        return curator.getData().forPath(path);
    }

    /**
     * Set ZNode data with default value.
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static Stat set(String path) throws Exception {
        LOG.info("Set ZNode data, path is [" + path + "].");
        return curator.setData().forPath(path);
    }

    /**
     * Set ZNode data with specific value.
     * 
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    public static Stat set(String path, byte[] data) throws Exception {
        LOG.info("Set ZNode data, path is [" + path + "], and value is [" + new String(data) + "].");
        return curator.setData().forPath(path, data);
    }

    /**
     * List ZNode children.
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> list(String path) throws Exception {
        LOG.info("List ZNode children, path is [" + path + "].");
        return curator.getChildren().forPath(path);
    }

    /**
     * Delete ZNode.
     * 
     * @param path
     * @throws Exception
     */
    public static void delete(String path) throws Exception {
        LOG.info("Delete ZNode, path is [" + path + "].");
        curator.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
    }

}
