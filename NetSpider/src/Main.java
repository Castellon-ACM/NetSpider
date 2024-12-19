import Debug.DebugCenter;
import config.ConfigurationSingleton;
import data_controller.DataController;
import entities.Node;
import equilibrator.Equilibrator;
import processor.ReportWriter;
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
            System.out.println("IP extractor stopped");
            Equilibrator.stopEquilibrator();
            System.out.println("Equilibrator stopped");
            DataController.stopDataController();
            System.out.println("Data controller stopped");

            ArrayList<Node> nodes = new ArrayList<>(Storage.getNodes());
            ReportWriter report = new ReportWriter(new ArrayList<>(nodes));

            report.start();
            try {
                report.join(); // Wait for the report to finish
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        })); // Shutdown hook to stop the threads when the JVM exits

    }

    private static void showConfig(ConfigurationSingleton config) {
        System.out.println(config.getAsciiArt());
        System.out.println("Expiration Time: " + config.getExpirationTime());
        System.out.println("Verbose Mode: " + config.isVerboseMode());
        System.out.println("Processes Volume: " + config.getProcessesVolume());
    }
}