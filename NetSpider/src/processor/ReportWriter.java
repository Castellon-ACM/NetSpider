package processor;

import config.ConfigurationSingleton;
import entities.Node;
import entities.Port;
import entities.NodeList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReportWriter extends Thread {
    private ArrayList<Node> nodes = new ArrayList<>();
    ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    private File xmlFile = new File(config.getExportPath()+"xmlReport.xml");

    /**
     * Constructor.
     * @param nodes List of nodes to write
     */
    public ReportWriter(ArrayList<Node> nodes ) {
        this.nodes = nodes;

    }

    @Override
    public void run() {
        writeNodesToFile();
        generatePDFReport();
    }

    /**
     * Writes the list of nodes to the file in XML format.
     */
    private void writeNodesToFile() {
        try (FileWriter writer = new FileWriter( xmlFile,
                StandardCharsets.UTF_8)) {
            JAXBContext context = JAXBContext.newInstance(NodeList.class, Node.class, Port.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());

            // Uses NODELIST; NOT NODES ARRAY!!!!!!!!!!!!
            NodeList nodeList = new NodeList();
            nodeList.setNodes(nodes);

            // Puts the Bloste inside the Marshaller
            marshaller.marshal(nodeList, writer);

        } catch (IOException | JAXBException e) {
            throw new RuntimeException("Error al escribir el archivo XML", e);
        }
    }

    /**
     * Generates a PDF report from the XML file.
     */
    private void generatePDFReport() {
        try {
            XMLToPDF converter = new XMLToPDF(xmlFile);
            converter.generatePDF();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el archivo PDF", e);
        }
    }
}