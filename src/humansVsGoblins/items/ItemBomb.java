package humansVsGoblins.items;

import humansVsGoblins.Land;
import humansVsGoblins.creatures.Creature;

/**
 * Item that lets the player attack in all directions for double damage.
 */
public class ItemBomb extends Item {
	@Override
	public String getName() {
		return "Bomb";
	}

	@Override
	public String getDescription() {
		return "attack around you twice";
	}

	@Override
	public boolean useItem(Creature user) {
		for(Creature c : Land.current().getDenizensNearPosition(user.getPosition())) {
			if(c != null) {
				user.attack(c);
				user.attack(c);
			}
		}
		return true;
	}
}
