package planetInevitable.game;

import java.util.*;

import planetInevitable.EarthBound;
import planetInevitable.enums.afflictions;
import planetInevitable.enums.damageTypes;
import planetInevitable.enums.returnCode;
import planetInevitable.enums.stat;
import planetInevitable.helpers.*;

/**
 *
 */
public class PartyMember {

    public boolean instantDamage;
    public HashSet<equipmentSlot> validEquipmentSlots;
    public HashSet<EarthBound.weaponType> validWeaponTypes = new HashSet<>();
    public int equippedWeaponID;
    public HashSet<EarthBound.locale> knownLocales = new HashSet<>();
    public String name; // Parameter
    public Stats stats; // Parameter
    public Status status;
    ArrayList<Item.ItemInstance> inventory = new ArrayList<>();
    HashSet<PSI> knowledge; // Parameter
    HashMap<equipmentSlot, Item.Equipment.ItemInstance> equipment = new HashMap<>();
    Item.Food[] preferredFoods;


    public PartyMember(String name, Stats stats, HashSet<PSI> knowledge) {
        this.name = name;
        this.stats = stats;
        this.knowledge = knowledge;
        validEquipmentSlots = new HashSet<>(List.of(new equipmentSlot[]{equipmentSlot.HEAD, equipmentSlot.HANDS, equipmentSlot.BODY, equipmentSlot.ARMS, equipmentSlot.LEGS, equipmentSlot.NECK, equipmentSlot.SHOES}));

        this.status = new Status();
        status.health = this.getEffectiveStats().get(stat.MAX_HP);
    }

