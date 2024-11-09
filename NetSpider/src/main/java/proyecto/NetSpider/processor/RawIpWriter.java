package proyecto.NetSpider.processor;

import proyecto.NetSpider.entities.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RawIpWriter {
    /**
     * Writes a file with the nodes ips
     * @param nodes Node object
     * @param file Destination file
     * @param regex Regex to separate the ips
     */
    public static void IpWriter(ArrayList<Node> nodes, File file , char regex) {
        try (FileWriter writer = new FileWriter(file)){
            if (!nodes.isEmpty()) {
                for (Node node : nodes) {
                    writer.write(node.getIp() + regex);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
