package equilibrator;
import cpu.UtilsCpu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import config.ConfigurationSingleton;
import processor.PythonOutputParser;

public class PythonProcessInstancer extends Thread {

    private final ConfigurationSingleton Configuration = ConfigurationSingleton.getInstance();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ArrayList<ProcessBuilder> processBuilders;

    @Override
    public void run() {
        processBuilders = new ArrayList<>();
        for (int i = 0; i < Configuration.getProcessesVolume(); i++) {
            ProcessBuilder builder = Equilibrator.pythonProcesses.get(i);
            processBuilders.add(builder);
        }
        Equilibrator.pythonProcesses.removeAll(processBuilders);
        // We use a scheduled executor to run the checkAndProcessBuilders method periodically
        // This ensures that the Python processes are only started when the CPU load is below the maximum allowed value
        scheduler.scheduleAtFixedRate(this::checkAndProcessBuilders, 0,
                Configuration.getThreadSleepPythonInstancer(), TimeUnit.MILLISECONDS);
    }

    /**
     * Checks the CPU load and starts new Python processes if the load is below the maximum allowed value
     */
    private void checkAndProcessBuilders() {
        if (UtilsCpu.getCpuLoad() <= Configuration.getMaxCpuLoad()) {
            while (UtilsCpu.getCpuLoad() <= Configuration.getMaxCpuLoad()) {
                ProcessBuilder builder = processBuilders.getLast();
                processInstancer(builder);
                processBuilders.remove(builder);
            }
            if (processBuilders.isEmpty()) {
                scheduler.shutdown();
            }
        }
    }

    /**
     * Starts a new Python process and processes its output
     * @param builder The ProcessBuilder for the Python process.
     */
    private void processInstancer(ProcessBuilder builder) {
        try {
            Process process = builder.start();
            // NEW THREAD TO PROCESS THE OUTPUT OF THE PYTHON PROCESS
            new PythonOutputParser(process).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


 
