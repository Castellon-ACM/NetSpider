package storage.ip_extractor;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

//EXTRACTOR DE IPS, ESCANEA LAS IPS DE LA RED
// DIFICULTAD MEDIA
public class IpExtractor {

    /**
     * Gets active IP addresses from a list of given IP addresses
     * @param ipAddresses
     * @return List of active IP addresses
     */
    public List<String> getActiveIps(List<String> ipAddresses) {
        List<String> activeIps = new ArrayList<>();
        for (String ip : ipAddresses) {
            if (isIpActive(ip)) {
                activeIps.add(ip);
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
            return inet.isReachable(1000); // Timeout of 1000ms
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}