/**
 * 
 */
package planetInevitable.game;

import planetInevitable.EarthBound;
import planetInevitable.helpers.Affliction;
import planetInevitable.enums.afflictions;
import planetInevitable.enums.damageTypes;
import planetInevitable.enums.stat;
import planetInevitable.helpers.Modulate;

import java.util.HashMap;

/**
 * The Item class is the definition of the item. To instantiate it, create it's respective ItemInstance
 */
public class Item {
	public String name;
	public String help;
	public int price; // Sell-price is 3/4. 0 if unsellable.
	EarthBound.locale locale = EarthBound.locale.GENERIC; // Who is able to use this food? ( like how pu doesn't like american food )

	@SuppressWarnings("InnerClassMayBeStatic")
    public class ItemInstance {
		private static int maxID = 0;
		public int id;
		public Item type;

		ItemInstance(Item type){
			this.id = ++maxID;
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
		public PartyMember.equipmentSlot slot;

		public HashMap<stat, Modulate> statModulation = new HashMap<>();
		public HashMap<damageTypes, Modulate> damageResistances = new HashMap<>();
		HashMap<afflictions, Modulate> afflictionResistances = new HashMap<>();
		HashMap<Affliction.ailment, Modulate> ailmentModulations = new HashMap<>();

		public void defaultStats(){
			for (stat stat : stat.values()){
				statModulation.putIfAbsent(stat, new Modulate());
			}
			for (damageTypes dmgType : damageTypes.values()){
				damageResistances.putIfAbsent(dmgType, new Modulate());
			}
			for (afflictions affType : afflictions.values()){
				afflictionResistances.putIfAbsent(affType, new Modulate());
			}
		}
		public void setDefense(int def){
			statModulation.put(stat.DEFENSE, new Modulate(Modulate.types.ADD, def));
		}
	}
	static public class Weapon extends Item {
		public EarthBound.weaponType type;

		public damageTypes damageType;
		public float missChance;
		public PrefixModifier prefix;
		HashMap<stat, Modulate> statModulation = new HashMap<>();
		HashMap<afflictions, Float> afflictionInflictionChances = new HashMap<>();
		HashMap<Affliction.ailment, Modulate> ailmentModulations = new HashMap<>();

		public void defaultStats(){
			for (stat stat : stat.values()) {
				statModulation.putIfAbsent(stat, new Modulate());
			}
		}

		public void setDamage(int dmg){
			statModulation.put(stat.OFFENSE, new Modulate(Modulate.types.ADD, dmg));
		}
	}

	/**
	 * Like bombs and such
	 */

	static public class Offensive extends Item {

	}
}
