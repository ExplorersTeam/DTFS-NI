package org.exp.dtfs.ni.entity;

import org.exp.dtfs.ni.conf.CommonConfigs;
import org.exp.dtfs.ni.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogMessage {
    @JsonProperty("SEQID")
    private String seqID = CommonConfigs.getSeqID();// 一次传输流水序列号。全局唯一

    @JsonProperty("sendtime")
    private String sendTime = DateUtils.getNowTime(); // 日志上传时间。长整型 时间戳 精确到秒

    @JsonProperty("duetime")
    private long dueTime; // 长整型 处理时长 秒

    @JsonProperty("CompKey")
    private String compKey; // 组件对象唯一标识

    @JsonProperty("sendip")
    private String hostIP; // 源IP当前机器的IP

    @JsonProperty("srcpath")
    private String srcPath; // 源路径

    @JsonProperty("log")
    private String log;// 日志内容

    @JsonProperty("remark")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;// 备注信息

    @JsonProperty("status")
    private String status;// 发送成功标识

    public String getSeqID() {
        return seqID;
    }

    public void setSeqID(String seqID) {
        this.seqID = seqID;
    }

    public String getSendTime() {
        return sendTime;
    }

    // public void setSendTime(String sendTime) {
    // this.sendTime = sendTime;
    // }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

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

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
