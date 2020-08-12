package humansVsGoblins.creatures;

import humansVsGoblins.Game;
import humansVsGoblins.RNG;
import humansVsGoblins.items.EquipmentGenerator;
import humansVsGoblins.items.ItemPotentPotion;
import humansVsGoblins.items.ItemPotion;
import humansVsGoblins.items.ItemRevive;

/**
 * Known for their high HP and attack.
 */
public class WarriorGoblin extends Goblin {
	public WarriorGoblin() {
		setMaxHP(160);
		setAttack(55);
		setDefense(5);
		setCapacity(2);
		getItem(EquipmentGenerator.generate(this));
		if(RNG.chance(0.1f))
			getItem(new ItemPotentPotion());
		if(RNG.chance(0.1f))
			getItem(new ItemRevive());
		if(RNG.chance(0.5f))
			getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "Warrior Goblin";
	}

	@Override
	public String toString() {
		return Game.YELLOW_TEXT + "W" + Game.NORMAL_TEXT;
	}
}
