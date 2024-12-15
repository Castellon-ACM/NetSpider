package entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
@XmlRootElement(name = "Nodes")
public class NodeList {
    private ArrayList<Node> nodes = new ArrayList<>();

    @XmlElement(name = "Node")
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
}
