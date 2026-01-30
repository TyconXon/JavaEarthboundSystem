package planetInevitable;

import java.util.HashMap;

import planetInevitable.helpers.*;

/**
 * 
 */
public class PartyMember {
	String name;
	
	Stats stats;
	Status status;
	
	Item.ItemInstance[] inventory;
	PSI[] knowledge;
	HashMap<String, Item.ItemInstance> equipment;
	
	Item[] preferredFoods;
	
	
}

