// this will be the interface for all the effects to be built upon
package sc2002.arena.effects;
public interface StatusEffect {
    String getName();
    void tick();
    boolean isExpired();
    default int getAttackModifier() { return 0; }
    default int getDefenseModifier() { return 0; }
    default boolean preventsAction() { return false; }
    default boolean grantsInvulnerability() { return false; }
}
