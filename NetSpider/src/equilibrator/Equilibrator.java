package equilibrator;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import config.ConfigurationSingleton;
import entities.Node;

interface Arguments {
    public final String QUIET = "--quiet";
    public final String VERBOSE = "--verbose";
}

public class Equilibrator extends Thread implements Arguments{

    // Get Instance of ConfigurationSingleton

    private final ConfigurationSingleton CONFIG = ConfigurationSingleton.getInstance();

    protected final File pythonScript = new File("./python_modules/scanner.py");

    // PROCESSES ARRAYLIST
    public static CopyOnWriteArrayList<ProcessBuilder> pythonProcesses = new CopyOnWriteArrayList<>();

    // QUEUE OF NODES TO PROCESS
    public static CopyOnWriteArrayList<Node> ProcessQueue = new CopyOnWriteArrayList<>();

    // QUEUE OF PROCESSED NODES
    public static CopyOnWriteArrayList<Node> ProcessedQueue = new CopyOnWriteArrayList<>();

    private ExecutorService executor = Executors.newFixedThreadPool(CONFIG.getPythonProcessInstancersThreads());

    public Equilibrator() {

    }

    @Override
    public void run() {
        // TODO: Implement logic to clear and prepare queue
    }

    /**
     * Clears the ProcessQueue and creates the pythonProcesses queue
     */
    private void clearAndPrepareQueue() {
        for (Node node : ProcessQueue) {
            String currentArguments = (CONFIG.isVerboseMode()) ? Arguments.VERBOSE : Arguments.QUIET;
            pythonProcesses.add(new ProcessBuilder("python", pythonScript.getAbsolutePath(),
                    node.getIp(),currentArguments));
        }
        ProcessQueue.clear();
    }

    private void startInstacers() {
        if (!pythonProcesses.isEmpty()) {
            executor.execute(new PythonProcessInstancer());
            // TODO: Implement logic to check if all python processes have completed
        }
    }
}
