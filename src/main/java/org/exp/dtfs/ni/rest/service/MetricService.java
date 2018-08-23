package org.exp.dtfs.ni.rest.service;

import java.net.InetAddress;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.entity.CommandBody;
import org.exp.dtfs.ni.entity.ResponseBody;
import org.exp.dtfs.ni.entity.ResponseBody.ResultStatus;
import org.exp.dtfs.ni.utils.DateUtils;
import org.exp.dtfs.ni.utils.HBaseUtils;
import org.exp.dtfs.ni.utils.HDFSUtils;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path(Constants.REST_SERVER_PATH)
public class MetricService {
    private static final Log LOG = LogFactory.getLog(MetricService.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseBody exec(CommandBody cmd) {
        String id = cmd.getID();
        String compKey = cmd.getComponentKey();
        String command = cmd.getCommand();
        String params = cmd.getParams();

        LOG.info("Received command, ID is [" + id + "], component key is [" + compKey + "], command is [" + command + "], parameters is [" + params + "].");

        String[] keys = compKey.split(Constants.TRANSFER_VERTICAL_DELIMITER);
        if (2 > keys.length) {
            return new ResponseBody(id, compKey, command, DateUtils.getNowTime(), "Illegal arguments number: [" + keys.length + "].",
                    ResultStatus.FAILED.value());
        }

        String result = null;
        String status = ResultStatus.SUCCESS.value();

        PCommand pcmd = PCommand.valueOf(command.toUpperCase());
        String ip = keys[0];

        try {
            // TODO Check component and subcomponent value.
            switch (pcmd) {
            case P_HB_NNPROC: // 基础运行类 - 数据管理节点服务进程是否存在
            case P_HB_CONSTATUS: // 基础运行类 - NameNode连接状态是否正常
                result = Boolean.toString(HDFSUtils.checkNameNodeAlive(ip));
                break;

            case P_HB_REGPROC: // 基础运行类 - 元数据节点服务进程是否存在
                result = Boolean.toString(HBaseUtils.checkRegionServerAlive(ip));
                break;

            case P_HB_DMPROC: // 基础运行类 - 数据节点服务进程是否存在
                result = Boolean.toString(HDFSUtils.checkDataNodeAlive(ip));
                break;

            case P_HB_CONNUM: // 基础运行类 - NameNode客户端连接数
                result = Integer.toString(HDFSUtils.getRPCClientConnNum());
                break;

            case P_HB_SAFEMOD: // 基础运行类 - NameNode是否启用安全模式
                result = Boolean.toString(HDFSUtils.isSafeMode());
                break;

            case P_HB_ROLE: // 基础运行类 - NameNode主备角色
                if ((!ip.equals(HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER)[0]))
                        && (!ip.equals(HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER)[0]))) {
                    result = "Host [" + ip + "] is not HDFS NameNode.";
                    status = ResultStatus.FAILED.value();
                } else {
                    result = InetAddress.getByName(ip).getCanonicalHostName().equals(HDFSUtils.getActiveNameNodeHostname()) ? "master" : "slave";
                }
                break;

            case P_HB_CLRFILES: // 基础运行类 - 集群总文件数（FilesTotal）
                result = Long.toString(HDFSUtils.getTotalFilesNum());
                break;

            case P_HB_CLRBKDAM: // 基础运行类 - 集群中已损坏block总个数
                result = Long.toString(HDFSUtils.getCorruptBlockNum());
                break;

            case P_HB_DNREAD: // 性能类 - 单个数据节点的平均读取时间
                result = Float.toString(HDFSUtils.getDataNodeAvgReadTime(ip));
                break;

            case P_HB_DNWRITE: // 性能类 - 单个数据节点的平均写入时间
                result = Float.toString(HDFSUtils.getDataNodeAvgWriteTime(ip));
                break;

            case P_HB_CLRMEM: // 基础运行类 - 集群占用内存总数
                result = Float.toString(HDFSUtils.getHeapMemoryUsageMB());
                break;

            case P_HB_DNVALID: // 基础运行类 - 存活数据节点的个数
                result = Integer.toString(HDFSUtils.getAliveDataNodeNum());
                break;

            case P_HB_DNEXPNUM: // 基础运行类 - 异常数据节点的个数
                result = Integer.toString(HDFSUtils.getAbnormalDataNodeNum());
                break;

            case P_HB_CLRDISK: // 基础运行类 - 集群磁盘空间占用(率)
                result = Float.toString(HDFSUtils.getCapacityUsageGB());
                break;

            case P_HB_CLRCPU: // 基础运行类 - 集群CPU占用率
                result = Float.toString(HDFSUtils.getCPUUsage());
                break;

            case P_HB_SFILEPERC: // 基础运行类 - 小文件(≤2MB)数占比
                // TODO Check component and subcomponent value.
                break;

            default:
                result = "Illegal command: [" + command + "].";
                status = ResultStatus.FAILED.value();
                break;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = e.getMessage();
            status = ResultStatus.FAILED.value();
        }

        LOG.info("Built response, result content is [" + result + "].");
        return new ResponseBody(id, compKey, command, DateUtils.getNowTime(), result, status);

    }

    private static enum PCommand {
        P_HB_NNPROC, // 基础运行类 - 数据管理节点服务进程是否存在
        P_HB_REGPROC, // 基础运行类 - 元数据节点服务进程是否存在
        P_HB_DMPROC, // 基础运行类 - 数据节点服务进程是否存在
        P_HB_CONSTATUS, // 基础运行类 - NameNode连接状态是否正常
        P_HB_CONNUM, // 基础运行类 - NameNode客户端连接数
        P_HB_SAFEMOD, // 基础运行类 - NameNode是否启用安全模式
        P_HB_ROLE, // 基础运行类 - NameNode主备角色
        P_HB_CLRFILES, // 基础运行类 - 集群总文件数（FilesTotal）
        P_HB_CLRBKDAM, // 基础运行类 - 集群中已损坏block总个数
        P_HB_DNREAD, // 性能类 - 单个数据节点的平均读取时间
        P_HB_DNWRITE, // 性能类 - 单个数据节点的平均写入时间
        P_HB_CLRMEM, // 基础运行类 - 集群占用内存总数
        P_HB_DNVALID, // 基础运行类 - 存活数据节点的个数
        P_HB_DNEXPNUM, // 基础运行类 - 异常数据节点的个数
        P_HB_CLRDISK, // 基础运行类 - 集群磁盘空间占用率
        P_HB_CLRCPU, // 基础运行类 - 集群CPU占用率
        P_HB_SFILEPERC; // 基础运行类 - 小文件(≤2MB)数占比
    }

}
