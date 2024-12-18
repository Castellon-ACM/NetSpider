package entities;
import entities.Port;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



@XmlRootElement(name = "Node")
@XmlType(propOrder = {"ip", "nodeName", "operativeSystem", "lapsed", "lastUpdate", "ports"})
public class Node {

    public enum OPERATIVE_SYSTEM {
        LINUX, WINDOWS
    }
    private String ip;
    private OPERATIVE_SYSTEM operativeSystem;
    private boolean isLapsed = false;
    private Date lastUpdate;
    private ArrayList<Port> ports = new ArrayList<>();


    public Node() {
    }

    /**
     * Constructor for creating a new node with a given IP address, node name, and operating system
     * @param ip
     * @param operativeSystem
     * @param lastUpdate
     */
    public Node(String ip, OPERATIVE_SYSTEM operativeSystem, ArrayList<Port> ports, Date lastUpdate) {
        this.ip = ip;
        this.operativeSystem = operativeSystem;
        this.lastUpdate = lastUpdate;
        this.ports = ports;
    }

    /**
     * Constructor for creating a new node with a given IP address
     * @param ip
     */
    public Node(String ip) {
        this.ip = ip;
        this.operativeSystem = OPERATIVE_SYSTEM.LINUX;
        updateLastProcessedTime();
        this.ports = new ArrayList<>();
        this.isLapsed = true; // If the node is newly created, it's considered lapsed

    }

    /**
     * Updates the last processed time
     */
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
