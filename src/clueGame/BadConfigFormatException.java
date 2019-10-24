//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

public class BadConfigFormatException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException() {
		System.out.println("Bad Configuration Format: Check load file format");
	}
	
	public BadConfigFormatException(String error) {
		System.out.println(error);
	}
}
