package org.exp.dtfs.ni.entity;

import org.exp.dtfs.ni.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetricMessage {
    @JsonProperty("ID")
    private String id = "40000"; // 唯一序列号
    @JsonProperty("CompKey")
    private String compKey; // 组件实例名称
    @JsonProperty("HOSTIP")
    private String hostIP; // 组件运行主机IP
    @JsonProperty("METRICCODE")
    private String metricCode; // 上报指标编码
    @JsonProperty("METRICTYPE")
    private String metricType; // 上报指标类型
    @JsonProperty("METRICVALUE")
    private String metricValue; // 具体指标值
    @JsonProperty("COLLECTTIME")
    private String collectTime = DateUtils.getNowTime(); // 具体指标值的采集时间

    @JsonProperty("SEQID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String seqID; // 时序ID，标识组件采集顺序，可选

    public String getId() {
        return id;
    }

    // public void setId(String id) {
    // this.id = id;
    // }

    public String getCompKey() {
        return compKey;
    }

    public void setCompKey(String compKey) {
        this.compKey = compKey;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getMetricCode() {
        return metricCode;
    }

    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType.id();
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public String getCollectTime() {
        return collectTime;
    }

    // public void setCollectTime(String collectTime) {
    // this.collectTime = collectTime;
    // }

    public String getSeqID() {
        return seqID;
    }

    public void setSeqID(String seqID) {
        this.seqID = seqID;
    }

}
