package domain;

import effects.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCombatant implements Combatant {
    private final String name;
    private final int maxHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int speed;

    private int hp;
    private int specialCooldownRemaining;
    private final List<StatusEffect> statusEffects;

    protected AbstractCombatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.baseAttack = attack;
        this.baseDefense = defense;
        this.speed = speed;
        this.hp = maxHp;
        this.specialCooldownRemaining = 0;
        this.statusEffects = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getMaxHp() {
        return maxHp;
    }
    @Override
    public int getHp() {
        return hp;
    }
    @Override
    public int getAttack() {
        // to complete
    }
    @Override
    public int getDefense() {
        // To complete
    }
    @Override
    public int getSpeed() {
        return speed;
    }
    @Override
    public boolean isAlive() {
        return hp > 0;
    }
    @Override
    public boolean canAct() {
        if (!isAlive()) {
            return false;
        }
        for (StatusEffect effect : statusEffects) {
            if (effect.preventsAction() && !effect.isExpired()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }
    @Override
    public void takeDamage(int amount) {
        int finalDamage = Math.max(0, modifyIncomingDamage(amount));
        hp = Math.max(0, hp - finalDamage);
    }
    @Override
    public int modifyIncomingDamage(int damage) {
        int current = damage;
        for (StatusEffect effect : statusEffects) {
            current = effect.modifyIncomingDamage(this, current);
        }
        return Math.max(0, current);
    }
    @Override
    public boolean isInvulnerable() {
        return modifyIncomingDamage(1) == 0;
    }
    @Override
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }
    @Override
    public void addStatusEffect(StatusEffect effect) {
        effect.onApply(this);
        statusEffects.add(effect);
        removeExpiredEffects();
    }
    @Override
    public void startTurn() {
        for (StatusEffect effect : statusEffects) {
            effect.onOwnerTurnStart(this);
        }
        removeExpiredEffects();
    }
    @Override
    public void endTurn() {
        for (StatusEffect effect : statusEffects) {
            effect.onOwnerTurnEnd(this);
        }
        removeExpiredEffects();
        reduceSpecialCooldownIfNeeded();
    }
    @Override
    public int getSpecialCooldownRemaining() {
        return specialCooldownRemaining;
    }
    @Override
    public void setSpecialCooldownRemaining(int turns) {
        specialCooldownRemaining = Math.max(0, turns);
    }
    @Override
    public void reduceSpecialCooldownIfNeeded() {
        if (specialCooldownRemaining > 0) {
            specialCooldownRemaining--;
        }
    }
    private void removeExpiredEffects() {
        // To complete
    }
    public String statusSummary() {
        // To complete
    }
}
