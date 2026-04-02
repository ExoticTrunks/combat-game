package effects;
import domain.Combatant;

public interface StatusEffect {
    String getName();
    void onApply(Combatant owner);
    // Default no-op where only DefendEffect and SmokeBombEffect override this
    default void onOwnerTurnStart(Combatant owner) {
    }
    // Default no-op where only StunEffect overrides this
    default void onOwnerTurnEnd(Combatant owner) {
    }
    // Default pass-through where only SmokeBombEffect overrides this
    default int modifyIncomingDamage(Combatant owner, int damage) {
        return damage;
    }
    // Default false where only StunEffect overrides this
    default boolean preventsAction() {
        return false;
    }
    // Default 0 where only ArcaneBlastEffect overrides this
    default int getAttackBonus() {
        return 0;
    }
    // Default 0 where only DefendEffect overrides this
    default int getDefenseBonus() {
        return 0;
    }
    boolean isExpired();
    String describe();
}
