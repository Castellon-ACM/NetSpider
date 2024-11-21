package processor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// RECOGERÁ TAMBIEN EL OUTPUT DEL PROCESO DE PYTHON
// DEBE DE PARSEAR EL JSON QUE DEVUELVE EL JEFAZO DE FELIX EN SU PROCESO DE PYTHON Y CONVERTIRLO A UN
// OBJETO NODE. UNA VEZ SE CONVIERTA SE GUARDARÁ EN EL PROCESSED QUEUE DEL EQUILIBRATOR
//  ********************SE HARÁ DE MANERA ASÍNCRONA*********************
public class PythonOutputParser extends Thread {
    private Process pythonProcess;

    /**
     * Receives a Process object and starts the thread
     *
     * @param pythonProcess
     */
    public PythonOutputParser(Process pythonProcess) {
        this.pythonProcess = pythonProcess;
    }


    @Override
    public void run() {
        if (pythonProcess != null) {
            StringBuilder output = outputReader(pythonProcess);
            // AQUI SE DEBE USAR EL JSON QUE RECIBIO EL JEFE FELIX Y CONVERTIRLO A UN OBJETO NODE

            

            // LUEGO DE CONVERTIRLO SE GUARDA EN EL PROCESSED QUEUE DEL EQUILIBRATOR
            // SI EL OUTPUT ES NULO, SE DEBE DETENER EL PROCESO
            // SI EL OUTPUT ES NO NULO, SE DEBE CREAR UN NUEVO NODE Y GUARDARLO EN EL PROCESSED QUEUE DEL EQUILIBRATOR

        }
    }

    private StringBuilder outputReader(Process process) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder;
    }
}
