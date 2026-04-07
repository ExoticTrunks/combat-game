package domain;

import actions.BattleAction;
import actions.ShieldBashAction;
import items.Inventory;

public class Warrior extends PlayerCharacter {
    public Warrior(Inventory inventory) {
        super("Warrior", 260, 40, 20, 30, inventory);
    }
    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }
    @Override
    public BattleAction createSpecialSkillAction(boolean ignoreCooldown) {
        return new ShieldBashAction(ignoreCooldown);
    }
}
