package humansVsGoblins.landmarks;

import humansVsGoblins.Game;
import humansVsGoblins.creatures.Creature;
import humansVsGoblins.items.Item;

/**
 * Landmark that gives the player an item when stepped on.
 */
public class LandmarkItem extends Landmark {
	private Item item;

	public LandmarkItem(Item item) {
		this.item = item;
	}

	@Override
	public boolean interact(Creature creature) {
		boolean received = creature.getItem(item);
		if(!received && creature == Game.current().player)
			Game.current().appendMessage("Your inventory is full! You could not get the " + item.getName() + ".");
		return received;
	}

	@Override
	public String toString() {
		return "▲";
	}
}
