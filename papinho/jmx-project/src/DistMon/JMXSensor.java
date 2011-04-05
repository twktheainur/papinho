package DistMon;

import java.lang.management.MemoryMXBean;

import java.lang.management.*;

public class JMXSensor {
	
	private MemoryMXBean memory;
	private OperatingSystemMXBean operatingSystem;
	
	public JMXSensor() {
		memory = ManagementFactory.getMemoryMXBean();
		operatingSystem = ManagementFactory.getOperatingSystemMXBean();
	}
	public long getMemory(){
		return memory.getHeapMemoryUsage().getUsed();
	}
}
