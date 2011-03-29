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
	}

}
