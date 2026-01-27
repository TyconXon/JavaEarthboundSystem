/**
 * 
 */
package planetInevitable;

import java.util.Scanner;

/**
 * 
 */
public class Main {

	public static void main(String[] args) {
		Scanner inputter = new Scanner(System.in);
		while(true) {
			String input = inputter.nextLine();
			if (input == "") {
				break;
			}
			EBSystem.say(input);
		}

		System.out.println("Terminated!");
		inputter.close();
	}

}
