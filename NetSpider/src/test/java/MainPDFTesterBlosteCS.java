package test.java;

import entities.Node;

import java.io.File;
import java.util.ArrayList;

import entities.Node;
import processor.ReportWriter;

public class MainPDFTesterBlosteCS {
    public static void main(String[] args) {

        ArrayList<Node> nodes = new ArrayList<>();

        nodes.add(new Node());
        nodes.get(0).setIp("192.168");
        nodes.get(0).setOperativeSystem(Node.OPERATIVE_SYSTEM.LINUX);
        nodes.get(0).setPorts(new ArrayList<>());

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
