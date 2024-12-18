package equilibrator;
import Debug.DebugCenter;
import cpu.UtilsCpu;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import config.ConfigurationSingleton;
import processor.OutputParser;

public class ProcessInstancer extends Thread {

    private final ConfigurationSingleton Configuration = ConfigurationSingleton.getInstance();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ArrayList<ProcessBuilder> processBuilders; // List of ProcessBuilder objects to be started


    @Override
       public void run() {
        processBuilders = new ArrayList<>();
        int i = 0;
        while (i < Configuration.getProcessesVolume() && i < Equilibrator.cProcesses.size()) {
            ProcessBuilder builder = Equilibrator.cProcesses.get(i);
            processBuilders.add(builder);
            i++;
            DebugCenter.debug("Importing c process into ProcessInstancer ");
        }
        if (!processBuilders.isEmpty()) {
            Equilibrator.cProcesses.removeAll(processBuilders);
            // We use a scheduled executor to run the checkAndProcessBuilders method periodically
            // This ensures that the c processes are only started when the CPU load is below the maximum allowed value
            scheduler.scheduleAtFixedRate(this::checkAndProcessBuilders, 0,
                    Configuration.getThreadSleepCModuleInstancer(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Checks the CPU load and starts new processes if the load is below the maximum allowed value
     */
    private void checkAndProcessBuilders() {
        DebugCenter.debug("Checking CPU load... " + UtilsCpu.getCpuLoad() + " / " + Configuration.getMaxCpuLoad()  + " %"  );
        if (UtilsCpu.getCpuLoad() <= Configuration.getMaxCpuLoad()) {
            while (UtilsCpu.getCpuLoad() <= Configuration.getMaxCpuLoad() && !processBuilders.isEmpty()  ) {
                ProcessBuilder builder = processBuilders.getLast();
                processInstancer(builder);

                processBuilders.remove(builder);
            }
        }
        if (processBuilders.isEmpty()) {
            scheduler.shutdown();
            DebugCenter.debug("All c processes started, shutting down process instancer... ");
        }
    }

    /**
     * Starts a new c process and processes its output
     * @param builder The ProcessBuilder for the Python process.
     */
    private void processInstancer(ProcessBuilder builder) {
        try {
            DebugCenter.debug("Starting new c process " + builder.command()  );
            Process process = builder.start();
            new OutputParser(process).start();
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}


 
