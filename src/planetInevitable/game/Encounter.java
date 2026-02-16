/**
 * 
 */
package planetInevitable.game;

/**
 * 
 */
public class Encounter {
	public Adversary[] adversaries;
	public PartyMember[] party;
	public swirl initiative;

	private boolean partyTurn;

	public enum swirl {
		GREEN, GREY, RED
	}

	public Encounter(Adversary[] adversaries, PartyMember[] party, swirl initiative){
		this.adversaries = adversaries;
		this.party = party;
		this.initiative = initiative;
	}
}
