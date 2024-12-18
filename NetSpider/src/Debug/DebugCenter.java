package Debug;

import config.ConfigurationSingleton;

public class DebugCenter {
    private static ConfigurationSingleton config = ConfigurationSingleton.getInstance();
    public static void debug(String message) {
        if (config.isDebugMode()) {
            System.out.println("[DEBUG] " + message);
        }
    }
}
