package org.exp.dtfs.ni.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseBody {
    private static final String ID_KEY = "ID";
    private static final String COMP_KEY = "CompKey";
    private static final String COMMAND_KEY = "Command";

    @XmlElement(name = ID_KEY)
    private String id;

    @XmlElement(name = COMP_KEY)
    private String componentKey;

    @XmlElement(name = COMMAND_KEY)
    private String command;

    public BaseBody() {
        // Do nothing.
    }

    public BaseBody(String id, String componentKey, String command) {
        this.id = id;
        this.componentKey = componentKey;
        this.command = command;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getComponentKey() {
        return componentKey;
    }

    public void setComponentKey(String componentKey) {
        this.componentKey = componentKey;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
