package storage;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import config.ConfigurationSingleton;
import data_controller.Migrable;
import entities.Node;

// Clase almacén
// Dificultad medio
public class Storage implements Migrable {
    private static final ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    private static CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList<>();

    /**
     * Method to import nodes
     *
     * @param nodes net nodes
     */
    @Override
    public void saveNodes(ArrayList<Node> nodes) {
        addNodes(nodes);
    }

    /**
     * Method to export nodes.
     * It returns the nodes in the CopyOnWriteArrayList
     *
     * @return nodes
     */
    @Override
    public ArrayList<Node> exportNodes() {
        return new ArrayList<>(nodes);
    }

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
     * Add the nodes to the storage, if a node already exists overwrites it
     *
     * @param newNodes
     * @return
     */
    public static void addNodes(ArrayList<Node> newNodes) {
        for (Node newNode : newNodes) {
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
    }
}
