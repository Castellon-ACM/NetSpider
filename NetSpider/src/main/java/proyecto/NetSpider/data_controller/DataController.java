package proyecto.NetSpider.data_controller;

import proyecto.NetSpider.entities.Node;

import java.util.ArrayList;

// CONTROLADOR DE DATOS
// DIFICULTAD DIFICIL
public class DataController extends Thread {

    private ArrayList<Node> lapsedNodes = new ArrayList<>();
    private ArrayList<Node> processedNodes = new ArrayList<>();


    public ArrayList<Node> getLapsedNodes() {
        return lapsedNodes;
    }

    public void setLapsedNodes(ArrayList<Node> lapsedNodes) {
        this.lapsedNodes = lapsedNodes;
    }

    public ArrayList<Node> getProcessedNodes() {
        return processedNodes;
    }

    public void setProcessedNodes(ArrayList<Node> processedNodes) {
        this.processedNodes = processedNodes;
    }
}
