package planetInevitable;

public class EBSystem {

	/**
	 * Print out the given text with the RPG format.
	 * @param text Given text to print
	 */
	public static void say(String text) {
		Boolean escaped = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int delay = 25;

			try {

				//Escape logic
				if (c == '\\') {
					//Enter escaped logic
					escaped = true;
					continue;
				}
				//If previous letter was a \
				if (escaped) {
					escapedCharacterInterpreter(c);
					// If we are still escaped, exit condition and continue loop
					if (escaped) {
						escaped = false;
						continue;
					}
				} 	
				

				//Delay logic
				delay += modDelay(c, delay);

				// Print the char, then delay (because we want the reason of the delay to be visible, eg. periods and commas)
				System.out.print(c);
				Thread.sleep(delay);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		//Clear the formatting
		System.out.println("\u001B[0m");
	}
	public static void print(String text) {
		Boolean escaped = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			

				//Escape logic
				if (c == '\\') {
					//Enter escaped logic
					escaped = true;
					continue;
				}
				//If previous letter was a \
				if (escaped) {
					try {
						escapedCharacterInterpreter(c);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					// If we are still escaped, exit condition and continue loop
					if (escaped) {
						escaped = false;
						continue;
					}
				} 	
				
				System.out.print(c);

			
		}

		//Clear the formatting
		System.out.println("\u001B[0m");
	}

	/**
	 * Modulate the delay based on the given character
	 * @param c The character
	 * @param delay current delay
	 * @return The new delay
	 */
	private static int modDelay(char c, int delay) {
		switch (c) {
			case ' ':
				delay += 10;
				break;
			case '.':
				delay += 500;
				break;
			case ',':
				delay += 100;
				break;
			case '*':
				delay = 0;
				break;
		}
		return delay;
	}
	
	/**
	 * Does something to the console depending on the character
	 * for use with escaped characters
	 * @param c The character
	 * @return whether the process should remain escaped
	 * @throws InterruptedException (because it uses sleep)
	 */
	private static boolean escapedCharacterInterpreter(char c) throws InterruptedException {
		switch (c) {
			case '\\':
				// Print a normal \
				return false;
				//break;
			case 'n':
				// New line
				System.out.println();
				Thread.sleep(100);
				break;
			case ' ':
				// Pause
				Thread.sleep(100);
				break;
			//Colors
			case 'r': //red
				System.out.print("\u001B[91m");
				break;
			case 'g': //green
				System.out.print("\u001B[92m");
				break;
			case 'y': //yellow
				System.out.print("\u001B[93m");
				break;
			case 'b': //blue
				System.out.print("\u001B[94m");
				break;
			case 'm': //magenta
				System.out.print("\u001B[95m");
				break;
			case 'c': //cyan
				System.out.print("\u001B[96m");
				break;
			case 'w': //white
				System.out.print("\u001B[97m");
				break;
				
			//Formatting
			case 'S': //strikethru
				System.out.print("\u001B[9m");
				break;
			case 's': //STRONG (bold
				System.out.print("\u001B[1m");
				break;
			case 'u': //Underline
				System.out.print("\u001B[4m");
				break;
			case 'U': //faint
				System.out.print("\u001B[21m");
				break;
			case 'i': //italics
				System.out.print("\u001B[3m");
				break;
			case 'I': //Inverted
				System.out.print("\u001B[7m");
				break;
			case 'f': //framed
				System.out.print("\u001B[51m");
				break;
			
			
			
			//Background Colors
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
				
			//Default
			case 'd':
				System.out.print("\u001B[0m\u001B[22m\u001B[24m\u001B[27m");
				break;
			default:
				break;
		}
		return true;
	}
}
