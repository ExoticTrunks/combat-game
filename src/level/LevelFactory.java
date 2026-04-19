package level;

import domain.Enemy;
import domain.Goblin;
import domain.Wolf;

import java.util.List;
import java.util.ArrayList;

public class LevelFactory {
	
	public static List<List<Enemy>> spawnAndGetAllEnemyWaves(DifficultyLevel difficultyLevel){
		List<List<Enemy>> allEnemies = new ArrayList<List<Enemy>>();
		
		char nameLetter = 'A';
		switch(difficultyLevel) {
		case EASY:
			allEnemies.add(new ArrayList<Enemy>());
			for(int i = 0; i < 3; i++) {
				allEnemies.get(0).add(new Goblin("" + nameLetter++));
			}
			break;
			
		case MEDIUM:
			allEnemies.add(new ArrayList<Enemy>());
			allEnemies.add(new ArrayList<Enemy>());
			
			allEnemies.get(0).add(new Goblin("" + nameLetter));
			allEnemies.get(0).add(new Wolf("" + nameLetter++));
			
			for(int i = 0; i < 2; i++) {
				allEnemies.get(1).add(new Wolf("" + nameLetter++));
			}
			break;

		case HARD:
			allEnemies.add(new ArrayList<Enemy>());
			allEnemies.add(new ArrayList<Enemy>());
			

			for(int i = 0; i < 2; i++) {
				allEnemies.get(0).add(new Goblin("" + nameLetter++));
			}

			allEnemies.get(1).add(new Goblin("" + nameLetter++));
			nameLetter = 'A';
			for(int i = 0; i < 2; i++) {
				allEnemies.get(1).add(new Wolf("" + nameLetter++));
			}
			break;
		}
			
		return allEnemies;
	}
    public static LevelConfig easy() {
        return new LevelConfig(DifficultyLevel.EASY, spawnAndGetAllEnemyWaves(DifficultyLevel.EASY));
    }

    public static LevelConfig medium() {
        return new LevelConfig(DifficultyLevel.MEDIUM, spawnAndGetAllEnemyWaves(DifficultyLevel.MEDIUM));
    }

    public static LevelConfig hard() {
        return new LevelConfig(DifficultyLevel.HARD, spawnAndGetAllEnemyWaves(DifficultyLevel.HARD));
    }
}
