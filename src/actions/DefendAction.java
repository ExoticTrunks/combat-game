package actions;

import domain.Combatant;
import effects.DefendEffect;
import engine.BattleContext;

public class DefendAction implements BattleAction {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void execute(BattleContext context, Combatant actor, Combatant target) {
        actor.addStatusEffect(new DefendEffect());
        context.log(actor.getName() + " uses Defend: +10 DEF for the current and next round.");
    }
}