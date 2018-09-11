package org.exp.dtfs.ni.entity;

public enum MetricCode {
    P_HB_NNPROC("30202623280001"), // 基础运行类 - 数据管理节点服务进程是否存在
    P_HB_REGPROC("30202623280002"), // 基础运行类 - 元数据节点服务进程是否存在
    P_HB_DMPROC("30202623280003"), // 基础运行类 - 数据节点服务进程是否存在
    P_HB_CONSTATUS("30202623280004"), // 基础运行类 - NameNode连接状态是否正常
    P_HB_CONNUM("30202623280005"), // 基础运行类 - NameNode客户端连接数
    P_HB_SAFEMOD("30202623280006"), // 基础运行类 - NameNode是否启用安全模式
    P_HB_ROLE("30202623280007"), // 基础运行类 - NameNode主备角色
    P_HB_CLRFILES("30202623280008"), // 基础运行类 - 集群总文件数（FilesTotal）
    P_HB_CLRBKDAM("30202623280009"), // 基础运行类 - 集群中已损坏block总个数
    P_HB_DNREAD("30202624280001"), // 性能类 - 单个数据节点的平均读取时间
    P_HB_DNWRITE("30202624280002"), // 性能类 - 单个数据节点的平均写入时间
    P_HB_CLRMEM("30202623280010"), // 基础运行类 - 集群占用内存总数
    P_HB_DNVALID("30202623280011"), // 基础运行类 - 存活元数据节点的个数
    P_HB_DNEXPNUM("30202623280012"), // 基础运行类 - 异常元数据节点的个数
    P_HB_CLRDISK("30202623280013"), // 基础运行类 - 集群磁盘空间占用率
    P_HB_CLRCPU("30202623280014"), // 基础运行类 - 集群CPU占用率
    P_HB_SFILEPERC("30202623280015"); // 基础运行类 - 小文件(≤2MB)数占比

    private String id;

    private MetricCode(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
