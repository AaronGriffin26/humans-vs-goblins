package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemBomb;
import humansVsGoblins.items.ItemPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * A goblin that is strong than the Small Goblin.
 */
public class MediumGoblin extends Goblin {
	public MediumGoblin() {
		setMaxHP(70);
		setAttack(25);
		setDefense(5);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.05f))
			getItem(new ItemRevive());
		if(RNG.chance(0.1f))
			getItem(new ItemBomb());
		if(RNG.chance(0.25f))
			getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "Medium Goblin";
	}

	@Override
	public String toString() {
		return Game.GREEN_TEXT + "M" + Game.NORMAL_TEXT;
	}
}
