package processor;

import entities.Node;
import entities.Port;
import entities.Service;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;

public class XMLToPDF {
    private File xmlFile;

    public XMLToPDF(File xmlFile, File pdfFile) {
        this.xmlFile = xmlFile;

    }

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

