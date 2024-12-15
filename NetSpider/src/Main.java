import processor.ReportWriter;
import entities.Node;
import entities.Port;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import processor.XMLToPDF;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear algunos puertos de ejemplo
            Port port1 = new Port("80", "Servicio de comunicación web.");
            Port port2 = new Port("22", "Servicio para acceso remoto seguro.");

            // Crear un nodo de ejemplo
            Node node1 = new Node("192.168.1.1", "Node 1", Node.OPERATIVE_SYSTEM.LINUX, new Date());
            node1.addPort(port1);
            node1.addPort(port2);

            // Crear una lista de nodos (en este caso solo un nodo)
            ArrayList<Node> nodes = new ArrayList<>();
            nodes.add(node1);

            // Archivos de salida
            File xmlFile = new File("nodos.xml");
            File pdfFile = new File("reporte.pdf");

            // Generar el archivo XML
            ReportWriter reportWriter = new ReportWriter(nodes, xmlFile, pdfFile);
            reportWriter.run(); // Genera el XML y PDF

            System.out.println("El reporte PDF ha sido generado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
