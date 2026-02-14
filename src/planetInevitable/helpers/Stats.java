package planetInevitable.helpers;

import planetInevitable.EarthBound;

import java.util.*;


public class Stats {
	
	public int level;
	public int experience;


	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public static float lvlXPCurve(Integer level) {
		return (float) Math.pow(0.5 * level, 3.5);
	}
	public static int getLevelFromXP(Integer XP) {
		return (int) Math.pow(2 * XP, 1 / 3.5);
	}


	public HashMap<stat, Integer> value = new HashMap<>();

	public int get(stat stat) {
		return this.value.get(stat);
	}
	public void set(stat stat, int value) {
		this.value.put(stat, value);
	}

	public HashMap<EarthBound.damageTypes,Float> damageTypeMultiplier = new HashMap<>();
	public HashMap<Affliction.afflictions,Float> afflictionResistances = new HashMap<>();
	public HashMap<EarthBound.damageTypes,Float> damageTypeResistances = new HashMap<>();

	
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

	public void defaultStats(){
		for (stat stat : stat.values()){
			set(stat, 25);
		}
		for (EarthBound.damageTypes type : EarthBound.damageTypes.values()){
			damageTypeResistances.putIfAbsent(type, 0.0f);
		}
		for (Affliction.afflictions type : Affliction.afflictions.values()){
			afflictionResistances.putIfAbsent(type, 0.0f);
		}

	}

	@Override
	public String toString() {
		return value.toString();
	}
}
