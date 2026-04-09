package items;

import domain.Combatant;
import domain.PlayerCharacter;
import engine.BattleContext;

public class Potion implements Item {
    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public String getDescription() {
        return "Heal 100 HP";
    }

    @Override
    public void use(BattleContext context, PlayerCharacter user, Combatant target) {
        int before = user.getHp();
        user.heal(100);
        context.log(user.getName() + " uses Potion: HP " + before + " -> " + user.getHp());
    }
}
