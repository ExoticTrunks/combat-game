package engine;
import actions.BattleAction;
import domain.Enemy;

//defines how an enemy decides what to do on its turn(just be a basic attack) but this interface allows for further implementation(DIP)
public interface EnemyActionStrategy {
	// Returns the battleAction it used
    BattleAction execute(BattleContext context, Enemy enemy);
}
