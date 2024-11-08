package proyecto.NetSpider.entities;

import java.util.ArrayList;
enum OPERATIVE_SYSTEM {
    LINUX, WINDOWS
}
public class Node {
    private String ip;
    private String nodeName;
    private OPERATIVE_SYSTEM operativeSystem;
    ArrayList<Port> ports;


    public Node(String ip) {
        this.ip = ip;
    }


    public Node(String ip, ArrayList<Port> ports) {
        this.ip = ip;
        this.ports = ports;
    }

    public Node (String ip, String nodeName, OPERATIVE_SYSTEM operativeSystem) {
        this.ip = ip;
        this.nodeName = nodeName;
        this.operativeSystem = operativeSystem;
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
}
