package entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "version", "description"})
public class Service {
    private String name;
    private String version;
    private String description;

    public Service() {
    }

    public Service(String name, String version, String description) {
        this.name = name;
        this.version = version;
        this.description = description;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}