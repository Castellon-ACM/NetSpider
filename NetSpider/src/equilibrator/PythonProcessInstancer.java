package equilibrator;

import java.util.ArrayList;

import config.ConfigurationSingleton;

public class PythonProcessInstancer extends Thread {


    // Get Instance of ConfigurationSingleton

    ConfigurationSingleton Configuration = ConfigurationSingleton.getInstance();


    @Override
    public void run() {
        ArrayList<ProcessBuilder> processBuilders = new ArrayList<>();
        for (int i = 0; i < Configuration.getProcessesVolume(); i++) {
            ProcessBuilder builder = Equilibrator.pythonProcesses.get(i);
            processBuilders.add(builder);
        }
        Equilibrator.pythonProcesses.removeAll(processBuilders);


        // SEGUIR IMPLEMENTANDO , DEBE DE COGER EL JSON DEL PROCESO Y CREAR UN NODE EN ProcessedQueue
    }
}
