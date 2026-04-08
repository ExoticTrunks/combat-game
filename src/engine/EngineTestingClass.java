package engine;

import java.util.ArrayList;
import java.util.List;

import domain.Combatant;
import domain.Enemy;
import domain.Goblin;
import domain.Wolf;

public class EngineTestingClass {
	public static void main(String[] args) {
//		TestTurnOrder();
		TestBattleContext();
	}
	
	public static boolean TestTurnOrder() {
		List<Combatant> combatants = new ArrayList<Combatant>();
		combatants.add(new Goblin("A"));
		combatants.add(new Wolf("A"));
		combatants.add(new Goblin("B"));
		combatants.add(new Wolf("B"));
		
		TurnOrderStrategy speedOrderStrat = new SpeedTurnOrderStrategy();
		List<Combatant> sorted = speedOrderStrat.determineTurnOrder(combatants);
		sorted.stream().forEach(c -> System.out.printf("%s, speed: %d\n", c.getName(), c.getSpeed()));
		
		return true;
	}
	
	public static boolean TestBattleContext() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		enemies.add(new Goblin("A"));
		enemies.add(new Wolf("A"));
		enemies.add(new Goblin("B"));
		enemies.add(new Wolf("B"));
		BattleContext bc = new BattleContext(null, enemies);
		
		System.out.printf("alive enemies: %d\n", bc.getAliveEnemies().size());
		System.out.printf("all enemies defeated: %b\n", bc.allEnemiesDefeated());
		
		enemies.stream().forEach(e -> e.takeDamage(9999999));

		System.out.printf("dealing 9999999 damage to all enemies...\n");
		System.out.printf("alive enemies: %d\n", bc.getAliveEnemies().size());
		System.out.printf("all enemies defeated: %b\n", bc.allEnemiesDefeated());
		return true;
	}
}
