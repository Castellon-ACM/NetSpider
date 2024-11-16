package entities;

import java.util.ArrayList;
import java.util.Date;

enum OPERATIVE_SYSTEM {
    LINUX, WINDOWS
}

public class Node {
    private String ip;
    private String nodeName;
    private OPERATIVE_SYSTEM operativeSystem;
    private boolean isLapsed = false;
    private Date lastUpdate;
    
    ArrayList<Port> ports;



    public Node(String ip) {
        this.ip = ip;
    }


    public Node(String ip, ArrayList<Port> ports) {
        this.ip = ip;
        this.ports = ports;
    }

    public Node(String ip, String nodeName, OPERATIVE_SYSTEM operativeSystem) {
        this.ip = ip;
        this.nodeName = nodeName;
        this.operativeSystem = operativeSystem;
    }

    /**
     * Analyze if the node is processed correctly
     * @return true or false
     */
    public boolean isProcessed() {
        return !ports.isEmpty() && !nodeName.isEmpty() && operativeSystem != null;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<Port> getPorts() {
        return ports;
    }

    public void setPorts(ArrayList<Port> ports) {
        this.ports = ports;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public OPERATIVE_SYSTEM getOperativeSystem() {
        return operativeSystem;
    }

    public void setOperativeSystem(OPERATIVE_SYSTEM operativeSystem) {
        this.operativeSystem = operativeSystem;
    }

    public boolean isLapsed() {
        return isLapsed;
    }

    public void setLapsed(boolean lapsed) {
        isLapsed = lapsed;
    }
}
