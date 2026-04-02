package actions;

import domain.Combatant;
import engine.BattleContext;

public interface BattleAction {
    String getName();
    void execute(BattleContext context, Combatant actor, Combatant target);
    default boolean requiresTarget() {
        return false;
    }
}
