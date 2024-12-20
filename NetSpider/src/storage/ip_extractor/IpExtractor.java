package storage.ip_extractor;
import Debug.DebugCenter;
import config.ConfigurationSingleton;
import config.SCAN_TYPE;
import entities.Node;
import storage.Storage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class IpExtractor {
    private final ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    private final List<String> networkSegments = new ArrayList<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * Constructor for IP extractor , starts automatically scanning and storing IP addresses
     */
    public IpExtractor() {
        SCAN_TYPE type = config.getIpScannerType();
        if (type == SCAN_TYPE.FULL) {
            initFullNetwork();
        } else if (type == SCAN_TYPE.PARTIAL && config.getIpRange() != null) {
            initPartialNetwork();
        }
        scanAndStore(); // Starts scanning and storing IP addresses
    }

    private void initPartialNetwork() {
        // Example: "192.168.0"
        String partialNetwork = config.getIpRange();
        networkSegments.add(partialNetwork);
        DebugCenter.debug("Partial network scanning enabled: " + partialNetwork);
    }

    private void initFullNetwork() {
        for (int i = 0; i < 256; i++) {
            networkSegments.add("192.168." + i);
        }
    }
    
    /**
     * Gets active IP addresses from all 192.168.x network segments
     * @return List of active IP addresses
     */
    private List<String> getActiveIps() {
        List<String> activeIps = new ArrayList<>();
        
        for (String segment : networkSegments) {
            for (int i = 1; i < 255; i++) {
                String ipAddress = segment + "." + i;
                if (isIpActive(ipAddress)) {
                    DebugCenter.debug("Found active IP address: " + ipAddress);
                    activeIps.add(ipAddress);
                }
            }
        }
        return activeIps;
    }

    /**
     * Checks if the given IP address is active
     * @param ipAddress
     * @return true if the IP address is active
     */
    private boolean isIpActive(String ipAddress) {
        try {
            InetAddress inet = InetAddress.getByName(ipAddress);
            return inet.isReachable(config.getIpScannerTimeout()); // Timeout
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Parses and saves the active IP addresses into the storage system
     * @param activeIps
     */
    private void nodeParserAndSaver(List<String> activeIps) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (String activeIp : activeIps) {
            nodes.add(new Node(activeIp));
        }
        Storage.addNodes(nodes);
        DebugCenter.debug("Stored " + nodes.size() + " nodes from active IP addresses");
    }

    /**
     * Starts the IP scanning and storage process
     */
    public void scanAndStore () {
        executor.scheduleAtFixedRate(() -> {
            List<String> activeIps = getActiveIps();
            nodeParserAndSaver(activeIps);
        }, 0, config.getIpScannerSecondsInterval(), TimeUnit.SECONDS);
    }
    public void stopIpExtractor() {
        try {
            executor.awaitTermination(config.getShutdownTimeout(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}