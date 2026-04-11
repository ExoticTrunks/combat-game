package effects;

import domain.Combatant;

/**
 * Increases defense by 10 for the current round and the next round.
 *
 * Duration is tracked by decrementing at the START of each owner turn
 * (not the end), so the bonus remains active while enemies attack in
 * the same round. The first owner startTurn after the effect was applied
 * is the beginning of "the next round", so remaining goes 2 → 1 → 0:
 *
 *   Round N (applied): remaining = 2, enemies hit +10 DEF  ✓
 *   Round N+1:         startTurn → remaining = 1, enemies hit +10 DEF  ✓
 *   Round N+2:         startTurn → remaining = 0, effect removed        ✓
 */
public class DefendEffect implements StatusEffect {
    private int remainingRounds = 2;

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void onApply(Combatant owner) {
        // Active immediately when applied.
    }

    @Override
    public void onOwnerTurnStart(Combatant owner) {
        if (remainingRounds > 0) {
            remainingRounds--;
        }
    }

    @Override
    public int getDefenseBonus() {
        return remainingRounds > 0 ? 10 : 0;
    }

    @Override
    public boolean isExpired() {
        return remainingRounds <= 0;
    }

    @Override
    public String describe() {
        return "Defend(+10 DEF, " + remainingRounds + " round(s) left)";
    }
}
