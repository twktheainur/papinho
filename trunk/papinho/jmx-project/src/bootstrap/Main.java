package bootstrap;

import utils.CmdLineParser;

public class Main {

	/**
	 * @param args
	 */
	private static void printUsage() {
		System.err
				.println("Usage: distmon [-d,--debug] [{-v,--verbose}] [{--alt}] [{--name} a_name]\n"
						+ "                  [{-s,--size} a_number] [{-f,--fraction} a_float] [a_nother]");
	}

	public static void main(String[] args) {
		CmdLineParser parser = new CmdLineParser();
		try {
			parser.parse(args);
		} catch (CmdLineParser.OptionException e) {
			System.err.println(e.getMessage());
			printUsage();
			System.exit(2);
		}
		CmdLineParser.Option bootstrap = parser.addBooleanOption('b', "bootstrap");
		CmdLineParser.Option distribute = parser.addBooleanOption('d', "distribute");
		CmdLineParser.Option adminurl = parser.addStringOption("admin_uri");
		CmdLineParser.Option user = parser.addStringOption("user");
		CmdLineParser.Option pass = parser.addStringOption("password");
		
		CmdLineParser.Option monitor = parser.addStringOption('m', "monitor");
		CmdLineParser.Option parent = parser.addStringOption('p', "parent");
		CmdLineParser.Option children = parser.addStringOption('c', "children");
		
		
		Bootstrap.bootstrap("tcp://localhost:3035/","admin", "openjms", 50, 5, false, null);
	}

}
