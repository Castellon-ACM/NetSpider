package processor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import Debug.DebugCenter;
import entities.Node;
import entities.Port;
import equilibrator.Equilibrator;


public class OutputParser extends Thread {
    private Process cProcess;
    private String ip;

    /**
     * Receives a Process object and starts the thread
     *
     * @param cProcess
     */
    public OutputParser(Process cProcess, String ip) {
        this.cProcess = cProcess;
        this.ip = ip;
    }


    @Override
    public void run() {
        if (cProcess != null) {
            try {
                cProcess.waitFor(); // waits until the process finishes
                System.out.println("Process finished for: " + ip);
                Node nodeFromOutput = toNode(cProcess);
                Equilibrator.addProcessedNode(nodeFromOutput);
                DebugCenter.debug("Node parsed from output: " + nodeFromOutput.getIp());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    private Node toNode(Process process) {
        Node node = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            ArrayList<Port> ports = new ArrayList<Port>();
            Node.OPERATIVE_SYSTEM operatingSystem = Node.OPERATIVE_SYSTEM.LINUX;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("puerto" )) {
                    String[] parts = line.split("|");
                    String portNumber = parts[1];
                    String serviceDescription = parts[2];
                    ports.add(new Port(portNumber, serviceDescription));

                }
                if (line.startsWith("sistemaoperativo" )) {
                    String[] parts = line.split("|");
                    String op = parts[1];
                    operatingSystem = op.equalsIgnoreCase("windows")? Node.OPERATIVE_SYSTEM.WINDOWS : Node.OPERATIVE_SYSTEM.LINUX;
                }

            }
            node = new Node( ip, operatingSystem, ports, new Date() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}
