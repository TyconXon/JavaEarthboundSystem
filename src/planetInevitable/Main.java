/**
 * 
 */
package planetInevitable;

import planetInevitable.helpers.Stats;

import java.util.Objects;
import java.util.Scanner;

/**
 * 
 */
public class Main {

	static void nothing(){

		var bracelet = new Item.Equipment();
		bracelet.name = "Cool bracelet";
		bracelet.slot = PartyMember.equipmentSlot.NECK;
		bracelet.damageResistances.put(EarthBound.damageTypes.BITE, new EarthBound.modulate(EarthBound.modulate.types.ADD, -5));
		var braceletInstance = bracelet.instantiate();

		Stats sophieStats = new Stats();
		sophieStats.maxCarry = 25;

		PSI[] sophieKnowledge = {};

		var sophie = new PartyMember("Sophie", sophieStats, sophieKnowledge);
		sophie.equipItem(braceletInstance);
	}

	static void main(String[] args) {

		nothing();

		var inputter = new Scanner(System.in);
		while(true) {
			String input = inputter.nextLine();
			if (Objects.equals(input, "")) {
				break;
			}
			EarthBound.say(input);
		}

		System.out.println("Terminated!");
		inputter.close();
	}

}
