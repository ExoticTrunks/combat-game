package items;

import domain.Combatant;
import domain.PlayerCharacter;
import effects.SmokeBombEffect;
import engine.BattleContext;

public class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public String getDescription() {
        return "Enemy attacks deal 0 damage this turn and next turn";
    }

    @Override
    public void use(BattleContext context, PlayerCharacter user, Combatant target) {
        user.addStatusEffect(new SmokeBombEffect());
        context.log(user.getName() + " uses Smoke Bomb: enemy attacks deal 0 damage this turn and next turn.");
    }
}
