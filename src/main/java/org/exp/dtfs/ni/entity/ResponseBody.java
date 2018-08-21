package org.exp.dtfs.ni.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseBody extends BaseBody {
    private static final String TIME_KEY = "CollectTime";
    private static final String RESULT_KEY = "Result";
    private static final String RESULT_STATUS_KEY = "ResultStatus";

    @XmlElement(name = TIME_KEY)
    private String time;

    @XmlElement(name = RESULT_KEY)
    private String result;

    @XmlElement(name = RESULT_STATUS_KEY)
    private String status;

    public ResponseBody() {
        // Do nothing.
    }

    public ResponseBody(String id, String componentKey, String command, String time, String result, String status) {
        super(id, componentKey, command);
        this.time = time;
        this.result = result;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public enum ResultStatus {
        SUCCESS("0"), FAILED("1");

        private String value;

        private ResultStatus(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

    }

}
