package engine;

import actions.BasicAttackAction;
import actions.BattleAction;
import domain.Enemy;

public class BasicEnemyActionStrategy implements EnemyActionStrategy{
    private final BasicAttackAction basicAttackAction = new BasicAttackAction();

    @Override
    public BattleAction execute(BattleContext context, Enemy enemy) {
        basicAttackAction.execute(context, enemy, context.getPlayer());
        return basicAttackAction;
	}
}