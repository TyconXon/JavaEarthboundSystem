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
	};


	String name; // Parameter

	Stats stats; // Parameter
	Status status;
	
	ArrayList<Item.ItemInstance> inventory = new ArrayList<>();
	PSI[] knowledge; // Parameter
	
	public HashSet<equipmentSlot> validEquipmentSlots;
	HashMap<equipmentSlot, Item.Equipment.ItemInstance> equipment = new HashMap<>();
	
	Item.Food[] preferredFoods;

	public PartyMember(String name, Stats stats, PSI[] knowledge) {
		this.name = name;
		this.stats = stats;
		this.knowledge = knowledge;
		validEquipmentSlots = new HashSet<>(List.of(new equipmentSlot[]{equipmentSlot.HEAD, equipmentSlot.HANDS, equipmentSlot.BODY, equipmentSlot.ARMS, equipmentSlot.LEGS, equipmentSlot.NECK, equipmentSlot.SHOES}));
	}


	public boolean giveItem(Item.ItemInstance item){
		if (this.inventory.size() < this.stats.maxCarry) {
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

		if (item.type instanceof Item.Equipment type){
			// Cannot equip
			if ( !validEquipmentSlots.contains( type.slot ) ){
				return EarthBound.returnCode.INCOMPATIBLE;
			}


			this.equipment.putIfAbsent( type.slot, item);
			EarthBound.print("\\b" + this.name + "\\d has equipped \\g" + type.name + "\\d onto their \\y" + type.slot + "\\d!");
			return EarthBound.returnCode.SUCCESS;
		}else{return EarthBound.returnCode.WHAT;}

	}
}

