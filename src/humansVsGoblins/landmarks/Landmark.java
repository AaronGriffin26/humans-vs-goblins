package humansVsGoblins.landmarks;

import humansVsGoblins.creatures.Creature;

/**
 * Base class for anything that is on the ground. Cannot overlap each other.
 */
public abstract class Landmark {
	public boolean isSolid() {
		return false;
	}

	public abstract boolean interact(Creature creature);
}
