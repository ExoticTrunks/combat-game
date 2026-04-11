package effects;

import domain.Combatant;

public interface StatusEffect {
    String getName();

    void onApply(Combatant owner);

    default void onOwnerTurnStart(Combatant owner) {
        // default no-op
    }

    default void onOwnerTurnEnd(Combatant owner) {
        // default no-op
    }

    default int modifyIncomingDamage(Combatant owner, int damage) {
        return damage;
    }

    default boolean preventsAction() {
        return false;
    }

    default int getAttackBonus() {
        return 0;
    }

    default int getDefenseBonus() {
        return 0;
    }

    boolean isExpired();

    String describe();
}
