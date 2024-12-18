import Debug.DebugCenter;
import config.ConfigurationSingleton;
import data_controller.DataController;
import equilibrator.Equilibrator;
import processor.Processor;
import storage.Storage;
import storage.ip_extractor.IpExtractor;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        showConfig(config);
        DataController.startDataController();
        IpExtractor ipExtractor = new IpExtractor(); // Starts automatically
        Equilibrator.startEquilibrator();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DebugCenter.debug("Shutdown hook started");
            ipExtractor.stopIpExtractor();
            Equilibrator.stopEquilibrator();
            DataController.stopDataController();
            Processor.writeResults(new ArrayList<>(Storage.getNodes()), new File("results.pdf"));  // Writes results to a file;
        })); // Shutdown hook to stop the threads when the JVM exits

    }

    private static void showConfig(ConfigurationSingleton config) {
        System.out.println(config.getAsciiArt());
        System.out.println("Expiration Time: " + config.getExpirationTime());
        System.out.println("Verbose Mode: " + config.isVerboseMode());
        System.out.println("Processes Volume: " + config.getProcessesVolume());
    }
}