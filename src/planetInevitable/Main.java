/**
 * 
 */
package planetInevitable;

import planetInevitable.game.Action;
import planetInevitable.game.Item;
import planetInevitable.game.PSI;
import planetInevitable.game.PartyMember;
import planetInevitable.helpers.Stats;
import planetInevitable.enums.damageTypes;
import planetInevitable.enums.stat;
import planetInevitable.helpers.Modulate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * 
 */
public class Main {

	static PartyMember sophie;

	static void nothing(){

		var bracelet = new Item.Equipment();
		bracelet.defaultStats();
		bracelet.name = "Cool BRACELET";
		bracelet.slot = PartyMember.equipmentSlot.NECK;
		bracelet.damageResistances.put(damageTypes.BITE, new Modulate(Modulate.types.ADD, (float) -0.5));
		bracelet.statModulation.put(stat.IQ, new Modulate(Modulate.types.ADD, 99));
		var braceletInstance = bracelet.instantiate();

        var gayclet = new Item.Equipment();
		gayclet.defaultStats();
        gayclet.name = "Cool gayclet";
        gayclet.slot = PartyMember.equipmentSlot.NECK;
        gayclet.damageResistances.put(damageTypes.BITE, new Modulate(Modulate.types.ADD, (float) -0.1));
		gayclet.statModulation.put(stat.SPEED, new Modulate(Modulate.types.ADD, 99));

        var gaycletInstance = gayclet.instantiate();

        var swordOfQueens = new Item.Weapon();
		swordOfQueens.defaultStats();
        swordOfQueens.name = "Sword of Queens";
        swordOfQueens.type = EarthBound.weaponType.SWORD;
        swordOfQueens.damageType = damageTypes.METAL;
        var soqInstance = swordOfQueens.instantiate();

        var boringSword = new Item.Weapon();
		boringSword.defaultStats();
        boringSword.name = "Boring Sword";
        boringSword.type = EarthBound.weaponType.SWORD;
        boringSword.damageType = damageTypes.METAL;
        var bsInstance = boringSword.instantiate();

		Stats sophieStats = new Stats();
		sophieStats.defaultStats();
		sophieStats.set(stat.MAX_CARRY, 25);
		sophieStats.set(stat.DEFENSE, 1);
		sophieStats.set(stat.OFFENSE, 1);


		HashSet<PSI> sophieKnowledge = new HashSet<>();

		sophie = new PartyMember("Sophie", sophieStats, sophieKnowledge);
        sophie.knownLocales.add(EarthBound.locale.GENERIC);
        sophie.validWeaponTypes.add(EarthBound.weaponType.SWORD);


		System.out.println(sophie.getEffectiveStats());

		//Test equipping
        sophie.equipItem(braceletInstance);
		System.out.println(sophie.getEffectiveStats().damageTypeWeaknesses);

        sophie.equipItem(soqInstance);
		//Test replacing equipment
		sophie.equipItem(gaycletInstance);
		System.out.println(sophie.getEffectiveStats());

		sophie.equipItem(bsInstance);

		//Test Reequipping
		sophie.equipItem(gaycletInstance);
		sophie.equipItem(bsInstance);


	}

	static void main(String[] args) {

		nothing();

		Action.Hit hurtself = new Action.Hit();
		hurtself.targets.add(sophie);
		hurtself.invoker = sophie;

		var inputter = new Scanner(System.in);
		do {
			String input = inputter.nextLine();
			switch(input) {
				case "h":
					hurtself.execute();
					break;
				case "i":
					sophie.addExperience(25);
					break;
				case "d":
					EarthBound.print(sophie.getEffectiveStats().toString());
				default:
					EarthBound.say(input);
					break;
			}

			EarthBound.print(sophie.toString());
			if(input.equals("x")) {
				break;
			}

		}while(true);

		System.out.println("Terminated!");
		inputter.close();
	}

}
