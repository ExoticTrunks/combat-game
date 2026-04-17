package engine;

import actions.BasicAttackAction;
import actions.BattleAction;
import actions.DefendAction;
import domain.Enemy;
import domain.Wolf;

public class HardEnemyActionStrategy implements EnemyActionStrategy{
	private final DefendAction defendAction = new DefendAction();
    private final BasicAttackAction basicAttackAction = new BasicAttackAction();
    
    
	@Override
	public BattleAction execute(BattleContext context, Enemy enemy) {
		if (enemy instanceof Wolf) {
			if (enemy.getHp() < enemy.getMaxHp() / 2 && Math.random() >= 0.5) {
				defendAction.execute(context, enemy, enemy);
			}
			basicAttackAction.execute(context, enemy, context.getPlayer());
			// Maybe in the future turn to list, but realistically there will be another way to do this
			// such as a new battleAction combining them or a status effect allowing extra turns
			// this is just a demo of how extending this could work
			return basicAttackAction;
		}
		else {
			basicAttackAction.execute(context, enemy, context.getPlayer());
			return basicAttackAction;
		}
	}
}