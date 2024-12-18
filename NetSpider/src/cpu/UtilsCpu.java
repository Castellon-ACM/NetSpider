package cpu;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class UtilsCpu {
    private static final OperatingSystemMXBean osBean = (OperatingSystemMXBean)  ManagementFactory.getOperatingSystemMXBean();

    /**
     * Returns the cpu load in real time
     * @return The percentage of cpu used in real time
     */
    public static double getCpuLoad() {
        return osBean.getCpuLoad() * 100;
    }
}
