package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * A Goblin that's stronger than the Medium Goblin.
 */
public class LargeGoblin extends Goblin {
	public LargeGoblin() {
		setMaxHP(100);
		setAttack(35);
		setDefense(10);
		setCapacity(2);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.1f))
			getItem(new ItemRevive());
		if(RNG.chance(0.3f))
			getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "Large Goblin";
	}

	@Override
	public String toString() {
		return Game.GREEN_TEXT + "L" + Game.NORMAL_TEXT;
	}
}
