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
    private File xmlFile;
    private File pdfFile;

    /**
     * Constructor.
     * @param nodes List of nodes to write
     * @param xmlFile Destination XML file
     * @param pdfFile Destination PDF file
     */
    public ReportWriter(ArrayList<Node> nodes, File xmlFile, File pdfFile) {
        this.nodes = nodes;
        this.xmlFile = xmlFile;
        this.pdfFile = pdfFile;
        if (!nodes.isEmpty()) this.start();
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
        try (FileWriter writer = new FileWriter(xmlFile)) {

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

    /**
     * Generates a PDF report from the XML file.
     */
    private void generatePDFReport() {
        try {
            XMLToPDF converter = new XMLToPDF(xmlFile, pdfFile);
            converter.generatePDF();

            System.out.println("PDF generado exitosamente en: " + pdfFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el archivo PDF", e);
        }
    }
}
