package storage.ip_extractor;
import config.ConfigurationSingleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class IpExtractor {
    private final ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    private final List<String> networkSegments = new ArrayList<>();
    
    public IpExtractor() {
        for (int i = 0; i < 256; i++) {
            networkSegments.add("192.168." + i);
        }
    }
    
    /**
     * Gets active IP addresses from all 192.168.x network segments
     * @return List of active IP addresses
     */
    public List<String> getActiveIps() {
        List<String> activeIps = new ArrayList<>();
        
        for (String segment : networkSegments) {
            for (int i = 1; i < 255; i++) {
                String ipAddress = segment + "." + i;
                if (isIpActive(ipAddress)) {
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
}