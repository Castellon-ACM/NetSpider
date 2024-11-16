package main.proyecto.NetSpider.config;
// CONFIGURACIÓN DEL PROYECTO

public class Configuration {

    private static Configuration instance = null;

    private int expirationTime;
    private boolean verboseMode;
    private int processesVolume;

    private Configuration() {
        this.expirationTime = 50;
        this.verboseMode = true;
        this.processesVolume = 20;
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isVerboseMode() {
        return verboseMode;
    }

    public void setVerboseMode(boolean verboseMode) {
        this.verboseMode = verboseMode;
    }

    public int getProcessesVolume() {
        return processesVolume;
    }

    public void setProcessesVolume(int processesVolume) {
        this.processesVolume = processesVolume;
    }

  
}
