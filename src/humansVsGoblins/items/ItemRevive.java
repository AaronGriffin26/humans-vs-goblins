package humansVsGoblins.items;

import humansVsGoblins.creatures.Creature;

/**
 * Item that is automatically used when the player's HP hits 0.
 */
public class ItemRevive extends Item {
	@Override
	public String getName() {
		return "Revive";
	}

	@Override
	public String getDescription() {
		return "+30 HP on death";
	}

	@Override
	public boolean useItem(Creature user) throws ItemException {
		if(user.getHP() == 0) {
			user.heal(30);
			return true;
		}
		return false;
	}
}
