package humansVsGoblins.items;

import humansVsGoblins.Game;
import humansVsGoblins.creatures.Creature;

/**
 * Item that is guaranteed to be stronger than the equipment the player is using.
 */
public class ItemEquipment extends Item {
	private String name;
	private int hp;
	private int attack;
	private int defense;

	public ItemEquipment(String name, int hp, int attack, int defense) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return "autoequip";
	}

	@Override
	public boolean useItem(Creature user) {
		if(user == Game.current().player) {
			StringBuilder builder = new StringBuilder();
			builder.append("You equipped a new ").append(getName()).append(". (");
			if(hp > 0) {
				user.setMaxHP(user.getMaxHP() + hp);
				builder.append("+").append(hp).append(" HP");
			}
			if(attack > 0) {
				user.setAttack(user.getAttack() + attack);
				builder.append("+").append(attack).append(" Attack");
			}
			if(defense > 0) {
				user.setDefense(user.getDefense() + defense);
				builder.append("+").append(defense).append(" Defense");
			}
			Game.current().appendMessage(builder + ")");
		}
		return true;
	}
}
