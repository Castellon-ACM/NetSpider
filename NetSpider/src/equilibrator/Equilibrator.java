package equilibrator;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Debug.DebugCenter;
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

    // "c_modules/extraktorLINUX"
    protected static final File cScript = new File("/Users/felixcaba/Documents/Proyectos/NetSpider/NetSpider/NetSpider/src/equilibrator/c_modules/extraktorMACOS");

    // PROCESSES ARRAYLIST
    public static CopyOnWriteArrayList<ProcessBuilder> cProcesses = new CopyOnWriteArrayList<>();

    // QUEUE OF NODES TO PROCESS
    public static CopyOnWriteArrayList<Node> processQueue = new CopyOnWriteArrayList<>();

    // QUEUE OF PROCESSED NODES
    public static CopyOnWriteArrayList<Node> ProcessedQueue = new CopyOnWriteArrayList<>();

    private static final ExecutorService executorInstancers = Executors.newFixedThreadPool(CONFIG.getCProcessInstancersThreads());
    private static ScheduledExecutorService equilibratorExecutor = Executors.newScheduledThreadPool(CONFIG.getEquilibratorThreads());

    public static void startEquilibrator() {
        DebugCenter.debug("Starting Equilibrator...");
        equilibratorExecutor.scheduleAtFixedRate(new Equilibrator(), 0,
                CONFIG.getEquilibratorInterval(), TimeUnit.SECONDS);
    }

    public static void stopEquilibrator() {
        try {
            executorInstancers.awaitTermination(CONFIG.getShutdownTimeout(), TimeUnit.SECONDS);
            equilibratorExecutor.awaitTermination(CONFIG.getShutdownTimeout(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
                DebugCenter.debug("Adding process to cProcesses queue in equilibrator: " + node.getIp());
                String currentArguments = (CONFIG.isVerboseMode()) ? Arguments.VERBOSE : Arguments.QUIET;

                System.out.println(Paths.get(cScript.getAbsolutePath()));
                System.out.println(node.getIp());

                cProcesses.add(new ProcessBuilder( cScript.getAbsolutePath(),
                        node.getIp(), currentArguments));
            }
            processQueue.clear();
        }
    }
    /**
     * Method to start ProcessInstancer if there are processes in the queue
     */
    private void startInstacers() {
        DebugCenter.debug("Attempting to start new process instancer ");

        if (!cProcesses.isEmpty()) {
            DebugCenter.debug("Starting new process instancer... ");
            executorInstancers.execute(new ProcessInstancer());

        }
    }
   
    /**
     * Method to export processed nodes to another thread
     * @return ArrayList with processed nodes
     */
    public static ArrayList<Node> clearAndExport() {
        ArrayList<Node> nodes = new ArrayList<>(ProcessedQueue);
        if (!ProcessedQueue.isEmpty()) {
            ProcessedQueue.clear();
        }
        return nodes;
    }
    /**
     * Method to import nodes to the process queue
     * @param nodes ArrayList with nodes to be imported
     */
    public static void importNodes(ArrayList<Node> nodes) {
        processQueue.addAll(nodes);
    }

    /**
     * Method to add a processed node to the ProcessedQueue
     * @param node node to be added
     */
    public static void addProcessedNode(Node node) {
        ProcessedQueue.add(node);
    }
}
