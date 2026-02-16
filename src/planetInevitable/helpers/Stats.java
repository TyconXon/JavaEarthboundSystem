package planetInevitable.helpers;

import planetInevitable.enums.afflictions;
import planetInevitable.enums.damageTypes;
import planetInevitable.enums.stat;

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

	public float lvlXPCurve(Integer level) {
		return (float) Math.pow(0.5 * level, 3.5);
	}
	public int getLevelFromXP(Integer XP) {
		return (int) Math.pow(2 * XP, 1 / 3.5);
	}


	public HashMap<stat, Integer> value = new HashMap<>();

	public int get(stat stat) {
		return this.value.get(stat);
	}
	public void set(stat stat, int value) {
		this.value.put(stat, value);
	}

	public HashMap<damageTypes,Float> damageTypeMultiplier = new HashMap<>();
	public HashMap<afflictions,Float> afflictionResistances = new HashMap<>();
	public HashMap<damageTypes,Float> damageTypeResistances = new HashMap<>();

	
	public boolean verify() {
		//Check if every damageTypeMultiplier has a value
		for (damageTypes type : damageTypes.values()){
			if (!damageTypeMultiplier.containsKey(type)) {
				return false;
			}
		}
		//Check if every afflictionResistances has a value
		for (afflictions type : afflictions.values()){
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
		for (damageTypes type : damageTypes.values()){
			damageTypeResistances.putIfAbsent(type, 0.0f);
		}
		for (afflictions type : afflictions.values()){
			afflictionResistances.putIfAbsent(type, 0.0f);
		}

	}

	@Override
	public String toString() {
		return value.toString();
	}
}
