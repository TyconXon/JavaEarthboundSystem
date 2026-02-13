/**
 * 
 */
package planetInevitable;

import jdk.jfr.Enabled;
import planetInevitable.helpers.Affliction;

import java.util.HashMap;

/**
 * The Item class is the definition of the item. To instantiate it, create it's respective ItemInstance
 */
public class Item {
	public String name;
	public String help;
	EarthBound.locale locale = EarthBound.locale.GENERIC; // Who is able to use this food? ( like how pu doesn't like american food )

	public class ItemInstance {
		private static int maxID = 0;
		int id;
		Item type;

		ItemInstance(Item type){
			this.id = maxID++;
			this.type = type;
		}
	}

	public ItemInstance instantiate(){
		return new ItemInstance(this);
	}


	static public class Food extends Item {
		int baseHealing = 30;
		int baseRegen = 0;
		int basePSI = 0;

		int uses = 0;
	}
	static public class Equipment extends Item {
		PartyMember.equipmentSlot slot;

		HashMap<EarthBound.damageTypes, EarthBound.modulate> damageResistances = new HashMap<>();
		HashMap<Affliction.afflictions, EarthBound.modulate> afflictionResistances = new HashMap<>();
		HashMap<Affliction.ailment, EarthBound.modulate> ailmentModulations = new HashMap<>();

	}
	static public class Weapon extends Item {
		EarthBound.weaponType type;

		EarthBound.damageTypes damageType;
		HashMap<Affliction.afflictions, Float> afflictionInflictionChances = new HashMap<>();
		HashMap<Affliction.ailment, EarthBound.modulate> ailmentModulations = new HashMap<>();

	}

	/**
	 * Like bombs and such
	 */

	static public class Offensive extends Item {

	}
}
