package storage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import data_controller.Migrable;
import entities.Node;

// Clase almacén
// Dificultad medio
public class Storage implements Migrable {
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

        public static ArrayList<Node> getLapsedNodes() {
        ArrayList<Node> lapsedNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isLapsed())  lapsedNodes.add(node);
        }
        return lapsedNodes;
    }

    public static void setNodes(CopyOnWriteArrayList<Node> nodes) {
        Storage.nodes = nodes;
    }

    /**
     * Add the nodes to the storage
     * @param nodes
     * @return
     */
    public static boolean addNodes(ArrayList<Node> nodes){
        return nodes.addAll(nodes);
    }
}
