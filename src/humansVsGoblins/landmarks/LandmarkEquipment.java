package humansVsGoblins.landmarks;

import humansVsGoblins.Game;
import humansVsGoblins.creatures.Creature;
import humansVsGoblins.items.ItemEquipment;
import humansVsGoblins.items.ItemException;

/**
 * A variant of LandmarkItem where equipment is immediately used instead of being put in an inventory.
 */
public class LandmarkEquipment extends Landmark {
	private ItemEquipment equipment;

	public LandmarkEquipment(ItemEquipment equipment) {
		this.equipment = equipment;
	}

	@Override
	public boolean interact(Creature creature) {
		if(creature == Game.current().player) {
			try {
				creature.useItem(equipment);
			}
			catch(ItemException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "♦";
	}
}
