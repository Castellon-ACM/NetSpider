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

public class DataController {
    static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static final ConfigurationSingleton CONFIG = ConfigurationSingleton.getInstance();
    private static boolean running = true;


    public static void worker () {
        DebugCenter.debug("DataController is working");
        Storage.setLapsedNodes();
        DataController.importProcessedNodesIntoStorage();
        if (running) {
            DataController.exportLapsedNodesToEquilibrator();
        }
    }


    /**
     * Imports the processed nodes into the storage
     */
    public static void importProcessedNodesIntoStorage() {
        // Saves the processed nodes to the storage
        ArrayList<Node> nodes = Equilibrator.clearAndExport();
        if (nodes != null && !nodes.isEmpty()) {
            DebugCenter.debug("IMPORTING PROCESSED NODES TO STORAGE: " + nodes.size() + " NODES");
            Storage.addNodes(nodes);
        }
    }

    /**
     * Exports the lapsed nodes to the equilibrator
     */
    private static void exportLapsedNodesToEquilibrator() {
        // Saves the lapsed nodes to the equilibrator
        ArrayList<Node> lapsedNodes = Storage.getLapsedNodes();
        if (lapsedNodes != null && !lapsedNodes.isEmpty()) {
            DebugCenter.debug("EXPORTING LAPSED NODES TO EQUILIBRATOR: " + lapsedNodes.size() + " NODES");
            Equilibrator.importNodes(lapsedNodes);
        }
    }

    public static void startDataController() {
        DataController.executor.scheduleAtFixedRate(DataController::worker, 0, CONFIG.getDataControllerPeriod(), TimeUnit.SECONDS);
    }

    public static void stopDataController() {
        try {
            running = false;
            while (!Equilibrator.ProcessedQueue.isEmpty()) {
                Thread.sleep(1000);
            }
            executor.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
