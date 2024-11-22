package equilibrator;

import java.io.File;
import java.util.concurrent.*;

import config.ConfigurationSingleton;
import entities.Node;

// ESTA CLASE EQUIVALE A LA FUNCION QUE DESEMPEÑA MANUEL ZAFRA EN EL VERGELES , ESENCIAL.

interface Arguments {
    public final String QUIET = "--quiet";
    public final String VERBOSE = "--verbose";
}

public class Equilibrator extends Thread implements Arguments {

    // Get Instance of ConfigurationSingleton

    private static final ConfigurationSingleton CONFIG = ConfigurationSingleton.getInstance();


    protected static final File pythonScript = new File("./python_modules/scanner.py");

    // PROCESSES ARRAYLIST
    public static CopyOnWriteArrayList<ProcessBuilder> pythonProcesses = new CopyOnWriteArrayList<>();

    // QUEUE OF NODES TO PROCESS
    public static CopyOnWriteArrayList<Node> processQueue = new CopyOnWriteArrayList<>();

    // QUEUE OF PROCESSED NODES
    public static CopyOnWriteArrayList<Node> ProcessedQueue = new CopyOnWriteArrayList<>();

    private static final ExecutorService executorInstancers = Executors.newFixedThreadPool(CONFIG.getPythonProcessInstancersThreads());
    private static ScheduledExecutorService equilibratorExecutor = Executors.newScheduledThreadPool(CONFIG.getEquilibratorThreads());

    public static void startEquilibrator() {
        equilibratorExecutor.scheduleAtFixedRate(new Equilibrator(), 0,
                CONFIG.getEquilibratorInterval(), TimeUnit.MILLISECONDS);
    }

    public static void stopEquilibrator() {
        executorInstancers.shutdown();
        equilibratorExecutor.shutdown();
    }

    @Override
    public void run() {
        clearAndPrepareQueue();
        startInstacers();
    }

    /**
     * Clears the ProcessQueue and creates the pythonProcesses queue
     */
    private void clearAndPrepareQueue() {
        if (!processQueue.isEmpty()) {
            for (Node node : processQueue) {
                String currentArguments = (CONFIG.isVerboseMode()) ? Arguments.VERBOSE : Arguments.QUIET;
                pythonProcesses.add(new ProcessBuilder("python", pythonScript.getAbsolutePath(),
                        node.getIp(), currentArguments));
            }
            processQueue.clear();
        }
    }
    /**
     * Method to start PythonProcessInstancer if there are processes in the queue
     */
    private void startInstacers() {
        if (!pythonProcesses.isEmpty() && pythonProcesses.size() > CONFIG.getProcessesVolume()) {
            executorInstancers.execute(new PythonProcessInstancer());
        }
    }
}
