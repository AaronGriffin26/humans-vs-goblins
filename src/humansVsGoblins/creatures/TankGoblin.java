package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemPotentPotion;
import humansVsGoblins.items.ItemPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * Known for their high defense.
 */
public class TankGoblin extends Goblin {
	public TankGoblin() {
		setMaxHP(100);
		setAttack(30);
		setDefense(20);
		setCapacity(2);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.02f))
			getItem(new ItemPotentPotion());
		if(RNG.chance(0.1f))
			getItem(new ItemRevive());
		if(RNG.chance(0.4f))
			getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "Tank Goblin";
	}

	@Override
	public String toString() {
		return Game.GREEN_TEXT + "T" + Game.NORMAL_TEXT;
	}
}
