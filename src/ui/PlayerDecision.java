package com.ntu.sc2002.arena.ui;

import com.ntu.sc2002.arena.actions.BattleAction;
import com.ntu.sc2002.arena.domain.Combatant;

public record PlayerDecision(BattleAction action, Combatant target) {
}
