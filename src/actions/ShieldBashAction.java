package actions;

import domain.Combatant;
import domain.Warrior;
import effects.StunEffect;
import engine.BattleContext;

public class ShieldBashAction implements BattleAction {
    private final boolean ignoreCooldown;

    public ShieldBashAction() {
        this(false);
    }

    public ShieldBashAction(boolean ignoreCooldown) {
        this.ignoreCooldown = ignoreCooldown;
    }

    @Override
    public String getName() {
        return "ShieldBash";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public void execute(BattleContext context, Combatant actor, Combatant target) {
        if (!(actor instanceof Warrior warrior)) {
            context.log("Shield Bash can only be used by a Warrior.");
            return;
        }

        if (!ignoreCooldown && warrior.getSpecialCooldownRemaining() > 0) {
            context.log(warrior.getName() + " cannot use Shield Bash. Cooldown: "
                    + warrior.getSpecialCooldownRemaining());
            return;
        }

        if (target == null || !target.isAlive()) {
            context.log("Shield Bash requires a valid target.");
            return;
        }

        int damage = Math.max(0, warrior.getAttack() - target.getDefense());
        int before = target.getHp();
        target.takeDamage(damage);
        int dealt = before - target.getHp();

        if (target.isAlive()) {
            target.addStatusEffect(new StunEffect());
            context.log(warrior.getName() + " -> Shield Bash -> " + target.getName()
                    + ": HP: " + before + " -> " + target.getHp() + " (dmg: " + dealt + ") | "
                    + target.getName() + " STUNNED");
        } else {
            context.log(warrior.getName() + " -> Shield Bash -> " + target.getName()
                    + ": HP: " + before + " -> " + target.getHp() + " (dmg: " + dealt + ")");
            context.log(target.getName() + " [ELIMINATED]");
        }

        if (!ignoreCooldown) {
            warrior.setSpecialCooldownRemaining(3);
        }
    }
}
