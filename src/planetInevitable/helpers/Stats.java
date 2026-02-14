package planetInevitable.helpers;

import planetInevitable.EarthBound;

import java.util.*;


public class Stats {
	
//	public int level;
//	public int maxHP;
//	public int maxPP;
//	public int maxCarry;
//	public int defense;
//	public int guts;
//	public int speed;
//	public int iq;
//	public int vitality;
//	public int offense;


	HashMap<stat, Integer> value = new HashMap<>();

	public int get(stat stat) {
		return this.value.get(stat);
	}
	public void set(stat stat, int value) {
		this.value.put(stat, value);
	}

	public HashMap<EarthBound.damageTypes,Double> damageTypeMultiplier = new HashMap<>();
	public HashMap<Affliction.afflictions,Double> afflictionResistances = new HashMap<>();

	
	public boolean verify() {
		//Check if every damageTypeMultiplier has a value
		for (EarthBound.damageTypes type : EarthBound.damageTypes.values()){
			if (!damageTypeMultiplier.containsKey(type)) {
				return false;
			}
		}
		//Check if every afflictionResistances has a value
		for (Affliction.afflictions type : Affliction.afflictions.values()){
			if (!afflictionResistances.containsKey(type)) {
				return false;
			}
		}
		return true;
	}

}
