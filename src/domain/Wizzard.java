package domain;

import actions.ArcaneBlastAction;
import actions.BattleAction;
import items.Inventory;

public class Wizard extends PlayerCharacter {
    public Wizard(Inventory inventory) {
        super("Wizard", 200, 50, 10, 20, inventory);
    }
    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }
    @Override
    public BattleAction createSpecialSkillAction(boolean ignoreCooldown) {
        return new ArcaneBlastAction(ignoreCooldown);
    }
}
