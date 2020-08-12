package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemPotentPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * The final boss of the game. Has all-around high stats.
 */
public class KingGoblin extends Goblin {
	public KingGoblin() {
		setMaxHP(250);
		setAttack(60);
		setDefense(30);
		setCapacity(3);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.2f))
			getItem(new ItemRevive());
		if(RNG.chance(0.25f))
			getItem(new ItemPotentPotion());
	}

	@Override
	public String getName() {
		return "King Goblin";
	}

	@Override
	public String toString() {
		return Game.RED_TEXT + "K" + Game.NORMAL_TEXT;
	}
}
