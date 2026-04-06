package domain;

import actions.BattleAction;
import items.Inventory;

public abstract class PlayerCharacter extends AbstractCombatant {
    private final Inventory inventory;
    protected PlayerCharacter(String name, int maxHp, int attack, int defense, int speed, Inventory inventory) {
        super(name, maxHp, attack, defense, speed);
        this.inventory = inventory;
    }
    @Override
    public boolean isPlayer() {
        return true;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public abstract String getSpecialSkillName();
    // creates the concrete special skill action for this player class, making this more OCP friendly
  
    public abstract BattleAction createSpecialSkillAction(boolean ignoreCooldown);
}
