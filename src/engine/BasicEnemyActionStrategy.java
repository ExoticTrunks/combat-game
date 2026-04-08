package engine;

import actions.BasicAttackAction;
import domain.Enemy;
import domain.PlayerCharacter;

public class BasicEnemyActionStrategy implements EnemyActionStrategy{
    private final BasicAttackAction basicAttackAction = new BasicAttackAction();

    @Override
    public void execute(BattleContext context, Enemy enemy) {
        basicAttackAction.execute(context, enemy, context.getPlayer());
	}
}
