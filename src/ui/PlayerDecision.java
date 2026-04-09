package ui;

import actions.BattleAction;
import domain.Combatant;

public record PlayerDecision(BattleAction action, Combatant target) {
}
