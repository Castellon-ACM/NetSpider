package processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import entities.Node;
import entities.Port;
import entities.Service;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class ReportWriter extends Thread {

    private ArrayList<Node> nodes = new ArrayList<>();
    private File file;

    /**
     * Write the scan results into a file asynchronously
     * @param nodes List of nodes to write
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

            JAXBContext context = JAXBContext.newInstance(Node.class, Port.class, Service.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            for (Node node : nodes) {
                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(node, stringWriter);

                writer.write(stringWriter.toString());
                writer.write("\n\n");
            }

        } catch (IOException | JAXBException e) {
            throw new RuntimeException("Error al escribir el archivo XML", e);
        }
    }
}
