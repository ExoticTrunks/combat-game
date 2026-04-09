package items;

import actions.BattleAction;
import domain.Combatant;
import domain.PlayerCharacter;
import engine.BattleContext;

/**
 * Trigger the special skill effect once, but it does not start or change
 * the cooldown timer. In short, free extra use of the skill.
 *
 * Uses PlayerCharacter.createSpecialSkillAction(true) so no instanceof
 * checks are needed — adding a new player class automatically works (OCP).
 */
public class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public String getDescription() {
        return "Trigger special skill once without changing cooldown";
    }

    @Override
    public boolean requiresTarget() {
        // Dynamic: Warrior's Shield Bash needs a target, Wizard's Arcane Blast does not.
        // The UI resolves this by checking user.createSpecialSkillAction(true).requiresTarget().
        return false;
    }

    @Override
    public void use(BattleContext context, PlayerCharacter user, Combatant target) {
        // Create the special skill with ignoreCooldown = true
        BattleAction action = user.createSpecialSkillAction(true);

        int cooldownBefore = user.getSpecialCooldownRemaining();
        action.execute(context, user, target);
        // Restore cooldown — Power Stone must not change it
        user.setSpecialCooldownRemaining(cooldownBefore);
        context.log("Power Stone consumed. Cooldown unchanged -> " + cooldownBefore);
    }
}
