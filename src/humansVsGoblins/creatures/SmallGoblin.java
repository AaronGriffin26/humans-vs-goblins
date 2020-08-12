package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemBomb;
import humansVsGoblins.items.ItemPotion;

/**
 * The weakest of all the goblins.
 */
public class SmallGoblin extends Goblin {
	public SmallGoblin() {
		setMaxHP(50);
		setAttack(20);
		setDefense(0);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.1f))
			getItem(new ItemBomb());
		if(RNG.chance(0.2f))
			getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "Small Goblin";
	}

	@Override
	public String toString() {
		return Game.GREEN_TEXT + "G" + Game.NORMAL_TEXT;
	}
}
