package engine;

import java.util.List;

import domain.Combatant;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy{

	@Override
	public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
		return combatants.stream().filter(c -> c.isAlive())
				.sorted((c1, c2) -> c2.getSpeed() - c1.getSpeed())
				.toList();
	}
}
