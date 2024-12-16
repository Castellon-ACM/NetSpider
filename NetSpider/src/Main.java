import config.ConfigurationSingleton;
import data_controller.DataController;
import equilibrator.Equilibrator;
import storage.ip_extractor.IpExtractor;

public class Main {
    public static void main(String[] args) {
            
        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        showConfig(config);
        DataController.startDataController();
        IpExtractor ipExtractor = new IpExtractor(); // Starts automatically
        Equilibrator.startEquilibrator();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
           ipExtractor.stopIpExtractor();
           Equilibrator.stopEquilibrator();
           DataController.stopDataController();
           // TODO: GENERATE THE PDF FILE
        })); // Shutdown hook to stop the threads when the JVM exits

    }

    private static void showConfig(ConfigurationSingleton config) {
        System.out.println(config.getAsciiArt());
        System.out.println("Expiration Time: " + config.getExpirationTime());
        System.out.println("Verbose Mode: " + config.isVerboseMode());
        System.out.println("Processes Volume: " + config.getProcessesVolume());
    }
}
