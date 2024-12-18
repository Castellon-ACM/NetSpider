package storage;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import config.ConfigurationSingleton;
import entities.Node;

// Clase almacén
// Dificultad medio
public class Storage  {
    private static final ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    private static CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList<>();


    /**
     * Method to get lapsed nodes
     *
     * @return lapsed nodes
     */
    public static ArrayList<Node> getLapsedNodes() {
        ArrayList<Node> lapsedNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isLapsed()) lapsedNodes.add(node);
        }
        return lapsedNodes;
    }

    /**
     * Method to set lapsed nodes
     */
    public static void setLapsedNodes() {
        for (Node node : nodes) {
            if (config.getExpirationTime() <= System.currentTimeMillis() - node.getLastUpdate().getTime()) {
                node.setLapsed(true);
            }
        }
    }

    public static void setNodes(CopyOnWriteArrayList<Node> nodes) {
        Storage.nodes = nodes;
    }

    /**
     * Add the array of nodes to the storage, if a node already exists overwrites it
     *
     * @param newNodes
     * @return
     */
    public static void addNodes(ArrayList<Node> newNodes) {
        for (Node newNode : newNodes) {
            addNode(newNode);
        }
    }
    /**
     * Add a node to the storage, if a node already exists updates it
     *
     * @param newNode
     */
    public static void addNode(Node newNode) {
        boolean existNode = false;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getIp().equals(newNode.getIp())) {
                nodes.set(i, newNode);  // Overwrite existing node
                existNode = true;
                i = nodes.size() + 1;
            }
        }
        if (!existNode) {
            nodes.add(newNode);
        }
    }

    public static CopyOnWriteArrayList<Node> getNodes() {
        return nodes;
    }
}
