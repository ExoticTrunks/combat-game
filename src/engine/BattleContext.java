package engine;

import java.util.ArrayList;
import java.util.List;

import actions.BattleAction;
import domain.Combatant;
import domain.Enemy;
import domain.PlayerCharacter;

public class BattleContext {
	private int roundNumber;
	private PlayerCharacter player;
	private List<Enemy> enemies;
	private List<String> logs = new ArrayList<String>();
	
    public BattleContext(PlayerCharacter player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.roundNumber = 0;
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

	public void log(Combatant combatant1, BattleAction action, Combatant combatant2) {
		//TODO: (E)
		logs.add(combatant1 + "");
	}
	
	// Logs current state
	public void log() {
		//TODO: (E)
		logs.add("");
	}
	
	public void log(DifficultyLevel difficultyLevel) {
		//TODO: (E)
		continue;
	}
	
	public List<String> consumeLog() {
		List<String> copy = new ArrayList<String>(logs);
		logs.clear();
		return copy;
	}
	
	public void resetLog() {
		logs.clear();
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
	
	public void spawnNewEnemies(List<Enemy> enemies) {
		enemies.addAll(enemies);
	}
}
