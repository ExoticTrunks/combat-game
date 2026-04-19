package engine;

import java.util.ArrayList;
import java.util.List;

import actions.BattleAction;
import domain.Combatant;
import domain.Enemy;
import domain.PlayerCharacter;
import level.LevelConfig;

public class BattleContext implements BattleEngineObserver {
	private int roundNumber;
	private PlayerCharacter player;
	private List<Enemy> enemies;
	private List<String> logs = new ArrayList<String>();
	
    public BattleContext(PlayerCharacter player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.roundNumber = 0;
    }
    

    public List<Enemy> getEnemies() {
        return enemies;
    }

    
	public List<Enemy> getAliveEnemies() {
		return enemies.stream().filter(e -> e.isAlive()).toList();
	}
	
	public List<Combatant> getAllCombatants() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(enemies);
        return all;
	}
	
	public boolean allEnemiesDefeated() {
		return enemies.stream().noneMatch(e -> e.isAlive());
	}
	
	public void log(String message) {
		logs.add(message);
	}

	public PlayerCharacter getPlayer() {
		return player;
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public void incrementRoundNumber() {
		++roundNumber;
	}
	
	public List<Enemy> spawnNewEnemies(List<Enemy> enemies) {
		this.enemies.addAll(enemies);
		return this.enemies;
	}

	public List<String> consumeLog() {
		List<String> copy = new ArrayList<String>(logs);
		logs.clear();
		return copy;
	}
	
	public void resetLog() {
		logs.clear();
	}
	
	@Override
	public void onLevelStart(LevelConfig level) {
		log("Level: " + level.getDifficultyName() + " | Initial enemies: " + enemies.size());
	}
	
	@Override
	public void onNewRoundStart(int roundNumber) {
		log("===== Round " + roundNumber + " =====");
	}

	@Override
	public void onTurnOrderDetermined(List<Combatant> orderedCombatants) {
		log("Turn Order: " + orderedCombatants.stream()
					        .map(Combatant::getName)
					        .reduce((a, b) -> a + " -> " + b)
					        .orElse("-"));
	}
	
	@Override
	public void onCombatantTurnStart(Combatant combatant) {
        if (!combatant.isAlive()) {
            log(combatant.getName() + " -> ELIMINATED: Skipped");   
        }
        else if (!combatant.canAct()) {
            log(combatant.getName() + " -> STUNNED: Turn skipped");
        }
	}

	@Override
	public void onCombatantAction(Combatant dealer, BattleAction action, Combatant target) {}

	@Override
	public void onRoundEnd(BattleContext battleContext) {}

	@Override
	public void onBackupSpawn(List<Enemy> enemiesSpawned) {
		log("All initial enemies eliminated -> Backup Spawn triggered! " 
								+ enemiesSpawned.stream()
								.map(Enemy::getName)
				                .reduce((a, b) -> a + " + " + b).orElse("none")
				                + " enter simultaneously");
	}
}
