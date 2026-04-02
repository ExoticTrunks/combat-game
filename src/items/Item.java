package items;
import domain.Combatant;
import domain.PlayerCharacter; //placeholders for now until this code is polished
import engine.BattleContext; //placeholders for now until this code is polished

public interface Item {
    String getName();
    String getDescription();
    void use(BattleContext context, PlayerCharacter user, Combatant target);
    default boolean requiresTarget() {
        return false;
    }
}
