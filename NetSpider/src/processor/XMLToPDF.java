package processor;

import entities.Node;
import entities.Port;
import entities.Service;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
                .setBold()
                .setFontSize(18)
                .setFontColor(ColorConstants.BLUE)
                .setMarginBottom(20));
    }
    /**
     * Iterates over the nodes and passes them to specific methods
     */
    private void addNodesContent(Document document, ArrayList<Node> nodes) {
        for (Node node : nodes) {
            addNodeHeader(document, node);
            addNodeDetails(document, node);
            addPortsTable(document, node);
        }
    }
    /**
     * Method to add the node header
     */
    private void addNodeHeader(Document document, Node node) {
        document.add(new Paragraph("Nodo: " + node.getNodeName())
                .setBold()
                .setFontSize(14)
                .setFontColor(ColorConstants.DARK_GRAY));
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
        Table table = new Table(new float[]{2, 4, 4});

        table.addHeaderCell(new Cell().add(new Paragraph("Puerto").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Servicio").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Descripción").setBold()));

        for (Port port : node.getPorts()) {
            table.addCell(port.getPortNumber());
            Service service = port.getService();
            table.addCell(service != null ? service.getName() : "Unknown");
            table.addCell(service != null ? service.getDescription() : "N/A");
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
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setMarginTop(20));
    }


    /**
     * Read XML with Unmarshaller
     */
    private ArrayList<Node> readNodesFromXML() throws JAXBException {
        ArrayList<Node> nodes = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(xmlFile)) {

            JAXBContext context = JAXBContext.newInstance(Node.class, Port.class, Service.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Node node = (Node) unmarshaller.unmarshal(inputStream);
            nodes.add(node);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el XML: " + e.getMessage(), e);
        }
        return nodes;
    }
}

