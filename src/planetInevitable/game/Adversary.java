package planetInevitable.game;

import planetInevitable.helpers.Stats;

public class Adversary extends PartyMember {

	drop[] possibleDrops;
	int row;
	int xp;

	public Adversary(String name, Stats stats, PSI[] knowledge) {
		super(name, stats, knowledge);
		this.instantDamage = true;
	}

	public class drop {
		float chance;
		Item item;
	}
}
