package processor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import entities.Node;
import entities.Port;


// RECOGERÁ TAMBIEN EL OUTPUT DEL PROCESO DE PYTHON
// DEBE DE PARSEAR EL JSON QUE DEVUELVE EL JEFAZO DE FELIX EN SU PROCESO DE PYTHON Y CONVERTIRLO A UN
// OBJETO NODE. UNA VEZ SE CONVIERTA SE GUARDARÁ EN EL PROCESSED QUEUE DEL EQUILIBRATOR
//  ********************SE HARÁ DE MANERA ASÍNCRONA*********************
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
        if (cProcess != null) {

            Node nodeFromOutput = toNode(cProcess);
            


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
                }
                if (line.startsWith("cerrados")) {
                    String[] parts = line.split("|");
                    String closedPorts = parts[1];
                    node.setClosedPorts(Integer.parseInt(closedPorts));
                }
                if (line.startsWith("sistemaoperativo")) {
                    String[] parts = line.split("|");
                    String os = parts[1];
                    node.setOperativeSystem(os);
                 }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}
