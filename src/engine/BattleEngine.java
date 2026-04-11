package engine;

import java.util.List;

import domain.Combatant;
import domain.Enemy;
import domain.PlayerCharacter;
import level.DifficultyLevel;
import level.LevelConfig;
import ui.PlayerDecision;

public class BattleEngine {
	private TurnOrderStrategy turnOrderStrategy;
	private EnemyActionStrategy enemyActionStrategy;
	private BattleUI ui;
	
	public BattleEngine(TurnOrderStrategy turnOrderStrategy, EnemyActionStrategy enemyActionStrategy, BattleUI ui) {
		this.turnOrderStrategy = turnOrderStrategy;
		this.enemyActionStrategy = enemyActionStrategy;
		this.ui = ui;
	}
	
	public BattleResult runBattle(PlayerCharacter player, LevelConfig levelConfig) {
		DifficultyLevel difficultyLevel = levelConfig.getDifficulty();
		
//		List<List<Enemy>> waves = LevelEnemyFactory.spawnAndGetAllEnemyWaves(difficultyLevel);
		boolean victory;
		
		BattleContext battleContext = new BattleContext(player, levelConfig.getAndProgressWave());
		while(true) {
			battleContext.incrementRoundNumber();
			// Spawn condition
			if(battleContext.allEnemiesDefeated()) {
				if(levelConfig.hasBackupWave()) {
					battleContext.spawnNewEnemies(levelConfig.getAndProgressWave());
				}
				// Victory condition
				else {
					victory = true;
					break;
				}
				
			}
			// Loss condition
			else if(!player.isAlive()) {
				victory = false;
				break;
			}
			
			// Start to end turn for combatants
			List<Combatant> sortedCombatants = turnOrderStrategy.determineTurnOrder(battleContext.getAllCombatants());
			for(Combatant combatant : sortedCombatants) {
				combatant.startTurn();
				if (combatant.canAct()) {
					if(combatant.isPlayer()) {
						PlayerDecision decision = ui.getPlayerDecision(battleContext);
						//TODO: player decision execution
					}
					else {
						enemyActionStrategy.execute(battleContext, (Enemy) combatant);
					}
				}
				combatant.endTurn();
			}
		}
		
		BattleResult battleResult = new BattleResult(victory, battleContext.getRoundNumber(),
				(victory) ? player.getHp() : battleContext.getAliveEnemies().size());
		
		return battleResult;
	}
}
