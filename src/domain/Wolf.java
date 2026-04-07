package domain;

public class Wolf extends Enemy {
    public Wolf(String suffix) {
      // Same as the Goblin, The super() call passes everything to the Enemy then AbstractCombatant constructor
        super(suffix == null || suffix.isBlank() ? "Wolf" : "Wolf " + suffix,
                40, 45, 5, 35);
    }
}
