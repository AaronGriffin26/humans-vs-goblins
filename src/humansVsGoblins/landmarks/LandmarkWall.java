package humansVsGoblins.landmarks;

import humansVsGoblins.creatures.Creature;

/**
 * Landmark that prevents creatures from moving. Can be broken down in 3 hits.
 */
public class LandmarkWall extends Landmark {
	private static final String[] FACES = { "▒", "▓", "█" };
	private int hp = 3;

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean interact(Creature creature) {
		hp--;
		return (hp <= 0);
	}

	@Override
	public String toString() {
		return FACES[hp - 1];
	}

}
