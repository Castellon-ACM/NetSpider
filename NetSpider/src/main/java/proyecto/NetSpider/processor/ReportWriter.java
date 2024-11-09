package proyecto.NetSpider.processor;

import proyecto.NetSpider.entities.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReportWriter extends Thread {

    private ArrayList<Node> nodes = new ArrayList<>();
    private File file;

    /**
     * Write the scan results into a file asynchronously
     * @param nodes List of a nodes to write
     * @param file Destination file
     */
    public ReportWriter(ArrayList<Node> nodes, File file) {
        this.nodes = nodes;
        this.file = file;
        if (!nodes.isEmpty()) this.start();
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter(file)) {
            for (Node node : nodes) {
                // IMPLEMENTAR ESCRITURA DE RESULTADOS AQUI
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
