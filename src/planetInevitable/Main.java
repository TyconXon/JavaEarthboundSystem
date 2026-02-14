/**
 * 
 */
package planetInevitable;

import planetInevitable.helpers.Stats;
import planetInevitable.helpers.stat;

import java.util.Objects;
import java.util.Scanner;

/**
 * 
 */
public class Main {

	static void nothing(){

		var bracelet = new Item.Equipment();
		bracelet.defaultStats();
		bracelet.name = "Cool BRACELET";
		bracelet.slot = PartyMember.equipmentSlot.NECK;
		bracelet.damageResistances.put(EarthBound.damageTypes.BITE, new EarthBound.modulate(EarthBound.modulate.types.ADD, -5));
		bracelet.statModulation.put(stat.IQ, new EarthBound.modulate(EarthBound.modulate.types.ADD, 99));
		var braceletInstance = bracelet.instantiate();

        var gayclet = new Item.Equipment();
		gayclet.defaultStats();
        gayclet.name = "Cool gayclet";
        gayclet.slot = PartyMember.equipmentSlot.NECK;
        gayclet.damageResistances.put(EarthBound.damageTypes.BITE, new EarthBound.modulate(EarthBound.modulate.types.ADD, -5));
		gayclet.statModulation.put(stat.SPEED, new EarthBound.modulate(EarthBound.modulate.types.ADD, 99));

        var gaycletInstance = gayclet.instantiate();

        var swordOfQueens = new Item.Weapon();
		swordOfQueens.defaultStats();
        swordOfQueens.name = "Sword of Queens";
        swordOfQueens.type = EarthBound.weaponType.SWORD;
        swordOfQueens.damageType = EarthBound.damageTypes.METAL;
        var soqInstance = swordOfQueens.instantiate();

        var boringSword = new Item.Weapon();
		boringSword.defaultStats();
        boringSword.name = "Boring Sword";
        boringSword.type = EarthBound.weaponType.SWORD;
        boringSword.damageType = EarthBound.damageTypes.METAL;
        var bsInstance = boringSword.instantiate();

		Stats sophieStats = new Stats();
		sophieStats.defaultStats();
		sophieStats.set(stat.MAX_CARRY, 25);


		PSI[] sophieKnowledge = {};

		var sophie = new PartyMember("Sophie", sophieStats, sophieKnowledge);
        sophie.knownLocales.add(EarthBound.locale.GENERIC);
        sophie.validWeaponTypes.add(EarthBound.weaponType.SWORD);

		System.out.println(sophie.getEffectiveStats());

		//Test equipping
        sophie.equipItem(braceletInstance);
		System.out.println(sophie.getEffectiveStats());

        sophie.equipItem(soqInstance);
		System.out.println(sophie.getEffectiveStats());
		System.out.println(sophie.getEffectiveStats());
		System.out.println(sophie.getEffectiveStats());
		System.out.println(sophie.getEffectiveStats());
		System.out.println(sophie.getEffectiveStats());


		//Test replacing equipment
		sophie.equipItem(gaycletInstance);
		sophie.equipItem(bsInstance);

		//Test Reequipping
		sophie.equipItem(gaycletInstance);
		sophie.equipItem(bsInstance);
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
