package data_controller;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import entities.Node;
import equilibrator.Equilibrator;
import storage.Storage;

public class DataController extends Thread {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    
    @Override
    public void run() {
        importProcessedNodesIntoStorage();
        exportLapsedNodesToEquilibrator();
    }


    /**
     * Imports the processed nodes into the storage
     */
    private static void importProcessedNodesIntoStorage() {
        // Saves the processed nodes to the storage
        ArrayList<Node> nodes = Equilibrator.clearAndExport();
        if (!nodes.isEmpty()) {
            Storage.addNodes(nodes);
        }
    }

    /**
     * Exports the lapsed nodes to the equilibrator
     */
    private static void exportLapsedNodesToEquilibrator() {
        // Saves the lapsed nodes to the equilibrator
        ArrayList<Node> lapsedNodes = Storage.getLapsedNodes();
        if (!lapsedNodes.isEmpty()) {
            Equilibrator.importNodes(lapsedNodes);
        }
    }

    public static void startDataController() {
        DataController dataController = new DataController();
        dataController.executor.scheduleAtFixedRate(dataController, 10, 10, TimeUnit.SECONDS);
    }


}
