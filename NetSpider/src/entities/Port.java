package entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"portNumber", "serviceDescription"})
public class Port {
    private String portNumber;
    private String serviceDescription;

    // Constructor sin argumentos requerido por JAXB
    public Port() {
    }

    public Port(String portNumber) {
        this.portNumber = portNumber;
    }

    public Port(String portNumber, String serviceDescription) {
        this.portNumber = portNumber;
        this.serviceDescription = serviceDescription;
    }

    @XmlElement
    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    @XmlElement
    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

}
