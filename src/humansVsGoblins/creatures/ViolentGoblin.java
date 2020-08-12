package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemPotentPotion;
import humansVsGoblins.items.ItemPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * Known for their high attack.
 */
public class ViolentGoblin extends Goblin {
	public ViolentGoblin() {
		setMaxHP(100);
		setAttack(50);
		setDefense(0);
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
		return "Violent Goblin";
	}

	@Override
	public String toString() {
		return Game.GREEN_TEXT + "V" + Game.NORMAL_TEXT;
	}
}
