import config.ConfigurationSingleton;

public class Main {
    public static void main(String[] args) {
            
        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        System.out.println(config.getAsciiArt());
        System.out.println("Expiration Time: " + config.getExpirationTime());
        System.out.println("Verbose Mode: " + config.isVerboseMode());
        System.out.println("Processes Volume: " + config.getProcessesVolume());
        
           
    }
}
