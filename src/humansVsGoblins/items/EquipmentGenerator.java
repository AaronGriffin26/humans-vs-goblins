package humansVsGoblins.items;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.creatures.Creature;
import humansVsGoblins.creatures.Human;

/**
 * Generates items that allow the player to get stronger based on the enemies they're fighting.
 */
public class EquipmentGenerator {
	public static ItemEquipment generate(Creature basis) {
		if(Game.current() == null)
			return null;
		Human player = Game.current().player;
		if(player == null)
			return null;
		double lifeSupportChance = (0.1 + (basis.getMaxHP() - player.getMaxHP()) / 1000.0);
		double statChance = (0.1 + (basis.getAttack() + basis.getDefense() - player.getAttack() - player.getDefense()) / 200.0);
		double swordChance = Math.min(0.1 + (basis.getDefense() - player.getDefense()) / 200.0, statChance);
		double shieldChance = Math.min(0.1 + (basis.getAttack() - player.getAttack()) / 200.0, statChance);
		if(RNG.chance(lifeSupportChance))
			return new ItemEquipment("Life Support", RNG.inclusive(1, 2) * 5, 0, 0);
		if(RNG.chance(swordChance))
			return new ItemEquipment("Sword", 0, RNG.inclusive(1, 5), 0);
		if(RNG.chance(shieldChance))
			return new ItemEquipment("Shield", 0, 0, RNG.inclusive(1, 5));
		return null;
	}
}
