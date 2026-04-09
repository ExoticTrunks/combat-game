package effects;

import domain.Combatant;

public class ArcaneBlastEffect implements StatusEffect {
    private final int amount;

    public ArcaneBlastEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public String getName() {
        return "Arcane Blast Attack Buff";
    }

    @Override
    public void onApply(Combatant owner) {
        // Permanent until end of level.
    }

    @Override
    public int getAttackBonus() {
        return amount;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public String describe() {
        return "ArcaneBuff(+" + amount + " ATK until end level)";
    }
}
