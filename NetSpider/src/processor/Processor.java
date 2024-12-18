package processor;

import java.io.File;
import java.util.ArrayList;

import entities.Node;

public class Processor {


   public static void writeResults(ArrayList<Node> nodes , File file) {
       // Writes the results with a new thread in the constructor
       new ReportWriter(nodes, file, file);
   }


}
