package org.exp.dtfs.ni.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.NameValuePair;

import com.alibaba.fastjson.JSONObject;

@XmlRootElement
public class CommandBody extends BaseBody {
    private static final String PARAMS_KEY = "Params";

    @XmlElement(name = PARAMS_KEY)
    private String params = "";

    public CommandBody() {
        // Do nothing.
    }

    public CommandBody(String id, String componentKey, String command) {
        super(id, componentKey, command);
    }

    public CommandBody(String id, String componentKey, String command, NameValuePair... kvs) {
        super(id, componentKey, command);
        JSONObject value = new JSONObject();
        for (NameValuePair kv : kvs) {
            value.put(kv.getName(), kv.getValue());
        }
        this.params = value.toJSONString();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
