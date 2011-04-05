package bootstrap;

import java.util.ArrayList;
import java.util.List;

import utils.CmdLineParser;
import DistMon.DistributedMonitor;
import DistMon.DistributedMonitorView;

public class Main {

	/**
	 * @param args
	 */
	private static void printUsage(String caller) {
		System.err.println("From "+caller);
		System.err
				.println("Usage: distmon --uri JMS_URI [-b {-u,--user} username {-p,--password} password {-n,--nodes-number} num_nodes ({-a,--arity}|{-d,--depth})]|\n"
						+ "[{-p,--parent} parent_topic {-c,--children} child_1,child_2,...]");
		System.exit(2);
	}

	public static String URI;

	public static void main(String[] args) throws Exception{
		System.out.println("Booting...");
		CmdLineParser parser = new CmdLineParser();

		CmdLineParser.Option bootstrap = parser.addBooleanOption('b',
				"bootstrap");
		CmdLineParser.Option uri = parser.addStringOption('i', "uri");
		CmdLineParser.Option user = parser.addStringOption('u', "user");
		CmdLineParser.Option pass = parser.addStringOption('w', "password");
		CmdLineParser.Option nnodes = parser.addIntegerOption('n',
				"nodes-number");
		CmdLineParser.Option a = parser.addIntegerOption('a', "arity");
		CmdLineParser.Option d = parser.addIntegerOption('d', "depth");

		CmdLineParser.Option parent = parser.addStringOption('p', "parent");
		CmdLineParser.Option nameArg = parser.addStringOption('m', "name");

		try {
			parser.parse(args);
		} catch (CmdLineParser.OptionException e) {
			System.err.println(e.getMessage());
			printUsage("Parse error!");
		}

		Boolean isBootstrap = (Boolean) parser.getOptionValue(bootstrap,
				Boolean.FALSE);
		String suri = (String) parser.getOptionValue(uri);
		if (suri == null) {
			printUsage("No uri specified!");
		}
		URI = suri;// "tcp://localhost:3035/";
		if (isBootstrap) {
			String uname = (String) parser.getOptionValue(user);
			String upass = (String) parser.getOptionValue(pass);
			Integer arity = (Integer) parser.getOptionValue(a);
			Integer depth = (Integer) parser.getOptionValue(d);
			Integer nn = (Integer) parser.getOptionValue(nnodes);

			if (nn == null
					|| (nn != null && ((arity == null && depth == null)))) {
				printUsage("Arity or depth missing!");
			}
			int val;
			if (arity != null) {
				val = arity;
			} else {
				val = depth;
			}

			String[] ips = parser.getRemainingArgs();
			Bootstrap.bootstrap(URI, uname, upass, nn, val, arity!=null, ips);
		} else {
			String pt = (String) parser.getOptionValue(parent);
			String name = (String) parser.getOptionValue(nameArg);
			String[] ct = parser.getRemainingArgs();
			List<String> c = null;
			if (ct.length > 0) {
				c = new ArrayList<String>();
				for (String s : ct) {
					c.add(s);
				}
			}
			System.out.println("Runny Run!!!");
			DistributedMonitor dm = new DistributedMonitor(name,pt, c);
			DistributedMonitorView dmv = new DistributedMonitorView(dm);
			dmv.setSize(300, 300);
			//dmv.setVisible(true);
			dm.start();
			System.out.println("Yawn...");
	        //Thread.sleep(Long.MAX_VALUE);
		}
	}
}
