package utils;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import sun.management.OperatingSystemImpl;

public class JMXLookup {
	public JMXLookup() {

		//try {
		
			System.out.println("--->"+ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());

/*
			String host = "127.0.0.1";
			int port = 1234;
			HashMap map = new HashMap();
			String[] credentials = new String[2];
			credentials[0] = "admin";
			credentials[1] = "admin";
			map.put("jmx.remote.credentials", credentials);
			JMXConnector c = JMXConnectorFactory.newJMXConnector(
					createConnectionURL(host, port), map);
			c.connect();
			Object o = c.getMBeanServerConnection().getAttribute(
					new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
			CompositeData cd = (CompositeData) o;
			System.out.println(cd.get("committed"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
/*
	private static JMXServiceURL createConnectionURL(String host, int port)
			throws MalformedURLException {
		// return new
		// JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
		// service:jmx:rmi:///jndi/rmi://localhost/jmxrmi

		return new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:9999/server");
	}
*/
	public static void main(String[] args) {

		new JMXLookup();

	}

}
