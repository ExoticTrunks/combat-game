package actions;

import domain.Combatant;
import domain.PlayerCharacter;
import engine.BattleContext;
import items.Item;

public class UseItemAction implements BattleAction {
    private final Item item;

    public UseItemAction(Item item) {
        this.item = item; 
    } 
    
    @Override
    public String getName() {
        return "UseItem(" + item.getName() + ")";
    }

    @Override
    public boolean requiresTarget() {
        return item.requiresTarget();
    }

    @Override
    public void execute(BattleContext context, Combatant actor, Combatant target) {
        if (!(actor instanceof PlayerCharacter player)) {
            context.log("Only player characters can use items.");
            return;
        }
        item.use(context, player, target);
    }
}
