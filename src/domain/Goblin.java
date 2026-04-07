package domain;

public class Goblin extends Enemy {
    public Goblin(String suffix) {
      // The super() call passes everything to the Enemy then AbstractCombatant constructor
        super(suffix == null || suffix.isBlank() ? "Goblin" : "Goblin " + suffix,
                55, 35, 15, 25);
    }
}
