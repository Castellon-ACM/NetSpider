package config;

// CONFIGURACIÓN DEL PROYECTO

public class ConfigurationSingleton {

    private static ConfigurationSingleton instance = null;

    private int expirationTime; // In seconds
    private boolean verboseMode;
    private int processesVolume; // Number of processes that PythonProcessInstancer will get from the queue
    private double maxCpuLoad;
    private int threadSleepCModuleInstancer; // In milliseconds
    private int equilibratorThreads; // Thread pool size for Equilibrator (in MILISECONDS)
    private int PythonProcessInstancersThreads; // Thread pool size for PythonProcessInstancer
    private int equilibratorInterval; // Intervall in seconds between each equilibration process
    private int ipScannerTimeout; // Timeout in milliseconds
    private int dataControllerPeriod; // Interval in seconds between each dataController execution


    private String asciiArt = """
                        
                         ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠘⢦⠀⠀⠀⠀⠀⠀⠀⠀⢠⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠈⢳⡀⠀⠀⠀⠀⠀⣠⣾⠙⢦⣀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠔⠂
            ⠀⠀⠀⠀⠀⠀⠀⢸⡿⣗⡲⠶⠖⠋⣡⣯⡀⠀⠈⠉⠓⠒⠲⢶⣶⡖⠋⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⢀⡼⠁⡿⢯⠙⠛⠋⣹⡇⠙⠲⢤⣀⣀⡤⠖⢫⠏⠀⠀⠀⠀⠀
            ⠈⠙⠓⠶⢤⣴⣋⢀⣰⠃⠈⣿⡛⠉⣽⠙⠲⢤⡤⠞⢻⠀⢀⡏⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠈⢯⡙⢳⡲⢴⣇⣙⣄⣇⡤⠚⠉⡇⠀⢸⠀⢸⡇⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⣷⠀⣧⠀⣇⡼⢻⢿⡲⠤⣄⣧⠀⠸⡆⠈⣧⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⢠⡇⣠⣿⡊⠙⢦⡞⠀⠳⣴⠋⠉⢉⡷⠿⠤⣌⣦⡀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⣠⢾⣋⠁⠀⠙⢦⢸⡟⠉⠉⠙⣆⢠⠏⣠⠖⠋⠉⠉⠉⠓⠲⠤⡄
            ⠀⠀⣠⠖⠋⠁⠀⠈⠙⠦⡀⠈⣿⣠⠤⠴⠶⠾⢿⣠⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣆⡏⠀⠀⢀⣀⣀⣀⣻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡷⠚⠉⠉⠁⠀⠀⠀⠙⣆⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢧⡀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
              NetSpider - A network crawler and IP address scanner.  
              Authors: JaggerFelix, SuperManuel , HyperCastellon.
                        """;


    public int getDataControllerPeriod() {
        return dataControllerPeriod;
    }

    private ConfigurationSingleton() {
        this.expirationTime = 50;
        this.verboseMode = true;
        this.processesVolume = 10;
        this.maxCpuLoad = 80;
        this.threadSleepCModuleInstancer = 1000;
        this.equilibratorThreads = 1;
        this.equilibratorInterval = 2000;
        this.ipScannerTimeout = 100;
        this.dataControllerPeriod = 10;
    }


    public static ConfigurationSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigurationSingleton();
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

    public String getAsciiArt() {
        return asciiArt;
    }

    public void setAsciiArt(String asciiArt) {
        this.asciiArt = asciiArt;
    }

    public double getMaxCpuLoad() {
        return maxCpuLoad;
    }

    public int getThreadSleepCModuleInstancer() {
        return threadSleepCModuleInstancer;
    }

    public int getPythonProcessInstancersThreads() {
        return PythonProcessInstancersThreads;
    }

    public int getEquilibratorThreads() {
        return equilibratorThreads;
    }

    public int getEquilibratorInterval() {
        return equilibratorInterval;
    }

    public int getIpScannerTimeout() {
        return ipScannerTimeout;
    }
}
