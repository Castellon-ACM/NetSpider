package proyecto.NetSpider.data_controller;

import proyecto.NetSpider.entities.Node;

import java.util.ArrayList;
// Interfaz que deben de implementar almacen y equilibrador
public interface Migrable {
    /**
     * Method to import nodes to the class
     * @param nodes net nodes
     */
    public void saveNodes(ArrayList<Node> nodes);

    /**
     * Method to export nodes.
     * It returns the nodes in the CopyOnWriteArrayList
     * @return nodes
     */
    public ArrayList<Node> exportNodes();
}
