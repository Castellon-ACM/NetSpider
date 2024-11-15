package entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"portNumber", "service"})
public class Port {
    private String portNumber;
    private Service service;

    // Constructor sin argumentos requerido por JAXB
    public Port() {
    }

    public Port(String portNumber) {
        this.portNumber = portNumber;
    }

    public Port(String portNumber, Service service) {
        this.portNumber = portNumber;
        this.service = service;
    }

    @XmlElement
    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    @XmlElement
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
