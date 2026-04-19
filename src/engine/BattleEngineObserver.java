package engine;

import java.util.List;

import actions.BattleAction;
import domain.Combatant;
import domain.Enemy;
import level.LevelConfig;

public interface BattleEngineObserver {
	public void onLevelStart(LevelConfig level);
	public void onNewRoundStart(int roundNumber);
	public void onTurnOrderDetermined(List<Combatant> orderedCombatants);
	public void onCombatantTurnStart(Combatant combatant);
	public void onCombatantAction(Combatant dealer, BattleAction action, Combatant target);
	public void onRoundEnd(BattleContext battleContext);
	public void onBackupSpawn(List<Enemy> enemiesSpawned);
	// public void onGameOver();
}
