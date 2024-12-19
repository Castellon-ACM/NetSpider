package test.java;

import entities.Node;

import java.io.File;
import java.util.ArrayList;

import entities.Node;
import entities.Port;
import processor.ReportWriter;

public class MainPDFTesterBlosteCS {
    public static void main(String[] args) {

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Port> portsTest = new ArrayList<Port>();


        portsTest.add(new Port("23", "xd"));
        portsTest.add(new Port("80", "xd"));
        portsTest.add(new Port("443", "xd"));


        nodes.add(new Node());
        nodes.get(0).setIp("192.168");
        nodes.get(0).setOperativeSystem(Node.OPERATIVE_SYSTEM.LINUX);
        nodes.get(0).setPorts(portsTest);



        nodes.add(new Node());
        nodes.get(1).setIp("192.168");
        nodes.get(1).setOperativeSystem(Node.OPERATIVE_SYSTEM.WINDOWS);
        nodes.get(1).setPorts(new ArrayList<>());


        ReportWriter report = new ReportWriter(new ArrayList<>(nodes));

        report.start();
        try {
            report.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
