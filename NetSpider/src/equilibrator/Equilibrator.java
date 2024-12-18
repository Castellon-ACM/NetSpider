package equilibrator;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


    protected static final File cScript = new File("./c_modules/extraktorMACOS");

    // PROCESSES ARRAYLIST
    public static CopyOnWriteArrayList<ProcessBuilder> cProcesses = new CopyOnWriteArrayList<>();

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
     * Clears the ProcessQueue and creates the cProcesses queue
     */
    private void clearAndPrepareQueue() {
        if (!processQueue.isEmpty()) {
            for (Node node : processQueue) {
                String currentArguments = (CONFIG.isVerboseMode()) ? Arguments.VERBOSE : Arguments.QUIET;
                cProcesses.add(new ProcessBuilder("", cScript.getAbsolutePath(),
                        node.getIp(), currentArguments));
            }
            processQueue.clear();
        }
    }
    /**
     * Method to start PythonProcessInstancer if there are processes in the queue
     */
    private void startInstacers() {
        if (!cProcesses.isEmpty() && cProcesses.size() > CONFIG.getProcessesVolume()) {
            executorInstancers.execute(new ProcessInstancer());
        }
    }
   
    /**
     * Method to export processed nodes to another thread
     * @return ArrayList with processed nodes
     */
    public static ArrayList<Node> clearAndExport() {
        ArrayList<Node> nodes = null;
        if (!ProcessedQueue.isEmpty()) {
            nodes = new ArrayList<>(ProcessedQueue);
            ProcessedQueue.clear();
        }
        return nodes;
    }
    public static void importNodes(ArrayList<Node> nodes) {
        processQueue.addAll(nodes);
    }
}
