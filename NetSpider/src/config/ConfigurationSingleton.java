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
    private int CProcessInstancersThreads; // Thread pool size for PythonProcessInstancer
    private int equilibratorInterval; // Intervall in seconds between each equilibration process
    private int ipScannerTimeout; // Timeout in milliseconds
    private int dataControllerPeriod; // Interval in seconds between each dataController execution
    private SCAN_TYPE ipScannerType; // Type of IP scan (FULL or PARTIAL)
    private String ipRange; // IP range to scan (e.g., 192.168.0)
    private int ipScannerSecondsInterval; // Number of seconds
    private boolean debugMode; // Debug mode
    private int shutdownTimeout; // Timeout in seconds for shutdown process

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
        this.CProcessInstancersThreads = 1;
        this.equilibratorInterval = 10; // Equilibrator interval in seconds
        this.ipScannerTimeout = 30; // To increase the speed of the IP scanner, decrease this value
        this.dataControllerPeriod = 10; // In seconds
        this.ipScannerType = SCAN_TYPE.PARTIAL;
        this.ipRange = "192.168.1";
        this.ipScannerSecondsInterval = 60;
        this.debugMode = true;
        this.shutdownTimeout = 5;
    }


    public static ConfigurationSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigurationSingleton();
        }
        return instance;
    }

    public SCAN_TYPE getIpScannerType() {
        return ipScannerType;
    }

    public String getIpRange() {
        return ipRange;
    }

    public int getExpirationTime() {
        return expirationTime * 1000;
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

    public int getCProcessInstancersThreads() {
        return CProcessInstancersThreads;
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

    public int getIpScannerSecondsInterval() {
        return ipScannerSecondsInterval;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public int getShutdownTimeout() {
        return shutdownTimeout;
    }
}
