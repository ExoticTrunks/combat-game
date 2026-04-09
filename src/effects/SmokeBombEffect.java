package effects;

import domain.Combatant;

/**
 * Enemy attacks deal 0 damage in the current turn and the next turn.
 *
 * Duration is decremented at the START of the owner's turn so the
 * invulnerability stays active while enemies attack later in the same round.
 *
 *   Round N (applied): remaining = 2, enemy damage → 0  ✓
 *   Round N+1:         startTurn → remaining = 1, enemy damage → 0  ✓
 *   Round N+2:         startTurn → remaining = 0, effect removed       ✓
 */
public class SmokeBombEffect implements StatusEffect {
    private int remainingRounds = 2;

    @Override
    public String getName() {
        return "Smoke Bomb Invulnerability";
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
    public int modifyIncomingDamage(Combatant owner, int damage) {
        return remainingRounds > 0 ? 0 : damage;
    }

    @Override
    public boolean isExpired() {
        return remainingRounds <= 0;
    }

    @Override
    public String describe() {
        return "SmokeBomb(0 dmg, " + remainingRounds + " round(s) left)";
    }
}
