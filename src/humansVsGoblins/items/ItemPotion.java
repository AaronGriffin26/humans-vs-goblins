package humansVsGoblins.items;

import humansVsGoblins.creatures.Creature;

/**
 * An item that restores the player's health points.
 */
public class ItemPotion extends Item {
	@Override
	public String getName() {
		return "Potion";
	}

	@Override
	public String getDescription() {
		return "+50 HP";
	}

	@Override
	public boolean useItem(Creature user) throws ItemException {
		if(user.getHP() == user.getMaxHP())
			throw new ItemException("Your HP is full.");
		user.heal(50);
		return true;
	}
}
