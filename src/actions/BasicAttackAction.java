package actions;

import domain.Combatant;
import engine.BattleContext;

public class BasicAttackAction implements BattleAction {
    @Override
    public String getName() {
        return "BasicAttack";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public void execute(BattleContext context, Combatant actor, Combatant target) {
        if (target == null || !target.isAlive()) {
            context.log(actor.getName() + " tried to BasicAttack, but the target is invalid.");
            return;
        }

        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        int before = target.getHp();
        target.takeDamage(damage);
        int dealt = before - target.getHp();

        context.log(actor.getName() + " -> BasicAttack -> " + target.getName()
                + ": HP: " + before + " -> " + target.getHp() + " (dmg: " + dealt + ")");

        if (!target.isAlive()) {
            context.log(target.getName() + " [ELIMINATED]");
        }
    }
}
