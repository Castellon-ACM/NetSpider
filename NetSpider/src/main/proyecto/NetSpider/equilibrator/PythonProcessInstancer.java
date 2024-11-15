package main.proyecto.NetSpider.equilibrator;

import main.proyecto.NetSpider.config.Configuration;

import java.util.ArrayList;

public class PythonProcessInstancer extends Thread {
    @Override
    public void run() {
        ArrayList<ProcessBuilder> processBuilders = new ArrayList<>();
        for (int i = 0; i < Configuration.processesVolume; i++) {
            ProcessBuilder builder = Equilibrator.pythonProcesses.get(i);
            processBuilders.add(builder);
        }
        Equilibrator.pythonProcesses.removeAll(processBuilders);
    }
}
