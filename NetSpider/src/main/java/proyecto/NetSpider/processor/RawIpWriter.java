package proyecto.NetSpider.processor;

import proyecto.NetSpider.entities.Node;

import java.util.ArrayList;

public class RawIpWriter {
    private ArrayList<String> rawIps = new ArrayList<>();

    /**
     * Write a new file of node
     * @param nodes
     */
    public RawIpWriter(ArrayList<Node> nodes) {
        if (!nodes.isEmpty()) {
            for (Node node : nodes) {
                rawIps.add(node.getIp());
            }
        }

    }

}
