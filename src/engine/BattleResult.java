package engine;

public class BattleResult {
	private boolean victory;
	private int totalRounds;
	private int remainingHpOrEnemies;
	
    public boolean isVictory() {
        return victory;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getRemainingHpOrEnemies() {
        return remainingHpOrEnemies;
    }
}
