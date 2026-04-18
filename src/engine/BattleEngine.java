package engine;

import java.util.ArrayList;
import java.util.List;

import actions.BattleAction;
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
	private List<BattleEngineObserver> observers = new ArrayList<BattleEngineObserver>();
	
	public BattleEngine(TurnOrderStrategy turnOrderStrategy, EnemyActionStrategy enemyActionStrategy, BattleUI ui) {
		this.turnOrderStrategy = turnOrderStrategy;
		this.enemyActionStrategy = enemyActionStrategy;
		this.ui = ui;
	}
	
	public BattleResult runBattle(PlayerCharacter player, LevelConfig levelConfig) {
		DifficultyLevel difficultyLevel = levelConfig.getDifficulty();
		
		boolean victory = false;
		boolean gameOver = false;
		BattleContext battleContext = new BattleContext(player, levelConfig.getAndProgressWave());
		addObserver(battleContext);
		onLevelStart(levelConfig);
		
		while(!gameOver) {
			battleContext.incrementRoundNumber();
			
			onNewRoundStart(battleContext.getRoundNumber());
			
			
			// Start to end turn for combatants
			List<Combatant> sortedCombatants = turnOrderStrategy.determineTurnOrder(battleContext.getAllCombatants());
			onTurnOrderDetermined(sortedCombatants);
			
			for(Combatant combatant : sortedCombatants) {
				BattleAction actionUsed;
				Combatant target;
				
				// Combatant turn
				combatant.startTurn();
				onCombatantTurnStart(combatant);
				if (combatant.canAct()) {
					if(combatant.isPlayer()) {
						ui.printEvents(battleContext.consumeLog());
						PlayerDecision decision = ui.getPlayerDecision(battleContext);
						actionUsed = decision.action();
						target = decision.target();
						
						actionUsed.execute(battleContext, combatant, target);
					}
					else {
						target = battleContext.getPlayer();
						actionUsed = enemyActionStrategy.execute(battleContext, (Enemy) combatant);
					}
					onCombatantAction(combatant, actionUsed, target);
				}
				combatant.endTurn();
				
				
				// Spawn condition
				if(battleContext.allEnemiesDefeated()) {
					if(levelConfig.hasBackupWave()) {
						List<Enemy> backupWave = levelConfig.getAndProgressWave();
						battleContext.spawnNewEnemies(backupWave);
						onBackupSpawn(backupWave);
						break;
					}
					// Victory condition
					else {
						victory = true;
						gameOver = true;
						break;
					}
					
				}
				// Loss condition
				else if(!player.isAlive()) {
					victory = false;
					gameOver = true;
					break;
				}
			}
			onRoundEnd(battleContext);
            ui.printEvents(battleContext.consumeLog());
            ui.showBattleState(battleContext);
		}

        ui.printEvents(battleContext.consumeLog());
		if (victory) {
			ui.showVictory(battleContext);
		} 
		else {
			ui.showDefeat(battleContext);
		}
		BattleResult battleResult = new BattleResult(victory, battleContext.getRoundNumber(),
				(victory) ? player.getHp() : battleContext.getAliveEnemies().size());
		return battleResult;
	}
	
	public void addObserver(BattleEngineObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(BattleEngineObserver observer) {
		observers.remove(observer);
	}
	
	private void onLevelStart(LevelConfig level) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onLevelStart(level);
		}
	}

	private void onNewRoundStart(int roundNumber) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onNewRoundStart(roundNumber);
		}
	}
	private void onTurnOrderDetermined(List<Combatant> orderedCombatants) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onTurnOrderDetermined(orderedCombatants);
		}
	}
	private void onCombatantTurnStart(Combatant combatant) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onCombatantTurnStart(combatant);
		}
	}
	private void onCombatantAction(Combatant dealer, BattleAction action, Combatant target) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onCombatantAction(dealer, action, target);
		}
	}
	private void onRoundEnd(BattleContext battleContext) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onRoundEnd(battleContext);
		}
	}
	private void onBackupSpawn(List<Enemy> enemySpawned) {
		// Update observers
		for(BattleEngineObserver observer: observers) {
			observer.onBackupSpawn(enemySpawned);
		}
	}
}
