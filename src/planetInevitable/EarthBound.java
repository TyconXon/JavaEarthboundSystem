package planetInevitable;

import java.util.function.UnaryOperator;



public class EarthBound {

	static int default_delay = 30;

	public enum locale {
		GENERIC, EAGLELAND, DALAAM, ALIEN, WINTERS, SCARABA, SUMMERS, TENDA, ANCIENT, OTHERWORLDLY
	}

	public enum weaponType {
		SWORD, BAT, GUN, YOYO, PAN, ABSTRACT, GLOVE, AXE, PICKAXE, BOW, FLAIL, SLINGSHOT, STAFF, GENERIC, SPEAR, WAND,
	}


	/**
	 * Print out the given text with the RPG format.
	 * 
	 * @param text Given text to print
	 */
	public static void say(String text) {
		boolean escaped = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int delay = default_delay;

			// Escaped character logic
			int result = handleEscapeLogic(c, escaped);
			switch (result) {
			case 1: // skip and flag
				escaped = true;
				continue;
			case 2: // skip and unflag
				escaped = false;
				continue;
			}

			// Delay logic
			delay = modDelay(c, delay);

			// Print the char
			System.out.print(c);

			// then delay (because we want the reason of the delay to be visible, eg.
			// periods and commas)
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

		}

		// Clear the formatting
		System.out.println("\u001B[0m");
	}

	/**
	 * Print out the text all at once, but with formatting.
	 * 
	 * @param text
	 */
	public static void print(String text) {
		boolean escaped = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			// Escaped character logic
			int result = handleEscapeLogic(c, escaped);
			switch (result) {
			case 1: // skip and flag
				escaped = true;
				continue;
			case 2: // skip and unflag
				escaped = false;
				continue;
			}

			// Print out each character, with no delay.
			System.out.print(c);

		}

		// Clear the formatting
		System.out.println("\u001B[0m");
	}
	
	

	
	
	/*
	 * Private methods
	 */
	
	/**
	 * Returns a value corresponding to how the loop should handle the character.
	 * 
	 * @param c       The current character
	 * @param escaped If the escaped flag was already set in the last iteration
	 * @return 0: Keep iteration, 1: Skip and flag, 2: Skip and unflag
	 */
	private static int handleEscapeLogic(char c, boolean escaped) {

		if (escaped) { // If previous letter was a \
			if (c == '\\') {
				System.out.print(c);
				return 0; // Only instance where the iteration is kept.
			}
			try {
				escapedCharacterInterpreter(c);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return 2;

		} else if (c == '\\') { // Enter escaped logic
			return 1; // Skip and flag
		}

		// Non-escaped character
		return 0;
	}

	/**
	 * Modulate the delay based on the given character
	 * 
	 * @param c     The character
	 * @param delay current delay
	 * @return The new delay
	 */
	private static int modDelay(char c, int delay) {
		switch (c) {
		case ' ':
			delay *= 2;
			break;
		case '.':
			delay *= 5;
			break;
		case ',':
			delay *= 2.5;
			break;
		case '*':
			delay = 0;
			break;
		}
		return delay;
	}

	/**
	 * Does something to the console depending on the character for use with escaped
	 * characters
	 * 
	 * @param c The character
	 * @return whether the process should remain escaped
	 * @throws InterruptedException (because it uses sleep)
	 */
	private static void escapedCharacterInterpreter(char c) throws InterruptedException {
		switch (c) {
		case 'n':
			// New line
			System.out.println();
			Thread.sleep(100);
			break;
		case ' ':
			// Pause
			Thread.sleep(100);
			break;
		// Colors
		case 'r': // red
			System.out.print("\u001B[91m");
			break;
		case 'g': // green
			System.out.print("\u001B[92m");
			break;
		case 'y': // yellow
			System.out.print("\u001B[93m");
			break;
		case 'b': // blue
			System.out.print("\u001B[94m");
			break;
		case 'm': // magenta
			System.out.print("\u001B[95m");
			break;
		case 'c': // cyan
			System.out.print("\u001B[96m");
			break;
		case 'w': // white
			System.out.print("\u001B[97m");
			break;

		// Formatting
		case 'S': // strikethru
			System.out.print("\u001B[9m");
			break;
		case 's': // STRONG (bold
			System.out.print("\u001B[1m");
			break;
		case 'u': // Underline
			System.out.print("\u001B[4m");
			break;
		case 'U': // faint
			System.out.print("\u001B[21m");
			break;
		case 'i': // italics
			System.out.print("\u001B[3m");
			break;
		case 'I': // Inverted
			System.out.print("\u001B[7m");
			break;
		case 'f': // framed
			System.out.print("\u001B[51m");
			break;

		// Background Colors
		case 'R':
			System.out.print("\u001B[41m");
			break;
		case 'G':
			System.out.print("\u001B[42m");
			break;
		case 'Y':
			System.out.print("\u001B[43m");
			break;
		case 'B':
			System.out.print("\u001B[44m");
			break;
		case 'M':
			System.out.print("\u001B[45m");
			break;
		case 'C':
			System.out.print("\u001B[46m");
			break;
		case 'W':
			System.out.print("\u001B[47m");
			break;

		// Default
		case 'd':
			System.out.print("\u001B[0m\u001B[22m\u001B[24m\u001B[27m");
			break;
		default:
			break;
		}
	}
}
