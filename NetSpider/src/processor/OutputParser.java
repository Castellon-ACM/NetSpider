package processor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import Debug.DebugCenter;
import entities.Node;
import entities.Port;
import equilibrator.Equilibrator;


public class OutputParser extends Thread {
    private Process cProcess;

    /**
     * Receives a Process object and starts the thread
     *
     * @param cProcess
     */
    public OutputParser(Process cProcess) {
        this.cProcess = cProcess;
    }


    @Override
    public void run() {
        DebugCenter.debug("OutputParser started for process, is alive? : " + cProcess.isAlive());
        if (cProcess != null) {
            try {
                DebugCenter.debug("OutputParser waiting for process to finish... ");
                cProcess.waitFor(); // waits until the process finishes
                Node nodeFromOutput = toNode(cProcess);
                Equilibrator.addProcessedNode(nodeFromOutput);
                DebugCenter.debug("Node parsed from output: " + nodeFromOutput.getIp());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    private Node toNode(Process process) {
        Node node = new Node();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("puerto" )) {
                    String[] parts = line.split("|");
                    String portNumber = parts[1];
                    String serviceDescription = parts[2];
                    Port port = new Port(portNumber, serviceDescription);
                    node.addPort(port);
                    node.setLastUpdate(new Date());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}
