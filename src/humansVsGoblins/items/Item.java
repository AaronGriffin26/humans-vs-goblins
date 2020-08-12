package humansVsGoblins.items;

import humansVsGoblins.creatures.Creature;

/**
 * Base class for consumable objects.
 */
public abstract class Item {
	public abstract String getName();

	public abstract String getDescription();

	public abstract boolean useItem(Creature user) throws ItemException;
}
