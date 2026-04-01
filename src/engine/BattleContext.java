package engine;

import java.util.List;

public class BattleContext {
	private int roundNumber;
	//TODO
//	private PlayerCharacter player;
	private List enemies;
	
	public List getAliveEnemies() {
		// TODO
		return enemies;
	}
	
	public List getAllCombatants() {
		//TODO
//		return player;
		return null;
	}
	
	public boolean allEnemiesDefeated() {
		//TODO
		return false;
	}
	public void log(String message) {
		//TODO
	}
}
