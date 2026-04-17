package engine;

import java.util.ArrayList;
import java.util.List;

import actions.BasicAttackAction;
import actions.BattleAction;
import actions.DefendAction;
import domain.Enemy;
import domain.Goblin;
import domain.Wolf;

// Just a demo of using this as FSM
// Advantage of enemy classes not needing to know any actions and ease of having multiple of
//		these strategy classes for easy switching between different difficulties
public class FSMEnemyActionStrategy implements EnemyActionStrategy{
	private final DefendAction defendAction = new DefendAction();
    private final BasicAttackAction basicAttackAction = new BasicAttackAction();
    
	// Just a demo with bare minimum to show how it might work
	private class FSM{
		enum State{
			DEFAULT,
			AGGRESSIVE,
			DEFENSIVE
		}
		enum EnemyType{
			GOBLIN,
			WOLF,
			DEFAULT
		}
		private Enemy enemy;
		private EnemyType enemyType;
		private State state = State.DEFAULT;
		
		FSM(Enemy enemy){
			this.enemy = enemy;
			if (enemy instanceof Wolf) {
				enemyType = EnemyType.WOLF;
			}
			else if (enemy instanceof Goblin) {
				enemyType = EnemyType.GOBLIN;
			}
			else {
				enemyType = EnemyType.DEFAULT;
			}
		}
		
		public Enemy getEnemy() {
			return enemy;
		}
		
		public BattleAction execute(BattleContext context) {
			switch(enemyType) {
			case WOLF:
				switch(state) {
				case DEFAULT:
					basicAttackAction.execute(context, enemy, context.getPlayer());
					state = State.AGGRESSIVE;
					return basicAttackAction;
				case AGGRESSIVE:
					basicAttackAction.execute(context, enemy, context.getPlayer());
					basicAttackAction.execute(context, enemy, context.getPlayer());
					state = State.DEFENSIVE;
					return basicAttackAction;
				case DEFENSIVE:
					if (enemy.getHp() <= enemy.getMaxHp() / 3) {
						state = State.AGGRESSIVE;
					}
					else {
						state = State.DEFAULT;
					}
					defendAction.execute(context, enemy, enemy);
					return defendAction;
				default:
					basicAttackAction.execute(context, enemy, context.getPlayer());
					return basicAttackAction;
				}

			default:
				basicAttackAction.execute(context, enemy, context.getPlayer());
				return basicAttackAction;
			}
		}
	}
	
	private List<FSM> enemyFSMs = new ArrayList<FSM>();
	
	private List<Enemy> registered = new ArrayList<Enemy>();
	
	@Override
	public BattleAction execute(BattleContext context, Enemy enemy) {
		FSM fsm = getEnemyFSM(enemy); 
		if (fsm == null) {
			fsm = registerNewEnemy(enemy);
			registered.add(enemy);
		}
		
		return fsm.execute(context);
	}
	
	private FSM registerNewEnemy(Enemy enemy) {
		FSM fsm = new FSM(enemy);
		enemyFSMs.add(fsm);
		return fsm;
	}

	private FSM getEnemyFSM(Enemy enemy) {
		for (FSM fsm: enemyFSMs) {
			if (fsm.getEnemy() == enemy) {
				return fsm;
			}
		}
		return null;
	}
}
