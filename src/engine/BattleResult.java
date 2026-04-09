package engine;

public class BattleResult {
	private boolean victory;
	private int totalRounds;
	private int remainingHpOrEnemies;
	
	public BattleResult(boolean victory, int totalRounds, int remainingHpOrEnemies) {
		this.victory = victory;
		this.totalRounds = totalRounds;
		this.remainingHpOrEnemies = remainingHpOrEnemies;
	}
	
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
