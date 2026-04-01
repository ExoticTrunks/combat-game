package domain;
import effects.StatusEffect;
import java.util.List;

public interface Combatant {
    String getName();
    int getMaxHp();
    int getHp();
    int getAttack();
    int getDefense();
    int getSpeed();
    boolean isAlive();
    boolean canAct();
    boolean isPlayer();

    List<StatusEffect> getStatusEffects();

    void takeDamage(int amount);
    void heal(int amount);
    void addStatusEffect(StatusEffect effect);
    void startTurn();
    void endTurn();
    int getSpecialCooldownRemaining();
    void setSpecialCooldownRemaining(int turns);
    void reduceSpecialCooldownIfNeeded();
    int modifyIncomingDamage(int damage);
    boolean isInvulnerable();
}