    public Stats getEffectiveStats() {
        var effective = new Stats();
        // Copy original stats into temporary effective stats
        effective.value.putAll(this.stats.value);
        effective.damageTypeWeaknesses.putAll(this.stats.damageTypeWeaknesses);
        effective.damageTypeMultiplier.putAll(this.stats.damageTypeMultiplier);
        effective.afflictionResistances.putAll(this.stats.afflictionResistances);

        //Equipment
        // For each valid equipment slot
        for (equipmentSlot slot : this.validEquipmentSlots) {
            // If nothing is there, skip.
            if (!this.equipment.containsKey(slot)) continue;

            // Set type variable
            if (this.equipment.get(slot).type instanceof Item.Equipment type) {
                // For each statmod
                for (stat curStat : type.statModulation.keySet())
                    effective.set(curStat, Math.round((Float) type.statModulation.get(curStat).mod(this.stats.get(curStat))));
                for (damageTypes curType : type.damageResistances.keySet())
                    effective.damageTypeWeaknesses.put(curType, (Float) type.damageResistances.get(curType).mod(effective.damageTypeWeaknesses.get(curType)));
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

    public Optional<Item.ItemInstance> getWeapon() {
        return inventory.stream().filter((itemInstance -> {
            return itemInstance.id == this.equippedWeaponID;
        })).findFirst();
    }

    public void addExperience(int xp) {
        if (this.status.health == 0) {
            EarthBound.say("\\r"+this.name + " is dead and can't receive XP!");
            return;
        }
        this.setExperience(this.getExperience() + xp);
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
            EarthBound.say("\\y"+this.name + " leveled up! Gained a total of " + levelsGained + " level(s).");
        }
    }

    private void levelUp() {
        stats.level += 1;
    }

    public Item.ItemInstance removeItem(int index) {
        return this.inventory.remove(index);
    }

    public boolean removeItem(Item.ItemInstance item) {
        return this.inventory.remove(item);
    }

    public returnCode equipItem(Item.ItemInstance item) {
        if (this.status.health == 0) {
            EarthBound.say("\\r"+this.name + " is dead and can't equip anything right now!");
            return returnCode.DEAD;
        }

        // Equip if you don't have the item
        if (!this.inventory.contains(item)) {
            if (!this.giveItem(item)) {
                return returnCode.OVERENCUMBERED;
            }
		}

        // Wrong Locale
        if (!knownLocales.contains(item.type.locale)) {
            return returnCode.WRONG_LOCALE;
        }

        if (item.type instanceof Item.Equipment type) {
            // Cannot equip
            if (!validEquipmentSlots.contains(type.slot)) {
                return returnCode.INCOMPATIBLE;
            }
            if (this.equipment.containsKey(type.slot)) {
                this.unequip(this.equipment.get(type.slot));
            }


            this.equipment.putIfAbsent(type.slot, item);
            EarthBound.print("\\b" + this.name + "\\d has equipped \\g" + type.name + "\\d onto their \\y" + type.slot + "\\d!");
            return returnCode.SUCCESS;

        } else if (item.type instanceof Item.Weapon type) {
            // Cannot equip
            if (!validWeaponTypes.contains(type.type)) {
                return returnCode.INCOMPATIBLE;
            }
            // Has something else equipped
            if (this.getWeapon().isPresent()) {
                this.unequip(getWeapon().get());
            }

            this.equippedWeaponID = item.id;
            EarthBound.print("\\b" + this.name + "\\d has equipped \\g" + type.name + "\\d!");
            return returnCode.SUCCESS;
        }
        return returnCode.WHAT;

    }

    public boolean giveItem(Item.ItemInstance item) {
        if (this.inventory.size() < this.stats.get(stat.MAX_CARRY)) {
            this.inventory.add(item);
            return true;
        }
        return false;
    }

    public void unequip(Item.ItemInstance equippable) {
        if (equippable.type instanceof Item.Equipment type) {
            this.equipment.remove(type.slot);
        } else if (equippable.type instanceof Item.Weapon type) {
            this.equippedWeaponID = -1;
        }

        EarthBound.print("\\b" + this.name + "\\d has unequipped \\r" + equippable.type.name + "\\d!");
    }

    public boolean rollCrit() {
        return ((double) this.getEffectiveStats().get(stat.GUTS) / 500 > Math.random()) || (1.0 / 20.0 > Math.random());
    }

    public int bashDamage(boolean crit) {
        if (this.status.health == 0) {
            EarthBound.say("\\r"+this.name + " is dead and can't deal damage!");
            return 0;
        }
        return Math.toIntExact(
                Math.round(
                        ( this.getEffectiveStats().get(stat.OFFENSE) * 2.0 )
                                * (crit ? 4 : (0.75 + Math.random()) )
                ));
    }

    public int receiveDamage(CalculatedAttack attack) {
        int effectiveDamage = attack.damage();

        Item.Weapon weaponType;
        if (attack.inflicter().getWeapon().isPresent()) {
            weaponType = (Item.Weapon) attack.inflicter().getWeapon().get().type;
        } else {
            weaponType = null;
        }

        if (this.status.health == 0) {
            EarthBound.say("\\r"+this.name + " is already dead!");
            return 0;
        }

        // Apply defense based on attack element
        if (!attack.crit()) {
//			console.log(`${this.name}|DEFENSE ITERATION| effectiveDamage = ${effectiveDamage} - ${Math.round(this.stats.defense[attackElement])}`);
            effectiveDamage -= this.getEffectiveStats().get(stat.DEFENSE);

            if (weaponType != null && this.getEffectiveStats().damageTypeWeaknesses.containsKey(weaponType.damageType))
                effectiveDamage *= this.getEffectiveStats().damageTypeWeaknesses.get(weaponType.damageType);

        }

        // Never below one
        if (effectiveDamage < 1) {
            effectiveDamage = 1;
        }

        if (((double) ((this.getEffectiveStats().get(stat.SPEED) * 2) - attack.inflicter().getEffectiveStats().get(stat.SPEED)) / 500) >= Math.random() && !attack.crit()) {
            EarthBound.say(this.name + " Dodged quickly!");
            return 0;
        }

        if (attack.crit()){
            EarthBound.say((this instanceof Adversary)?"\\g":"\\r"+"SMAAAASH!!!");
        }

        EarthBound.say(this.name + " received " + effectiveDamage + " damage!");
        if (this.status.health - effectiveDamage <= 0 && !(this instanceof Adversary)) {
            EarthBound.say(this.name + " took mortal damage!");
        }

        //GUTS
        if (this.status.health - effectiveDamage <= 0 && !attack.crit()) {
            if (((double) this.stats.get(stat.GUTS) / 500 > Math.random()) || ((double) 1 / 20 > Math.random())) {
                effectiveDamage = this.status.health - 1; // Leave on one health
                EarthBound.say(this.name + "was saved by their GUTS!");
            }
        }

        this.status.health -= effectiveDamage;
        // Check if party member is defeated
        if (this.status.health - effectiveDamage <= 0) {
            this.status.health = 0; // Ensure health doesn't go negative
            this.applyStatusEffect(afflictions.DEAD);
            EarthBound.say("\\r"+this.name+" has been defeated!");
        }

        return effectiveDamage;
    }

    public boolean applyStatusEffect(afflictions effect){
        if (this.status.health == 0 && effect != afflictions.DEAD) {
            EarthBound.say(this.name + " is dead and can't become "  + effect.name() + "!");
            return false;
        }
        if (this.getEffectiveStats().afflictionResistances.containsKey(effect) &&
                this.getEffectiveStats().afflictionResistances.get(effect) > Math.random()) {
            EarthBound.say(this.name + " managed was protected from being "  + effect.name() + "!");
            return false;
        }
        EarthBound.say(this.name + " is now "  + effect.name() + "!");
        return this.status.afflictions.add(effect);
    }

    public void removeStatusEffect(afflictions effect){
        this.status.afflictions.remove(effect);
        EarthBound.say("\\g"+this.name + " is no longer " +  effect.name() + "!");
    }

    @Override
    public String toString(){
        return this.name + " [HP: " + this.status.health + "/" + this.getEffectiveStats().get(stat.MAX_HP)+"]";
    }

    public enum equipmentSlot {
        HEAD, BODY, LEGS, SHOES, ARMS, NECK, HANDS,
    }
}

