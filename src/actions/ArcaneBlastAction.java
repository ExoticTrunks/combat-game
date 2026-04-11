package actions;

import domain.Combatant;
import domain.Enemy;
import domain.Wizard;
import effects.ArcaneBlastEffect;
import engine.BattleContext;

import java.util.ArrayList;
import java.util.List;

public class ArcaneBlastAction implements BattleAction {
    private final boolean ignoreCooldown;

    public ArcaneBlastAction() {
        this(false);
    }

    public ArcaneBlastAction(boolean ignoreCooldown) {
        this.ignoreCooldown = ignoreCooldown;
    }

    @Override
    public String getName() {
        return "ArcaneBlast";
    }

    @Override
    public void execute(BattleContext context, Combatant actor, Combatant target) {
        if (!(actor instanceof Wizard wizard)) {
            context.log("Arcane Blast can only be used by a Wizard.");
            return;
        }

        if (!ignoreCooldown && wizard.getSpecialCooldownRemaining() > 0) {
            context.log(wizard.getName() + " cannot use Arcane Blast. Cooldown: "
                    + wizard.getSpecialCooldownRemaining());
            return;
        }

        List<Enemy> enemies = new ArrayList<>(context.getAliveEnemies());
        if (enemies.isEmpty()) {
            context.log("Arcane Blast has no targets.");
            return;
        }

        context.log(wizard.getName() + " -> Arcane Blast -> All Enemies:");
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            int damage = Math.max(0, wizard.getAttack() - enemy.getDefense());
            int before = enemy.getHp();
            enemy.takeDamage(damage);
            int dealt = before - enemy.getHp();

            StringBuilder line = new StringBuilder("  " + enemy.getName()
                    + ": HP: " + before + " -> " + enemy.getHp() + " (dmg: " + dealt + ")");
            if (!enemy.isAlive()) {
                wizard.addStatusEffect(new ArcaneBlastEffect(10));
                line.append(" [ELIMINATED] | ATK +10");
            }
            context.log(line.toString());
        }

        if (!ignoreCooldown) {
            wizard.setSpecialCooldownRemaining(3);
        }
    }
}
