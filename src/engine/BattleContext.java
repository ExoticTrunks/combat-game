package engine;

import java.util.ArrayList;
import java.util.List;

import domain.Combatant;
import domain.Enemy;
import domain.PlayerCharacter;

public class BattleContext {
	private int roundNumber;
	private PlayerCharacter player;
	private List<Enemy> enemies;
	
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
		//TODO
	}

	public PlayerCharacter getPlayer() {
		return player;
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}
}
