package items;

import domain.Combatant;
import domain.PlayerCharacter;
import engine.BattleContext;

public interface Item {
    String getName();

    String getDescription();

    void use(BattleContext context, PlayerCharacter user, Combatant target);

    default boolean requiresTarget() {
        return false;
    }
}
