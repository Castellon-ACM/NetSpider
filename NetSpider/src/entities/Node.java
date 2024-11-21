package entities;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

enum OPERATIVE_SYSTEM {
    LINUX, WINDOWS
}

@XmlRootElement(name = "Node")
@XmlType(propOrder = {"ip", "nodeName", "operativeSystem", "isLapsed", "lastUpdate", "ports"})
public class Node {
    private String ip;
    private String nodeName;
    private OPERATIVE_SYSTEM operativeSystem;
    private boolean isLapsed = false;
    private Date lastUpdate;
    private ArrayList<Port> ports = new ArrayList<>();


    public Node() {

    }

    public Node(String ip, String nodeName, OPERATIVE_SYSTEM operativeSystem, Date lastUpdate) {
        this.ip = ip;
        this.nodeName = nodeName;
        this.operativeSystem = operativeSystem;
        this.lastUpdate = lastUpdate;



    }

    

    public void updateLastProcessedTime() {
        this.lastUpdate = Calendar.getInstance().getTime();
    }

    @XmlElement
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @XmlElement
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @XmlElement(name = "OperativeSystem")
    public OPERATIVE_SYSTEM getOperativeSystem() {
        return operativeSystem;
    }

    public void setOperativeSystem(OPERATIVE_SYSTEM operativeSystem) {
        this.operativeSystem = operativeSystem;
    }

    @XmlElement
    public boolean isLapsed() {
        return isLapsed;
    }

    public void setLapsed(boolean lapsed) {
        isLapsed = lapsed;
    }

    @XmlElement
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @XmlElementWrapper(name = "Ports")
    @XmlElement(name = "Port")
    public ArrayList<Port> getPorts() {
        return ports;
    }

    public void setPorts(ArrayList<Port> ports) {
        this.ports = ports;
    }

    public void addPort(Port port) {
        this.ports.add(port);
    }
}