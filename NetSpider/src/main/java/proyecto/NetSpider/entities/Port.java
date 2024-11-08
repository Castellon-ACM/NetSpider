package proyecto.NetSpider.entities;

public class Port {
    private String portNumber;
    private Service service;


    public Port(String portNumber) {
        this.portNumber = portNumber;
    }

    public Port(String portNumber, Service service) {
        this.portNumber = portNumber;
        this.service = service;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
