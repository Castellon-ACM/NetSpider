package proyecto.NetSpider.storage;

import proyecto.NetSpider.data_controller.Migrable;
import proyecto.NetSpider.entities.Node;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


// Clase almacén
// Dificultad fácil
public class Storage implements Migrable {
    private CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList<>();

    /**
     * Method to import nodes to the class
     *
     * @param nodes net nodes
     */
    @Override
    public void saveNodes(ArrayList<Node> nodes) {
        // IMPLEMENTAR
    }

    /**
     * Method to export nodes.
     * It returns the nodes in the CopyOnWriteArrayList
     *
     * @return nodes
     */
    @Override
    public ArrayList<Node> exportNodes() {
        // IMPLEMENTAR
        return null;
    }
}
