package level;

import java.util.List;
import java.util.function.Supplier;

import domain.Enemy;

//TODO: (E) Change classdiagram for this
public class LevelConfig {
	private DifficultyLevel difficulty;
	private List<List<Enemy>> waves;
	private int currentWave = 0;
//	public int MAXWAVES = 99;
	
    public LevelConfig(DifficultyLevel difficulty, List<List<Enemy>> waves) {
        this.difficulty = difficulty;
        this.waves = waves;
    }
    
    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public String getDifficultyName() {
        return difficulty.name().charAt(0) + difficulty.name().substring(1).toLowerCase();
    }

    public List<Enemy> getAndProgressWave() {
    	if (hasBackupWave()) {
    		return waves.get(currentWave++);
    	}
    	return null;
    }
    
    public boolean hasBackupWave() {
    	return currentWave <= waves.size();
    }
}
