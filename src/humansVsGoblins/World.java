package humansVsGoblins;

import humansVsGoblins.creatures.Creature;

/**
 * Represents a land generator. Each land it generates is more difficult than the previous.
 */
public class World {
	private LandSettings currentSettings;
	private Land currentLand;
	private Creature player;

	public World(Creature player) {
		currentSettings = new LandSettings();
		this.player = player;
		startNextFloor();
	}

	public void startNextFloor() {
		currentSettings.minimumEnemies++;
		currentSettings.maximumEnemies += RNG.inclusive(1, 2);
		currentSettings.maximumWalls += RNG.inclusive(0, 4);
		int minDimension = (int)Math.ceil(Math.sqrt(currentSettings.maximumEnemies + currentSettings.maximumWalls + 12));
		int maxDimension = (int)Math.ceil(Math.sqrt((currentSettings.maximumEnemies + currentSettings.maximumWalls + 17) * 3));
		currentSettings.width = Math.min(Math.max(RNG.inclusive(minDimension, maxDimension), 7), 60);
		currentSettings.height = Math.min(Math.max(RNG.inclusive(minDimension, maxDimension), 7), 30);
		// Land 1: Small
		// Land 2-6: Medium
		// Land 7-12: Large
		// Land 13-19: Sponge+Violent+Tank
		// Land 20-26: Warrior+Golem+Drastic
		// Land 27+: King (and King will become more and more likely)
		currentSettings.enemyRanking += 0.15 / currentSettings.enemyRanking + 0.1;
		currentLand = new Land(currentSettings);
		try {
			currentLand.placeDenizen(player);
		}
		catch(CapacityException e) {
			e.printStackTrace();
		}
	}

	public static Land getCurrentLand() {
		return Game.current().world.currentLand;
	}

	public static double getEnemyRanking() {
		return Game.current().world.currentSettings.enemyRanking;
	}

	@Override
	public String toString() {
		return currentLand.toString();
	}
}
