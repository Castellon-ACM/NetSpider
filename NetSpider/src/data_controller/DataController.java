package data_controller;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Debug.DebugCenter;
import config.ConfigurationSingleton;
import entities.Node;
import equilibrator.Equilibrator;
import storage.Storage;

public class DataController extends Thread {
    static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static final ConfigurationSingleton CONFIG = ConfigurationSingleton.getInstance();


    @Override
    public void run() {
        Storage.setLapsedNodes();
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
            DebugCenter.debug("IMPORTING PROCESSED NODES TO STORAGE: " + nodes.size() + " nodes");
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
            DebugCenter.debug("EXPORTING LAPSED NODES TO EQUILIBRATOR: " + lapsedNodes.size() + " nodes");
            Equilibrator.importNodes(lapsedNodes);
        }
    }

    public static void startDataController() {
        DataController.executor.scheduleAtFixedRate(new DataController(), 10, CONFIG.getDataControllerPeriod(), TimeUnit.SECONDS);
    }

    public static void stopDataController() {
        executor.shutdown();
    }


}
