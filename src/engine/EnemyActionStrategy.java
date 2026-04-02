package engine;
import domain.Enemy;

//defines how an enemy decides what to do on its turn(just be a basic attack) but this interface allows for further implementation(DIP)
public interface EnemyActionStrategy {
    void execute(BattleContext context, Enemy enemy);
}
