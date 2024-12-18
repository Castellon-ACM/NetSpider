package processor;
import entities.Node;
import entities.Port;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import entities.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class XMLToPDF {
    private File xmlFile;
    private File pdfFile;

    /**
     * Constructor
     * @param xmlFile Destination XML file
     * @param pdfFile Destination PDF file
     */
    public XMLToPDF(File xmlFile, File pdfFile) {
        this.xmlFile = xmlFile;
        this.pdfFile = pdfFile;
    }

    /**
     * Generate PDF and do format
     */
    public void generatePDF() throws IOException, JAXBException {
        ArrayList<Node> nodes = readNodesFromXML();
        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        addTitle(document);
        addNodesContent(document, nodes);
        addFooter(document);

        document.close();
    }

    /**
     * Method to add the title in the PDF
     */
    private void addTitle(Document document) {
        document.add(new Paragraph("Reporte de Escaneo")
                .setFontSize(18)
                .setFontColor(ColorConstants.BLUE)
                .setMarginBottom(20));
    }

    /**
     * Iterates over the nodes and passes them to specific methods
     */
    private void addNodesContent(Document document, ArrayList<Node> nodes) {
        for (Node node : nodes) {

            addNodeDetails(document, node);
            addPortsTable(document, node);
        }
    }



    /**
     * Method to add the node details
     */
    private void addNodeDetails(Document document, Node node) {
        document.add(new Paragraph("IP: " + node.getIp()));
        document.add(new Paragraph("Sistema Operativo: " + node.getOperativeSystem()));
        document.add(new Paragraph("Última Actualización: " + node.getLastUpdate()));
        document.add(new Paragraph("Estado: " + (node.isLapsed() ? "Lapsado" : "Activo"))
                .setMarginBottom(10));
    }

    /**
     * Method to add a table with all the ports
     */
    private void addPortsTable(Document document, Node node) {
        Table table = new Table(new float[]{2, 4});

        table.addHeaderCell(new Cell().add(new Paragraph("Puerto")));
        table.addHeaderCell(new Cell().add(new Paragraph("Descripción")));

        for (Port port : node.getPorts()) {
            table.addCell(port.getPortNumber());
            table.addCell(port.getServiceDescription() != null ? port.getServiceDescription() : "N/A");
        }

        document.add(table.setMarginBottom(20));
    }

    /**
     * Method to add the footer in PDF
     */
    private void addFooter(Document document) {
        document.add(new Paragraph("PDF generado automáticamente")
                .setFontColor(ColorConstants.GRAY)
                .setFontSize(10)
                .setMarginTop(20));
    }

    /**
     * Read XML with Unmarshaller
     */
    private ArrayList<Node> readNodesFromXML() throws JAXBException {
        ArrayList<Node> nodes = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(xmlFile)) {

            JAXBContext context = JAXBContext.newInstance(Node.class, Port.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Forzar la codificación UTF-8 para la lectura del archivo
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Node node = (Node) unmarshaller.unmarshal(reader);
            nodes.add(node);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el XML: " + e.getMessage(), e);
        }
        return nodes;
    }

}
