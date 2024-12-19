package processor;

import config.ConfigurationSingleton;
import entities.Node;
import entities.Port;
import entities.NodeList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class XMLToPDF {
    private static final Color PRIMARY_COLOR = new DeviceRgb(41, 128, 185);
    private static final Color SECONDARY_COLOR = new DeviceRgb(52, 73, 94);
    private static final Color WARNING_COLOR = new DeviceRgb(231, 76, 60);
    private static final float TITLE_FONT_SIZE = 24f;
    private static final float SUBTITLE_FONT_SIZE = 18f;
    private static final float NORMAL_FONT_SIZE = 12f;
    private static final float SMALL_FONT_SIZE = 10f;

    private final ConfigurationSingleton config;
    private final File pdfFile;
    private final File xmlFile;
    private PdfFont boldFont;
    private PdfFont normalFont;

    public XMLToPDF(File xmlFile) {
        this.config = ConfigurationSingleton.getInstance();
        this.pdfFile = new File(config.getExportPath() + "ReportePDF" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM\\dd-HH_mm")) + ".pdf");
        this.xmlFile = xmlFile;
    }

    public void generatePDF() throws IOException, JAXBException {
        initializeFonts();
        NodeList nodes = readNodesFromXML();

        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.setMargins(36, 36, 36, 36);

        addTitle(document);
        addSummary(document, nodes.getNodes());
        addNodesContent(document, nodes.getNodes());
        addFooter(document);

        document.close();
    }

    private void initializeFonts() throws IOException {
        boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    }

    private NodeList readNodesFromXML() throws JAXBException {
        try (FileInputStream inputStream = new FileInputStream(xmlFile)) {
            JAXBContext context = JAXBContext.newInstance(NodeList.class, Node.class, Port.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return (NodeList) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el XML: " + e.getMessage(), e);
        }
    }

    private void addTitle(Document document) {
        Paragraph title = new Paragraph("Reporte de Escaneo de Red")
                .setFont(boldFont)
                .setFontSize(TITLE_FONT_SIZE)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30);

        Paragraph date = new Paragraph("Generado el: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .setFont(normalFont)
                .setFontSize(SMALL_FONT_SIZE)
                .setFontColor(SECONDARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(40);

        document.add(title);
        document.add(date);
    }

    private void addSummary(Document document, ArrayList<Node> nodes) {
        Paragraph summaryTitle = new Paragraph("Resumen del Escaneo")
                .setFont(boldFont)
                .setFontSize(SUBTITLE_FONT_SIZE)
                .setFontColor(SECONDARY_COLOR)
                .setMarginBottom(20);

        Table summaryTable = new Table(new float[]{1, 1, 1})
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(40);

        addSummaryCell(summaryTable, "Total de Nodos", String.valueOf(nodes.size()));
        addSummaryCell(summaryTable, "Nodos Activos",
                String.valueOf(nodes.stream().filter(n -> !n.isLapsed()).count()));
        addSummaryCell(summaryTable, "Nodos Lapsados",
                String.valueOf(nodes.stream().filter(Node::isLapsed).count()));

        document.add(summaryTitle);
        document.add(summaryTable);
    }

    private void addSummaryCell(Table table, String label, String value) {
        Cell cell = new Cell()
                .setBorder(new SolidBorder(PRIMARY_COLOR, 1))
                .setPadding(10);

        Paragraph labelPara = new Paragraph(label)
                .setFont(boldFont)
                .setFontSize(SMALL_FONT_SIZE)
                .setFontColor(SECONDARY_COLOR);

        Paragraph valuePara = new Paragraph(value)
                .setFont(boldFont)
                .setFontSize(NORMAL_FONT_SIZE)
                .setFontColor(PRIMARY_COLOR);

        cell.add(labelPara).add(valuePara);
        table.addCell(cell);
    }

    private void addNodesContent(Document document, ArrayList<Node> nodes) {
        Paragraph nodesTitle = new Paragraph("Detalle de Nodos")
                .setFont(boldFont)
                .setFontSize(SUBTITLE_FONT_SIZE)
                .setFontColor(SECONDARY_COLOR)
                .setMarginBottom(20);

        document.add(nodesTitle);

        for (Node node : nodes) {
            addNodeDetails(document, node);
            addPortsTable(document, node);
        }
    }

    private void addNodeDetails(Document document, Node node) {
        Color statusColor = node.isLapsed() ? WARNING_COLOR : new DeviceRgb(46, 204, 113);

        Table nodeTable = new Table(new float[]{1})
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);

        Cell headerCell = new Cell()
                .setBackgroundColor(PRIMARY_COLOR)
                .setPadding(10);

        Paragraph ipPara = new Paragraph("Nodo: " + node.getIp())
                .setFont(boldFont)
                .setFontSize(NORMAL_FONT_SIZE)
                .setFontColor(ColorConstants.WHITE);

        headerCell.add(ipPara);
        nodeTable.addHeaderCell(headerCell);

        Cell detailsCell = new Cell().setPadding(10);
        detailsCell.add(new Paragraph("Sistema Operativo: " + node.getOperativeSystem())
                .setFont(normalFont)
                .setFontSize(NORMAL_FONT_SIZE));
        detailsCell.add(new Paragraph("Última Actualización: " + node.getLastUpdate())
                .setFont(normalFont)
                .setFontSize(NORMAL_FONT_SIZE));
        detailsCell.add(new Paragraph("Estado: " + (node.isLapsed() ? "Lapsado" : "Activo"))
                .setFont(boldFont)
                .setFontSize(NORMAL_FONT_SIZE)
                .setFontColor(statusColor));

        nodeTable.addCell(detailsCell);
        document.add(nodeTable);
    }

    private void addPortsTable(Document document, Node node) {
        Table table = new Table(new float[]{2, 4})
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(40);

        // Header
        Cell portHeader = new Cell()
                .setBackgroundColor(SECONDARY_COLOR)
                .setPadding(10)
                .add(new Paragraph("Puerto")
                        .setFont(boldFont)
                        .setFontSize(NORMAL_FONT_SIZE)
                        .setFontColor(ColorConstants.WHITE));

        Cell descHeader = new Cell()
                .setBackgroundColor(SECONDARY_COLOR)
                .setPadding(10)
                .add(new Paragraph("Descripción")
                        .setFont(boldFont)
                        .setFontSize(NORMAL_FONT_SIZE)
                        .setFontColor(ColorConstants.WHITE));

        table.addHeaderCell(portHeader);
        table.addHeaderCell(descHeader);

        // Content
        for (Port port : node.getPorts()) {
            Cell portCell = new Cell().setPadding(10);
            Cell descCell = new Cell().setPadding(10);

            portCell.add(new Paragraph(port.getPortNumber())
                    .setFont(normalFont)
                    .setFontSize(NORMAL_FONT_SIZE));

            descCell.add(new Paragraph(port.getServiceDescription() != null ?
                    port.getServiceDescription() : "N/A")
                    .setFont(normalFont)
                    .setFontSize(NORMAL_FONT_SIZE));

            table.addCell(portCell);
            table.addCell(descCell);
        }

        document.add(table);
    }

    private void addFooter(Document document) {
        Table footerTable = new Table(new float[]{1})
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedPosition(36, 20, UnitValue.createPercentValue((100) - 72));

        Cell footerCell = new Cell()
                .setBorder(null)
                .add(new Paragraph("NetSpider by JaggerFelix, SuperManuel, HyperCastellon")
                        .setFont(normalFont)
                        .setFontSize(SMALL_FONT_SIZE)
                        .setFontColor(SECONDARY_COLOR)
                        .setTextAlignment(TextAlignment.CENTER));

        footerTable.addCell(footerCell);
        document.add(footerTable);
    }
}