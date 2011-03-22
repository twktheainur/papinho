/**
 * 
 */
package bootstrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author twk
 * 
 */
public class Bootstrapper {

	List<String> hosts;
	private int N;
	private int d;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean local = false;
		String a;
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		System.out.println("Run locally?");
		try {
			a = in.readLine();
			if (a.equals("yes")) {
				local = true;
			}
			System.out.println("What is N?");
			a = in.readLine();
			if (local) {
				while (!a.equals("p")) {

				}
			}
		}

		catch (Exception e) {
		}

	}
}
