package main.proyecto.NetSpider.equilibrator;

import main.proyecto.NetSpider.data_controller.Migrable;
import main.proyecto.NetSpider.entities.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Equilibrator implements Migrable {

    private final File PythonScript = new File("./python_modules/scanner.py");

    // QUEUE OF NODES TO PROCESS
    private CopyOnWriteArrayList<Node> ProcessQueue = new CopyOnWriteArrayList<>();

    // QUEUE OF PROCESSED NODES
    private CopyOnWriteArrayList<Node> ProcessedQueue = new CopyOnWriteArrayList<>();


    public void ProcessIps() {

    }


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
