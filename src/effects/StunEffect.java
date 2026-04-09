package effects;

import domain.Combatant;

/**
 * Affected entity is unable to take actions for the current turn and the next turn.
 *
 * "Current turn" = the target's next upcoming turn after being stunned.
 * "Next turn" = the one after that.
 *
 * Remaining is decremented at endTurn (after the stunned turn is skipped),
 * so the target skips exactly 2 of its own turns before recovering.
 */
public class StunEffect implements StatusEffect {
    private int remainingSkippedTurns = 2;

    @Override
    public String getName() {
        return "Stun";
    }

    @Override
    public void onApply(Combatant owner) {
        // Target becomes unable to act on its next two turns.
    }

    @Override
    public void onOwnerTurnEnd(Combatant owner) {
        if (remainingSkippedTurns > 0) {
            remainingSkippedTurns--;
        }
    }

    @Override
    public boolean preventsAction() {
        return remainingSkippedTurns > 0;
    }

    @Override
    public boolean isExpired() {
        return remainingSkippedTurns <= 0;
    }

    @Override
    public String describe() {
        return "Stun(" + remainingSkippedTurns + " skip(s) left)";
    }
}
