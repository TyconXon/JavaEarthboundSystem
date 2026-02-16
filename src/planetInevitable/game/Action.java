package planetInevitable.game;

import planetInevitable.EarthBound;
import planetInevitable.helpers.CalculatedAttack;

import java.util.HashSet;

public abstract class Action {
    public PartyMember invoker;
    public HashSet<PartyMember> targets =  new HashSet<>();
    public boolean completed;

    public abstract Object execute();

    // Bash, shoot, swing
    public static class Hit extends Action {
        @Override
        public Object execute() {
            for(PartyMember target : targets) {
                boolean crit = invoker.rollCrit();
                int damage = invoker.bashDamage(crit);
                target.receiveDamage(new CalculatedAttack(damage, invoker, crit));
            }
            return null;
        }
    }
    // PSI
    public static class Magic extends Action {
        @Override
        public Object execute() {
            return null;
        }
    }
    // Use, switch, give
    public static class ItemAction extends Action {
        @Override
        public Object execute() {
            return null;
        }
    }
    // Jeff tools, Paula pray, Pu transform, all defend
    public static class Ability extends Action {
        @Override
        public Object execute() {
            return null;
        }
    }
}

