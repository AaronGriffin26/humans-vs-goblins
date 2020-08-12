package humansVsGoblins.items;

import humansVsGoblins.creatures.Creature;

/**
 * A stronger version of the potion.
 */
public class ItemPotentPotion extends Item {
	@Override
	public String getName() {
		return "Potent Potion";
	}

	@Override
	public String getDescription() {
		return "+120 HP";
	}

	@Override
	public boolean useItem(Creature user) throws ItemException {
		if(user.getHP() == user.getMaxHP())
			throw new ItemException("Your HP is full.");
		user.heal(120);
		return true;
	}
}
