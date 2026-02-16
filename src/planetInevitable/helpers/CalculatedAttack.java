package planetInevitable.helpers;

import planetInevitable.game.PartyMember;

public record CalculatedAttack(int damage, PartyMember inflicter, boolean crit) {
}
