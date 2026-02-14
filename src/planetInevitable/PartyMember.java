package planetInevitable;

import java.util.*;

import planetInevitable.helpers.*;

/**
 * 
 */
public class PartyMember {

	public boolean instantDamage;

	public enum equipmentSlot {
		HEAD, BODY, LEGS, SHOES, ARMS, NECK, HANDS,
	}


	String name; // Parameter

	Stats stats; // Parameter
	Status status;
	
	ArrayList<Item.ItemInstance> inventory = new ArrayList<>();
	PSI[] knowledge; // Parameter
	
	public HashSet<equipmentSlot> validEquipmentSlots;
	HashMap<equipmentSlot, Item.Equipment.ItemInstance> equipment = new HashMap<>();

	public HashSet<EarthBound.weaponType> validWeaponTypes = new HashSet<>();
	public int equippedWeaponID;
	public Optional<Item.ItemInstance> getWeapon(){
		return inventory.stream().filter((itemInstance -> {return itemInstance.id == this.equippedWeaponID;})).findFirst();
	}


	public HashSet<EarthBound.locale> knownLocales = new HashSet<>();

	Item.Food[] preferredFoods;

	public PartyMember(String name, Stats stats, PSI[] knowledge) {
		this.name = name;
		this.stats = stats;
		this.knowledge = knowledge;
		validEquipmentSlots = new HashSet<>(List.of(new equipmentSlot[]{equipmentSlot.HEAD, equipmentSlot.HANDS, equipmentSlot.BODY, equipmentSlot.ARMS, equipmentSlot.LEGS, equipmentSlot.NECK, equipmentSlot.SHOES}));
	}

	public int getExperience() {
		return stats.experience;
	}
	public void setExperience(int xp) {
		stats.experience = xp;

		int prevLevel = stats.level;
		while (stats.getLevelFromXP(xp) >= stats.level) {
			this.levelUp();
		}

		if (stats.level > prevLevel) {
			int levelsGained = stats.level - prevLevel;
			EarthBound.say(this.name+" leveled up! Gained a total of "+levelsGained+" level(s).");
		}
	}

	private void levelUp() {
		stats.level += 1;
	}

	public void addExperience(int xp){
		this.setExperience(this.getExperience() + xp);
	}

	public boolean giveItem(Item.ItemInstance item){
		if (this.inventory.size() < this.stats.get(stat.MAX_CARRY)) {
			this.inventory.add(item);
			return true;
		}
		return false;
	}

	public Item.ItemInstance removeItem(int index){
		return this.inventory.remove(index);
	}
	public boolean removeItem(Item.ItemInstance item){
		return this.inventory.remove(item);
	}

	public EarthBound.returnCode equipItem(Item.ItemInstance item) {

		// Equip if you don't have the item
		if (!this.inventory.contains(item)){
			if (!this.giveItem(item)) {return EarthBound.returnCode.OVERENCUMBERED;};
		}

		// Wrong Locale
		if ( !knownLocales.contains( item.type.locale ) ){
			return EarthBound.returnCode.WRONG_LOCALE;
		}

		if (item.type instanceof Item.Equipment type){
			// Cannot equip
			if ( !validEquipmentSlots.contains( type.slot ) ){
				return EarthBound.returnCode.INCOMPATIBLE;
			}
			if (this.equipment.containsKey(type.slot)){
				this.unequip(this.equipment.get(type.slot));
			}


			this.equipment.putIfAbsent( type.slot, item);
			EarthBound.print("\\b" + this.name + "\\d has equipped \\g" + type.name + "\\d onto their \\y" + type.slot + "\\d!");
			return EarthBound.returnCode.SUCCESS;

		}else if(item.type instanceof Item.Weapon type) {
			// Cannot equip
			if (!validWeaponTypes.contains(type.type)) {
				return EarthBound.returnCode.INCOMPATIBLE;
			}
			// Has something else equipped
			if (this.getWeapon().isPresent()){
				this.unequip(getWeapon().get());
			}

			this.equippedWeaponID = item.id;
			EarthBound.print("\\b" + this.name + "\\d has equipped \\g" + type.name + "\\d!");
			return EarthBound.returnCode.SUCCESS;
		}
		return EarthBound.returnCode.WHAT;

	}

	public void unequip(Item.ItemInstance equippable) {
		if (equippable.type instanceof Item.Equipment type){
			this.equipment.remove(type.slot);
		} else if (equippable.type instanceof Item.Weapon type) {
			this.equippedWeaponID = -1;
		}

		EarthBound.print("\\b" + this.name + "\\d has unequipped \\r" + equippable.type.name + "\\d!");
	}

	public Stats getEffectiveStats() {
		var effective = new Stats();
		effective.value.putAll(this.stats.value);
		effective.damageTypeResistances = this.stats.damageTypeResistances;
		effective.damageTypeMultiplier = this.stats.damageTypeMultiplier;
		effective.afflictionResistances = this.stats.afflictionResistances;

		//Equipment
		// For each valid equipment slot
		for (equipmentSlot slot : this.validEquipmentSlots) {
			// If nothing is there, skip.
			if (!this.equipment.containsKey(slot)) continue;

			// Set type variable
			if (this.equipment.get(slot).type instanceof Item.Equipment type) {
				// For each statmod
				for (stat curStat : type.statModulation.keySet())
					effective.set(curStat, Math.round((Float) type.statModulation.get(curStat).mod(this.stats.get(curStat))) );
				for (EarthBound.damageTypes curType : type.damageResistances.keySet())
					effective.damageTypeResistances.put(curType, (Float) type.damageResistances.get(curType).mod(effective.damageTypeResistances.get(curType)));
				for (var curAffl : type.afflictionResistances.keySet())
					effective.afflictionResistances.put(curAffl, (Float) type.afflictionResistances.get(curAffl).mod(effective.afflictionResistances.get(curAffl)));
			}


		}

		//Weapon
		// If nothing is there, skip.
		if (this.getWeapon().isPresent()) {
			// Set type variable
			if (this.getWeapon().get().type instanceof Item.Weapon type) {
				// For each statmod
				for (stat curStat : type.statModulation.keySet())
					effective.set(curStat, Math.round((Float) type.statModulation.get(curStat).mod(effective.get(curStat))));
			}
		}

		return effective;

	}
}

