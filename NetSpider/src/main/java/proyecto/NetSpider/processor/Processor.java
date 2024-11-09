package proyecto.NetSpider.processor;

import proyecto.NetSpider.entities.Node;

import java.util.ArrayList;

// PROCESADOR DE RESULTADOS. DEBE DE ESCRIBIR RESULTADOS EN UN FICHERO (hilo)
// También tiene que tener un método estático para escribir ips en un fichero , separadas por algún tipo de regex


enum ProcessorMode {
    WriteReport,
    WriteRawIp
}
public class Processor {
    
    
    public Processor(ArrayList<Node> nodes , ProcessorMode mode) {
        
    }

}
