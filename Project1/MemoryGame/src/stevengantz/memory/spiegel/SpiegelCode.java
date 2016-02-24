package stevengantz.memory.spiegel;

import java.io.IOException;

import stevengantz.memory.spiegel.Input.InputReader;;

/**
 * 
 * 
 * @author: Steven Gantz
 * @date: 2/4/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file is an abstract resource containing static methods that were
 *       designed and required to be used by Dr. Spiegel.
 **/
public class SpiegelCode {

	/**
	 * This method will retrieve an integer from a user in a command line
	 * environment. If the integer is read successfully, the value is returned
	 * form the method. However, if the integer is not retrieved or an exception
	 * is thrown, -1 is returned.
	 * 
	 * @return -1 if bad, else int entered from user
	 */
	public static int getIntFromUser() {
		int ret = -1;
		try {
			ret = InputReader.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
