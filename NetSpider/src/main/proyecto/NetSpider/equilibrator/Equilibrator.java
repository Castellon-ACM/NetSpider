package main.proyecto.NetSpider.equilibrator;
import main.proyecto.NetSpider.config.Configuration;
import main.proyecto.NetSpider.data_controller.Migrable;
import main.proyecto.NetSpider.entities.Node;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

interface Arguments {
    public final String QUIET = "--quiet";
    public final String VERBOSE = "--verbose";
}
public class Equilibrator extends Thread implements Migrable,Arguments{

    protected final File pythonScript = new File("./python_modules/scanner.py");

    // PROCESSES ARRAYLIST
    public static CopyOnWriteArrayList<ProcessBuilder> pythonProcesses = new CopyOnWriteArrayList<>();

    // QUEUE OF NODES TO PROCESS
    public static CopyOnWriteArrayList<Node> ProcessQueue = new CopyOnWriteArrayList<>();

    // QUEUE OF PROCESSED NODES
    public static CopyOnWriteArrayList<Node> ProcessedQueue = new CopyOnWriteArrayList<>();

    public Equilibrator() {

    }

    /**
     * Clears the ProcessQueue and creates the pythonProcesses queue
     */
    public void clearAndPrepareQueue() {
        for (Node node : ProcessQueue) {
            String currentArguments = (Configuration.verboseMode) ? Arguments.VERBOSE : Arguments.QUIET;
            pythonProcesses.add(new ProcessBuilder("python", pythonScript.getAbsolutePath(),
                    node.getIp(),currentArguments));
        }
        ProcessQueue.clear();
    }



    /**
     * Method to import nodes to the class
     *
     * @param nodes net nodes
     */
    @Override
    public void saveNodes(ArrayList<Node> nodes) {
        // IMPLEMENTAR
    }

    /**
     * Method to export nodes.
     * It returns the nodes in the CopyOnWriteArrayList
     *
     * @return nodes
     */
    @Override
    public ArrayList<Node> exportNodes() {
        // IMPLEMENTAR
        return null;
    }
}
