package processor;

import java.io.File;
import java.util.ArrayList;

import entities.Node;

// PROCESADOR DE RESULTADOS. DEBE DE ESCRIBIR RESULTADOS EN UN FICHERO (hilo)
// También tiene que tener un método estático para escribir ips en un fichero , separadas por algún tipo de regex


enum ProcessorMode {
    WriteReport,
    WriteRawIp
}
public class Processor {

    // hacer javadoc
   public static void writeResults(ArrayList<Node> nodes , File file) {
       // Writes the results with a new thread in the constructor
       new ReportWriter(nodes, file);
   }


}
