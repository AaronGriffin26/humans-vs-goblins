package humansVsGoblins.creatures;

import humansVsGoblins.CardinalDirection;
import humansVsGoblins.Land;
import humansVsGoblins.items.Item;
import humansVsGoblins.items.ItemException;
import humansVsGoblins.items.ItemRevive;

import java.awt.*;
import java.util.ArrayList;

/**
 * Base class for anything that can move and attack.
 */
public abstract class Creature {
	private Point position = new Point(0, 0);
	private int hp;
	private int maxHP;
	private int attack;
	private int defense;
	private int turnsSinceHit;
	private int inventoryCapacity = 1;
	private ArrayList<Item> inventory = new ArrayList<>();

	public abstract String getName();

	public Point getPosition() { return new Point(position); }

	public void setPosition(Point newPosition) {
		if(turnsSinceHit > 1) {
			if(newPosition.equals(position))
				hp++;
			hp++;
			hp = Math.min(hp, maxHP);
		}
		position = new Point(newPosition);
		turnsSinceHit++;
	}

	public int getHP() {
		return hp;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int newMax) {
		hp += newMax - maxHP;
		maxHP = newMax;
	}

	public boolean isDead() { return (hp <= 0); }

	public int getAttack() {
		return attack;
	}

	public void setAttack(int newAttack) {
		attack = newAttack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int newDefense) {
		defense = newDefense;
	}

	public String getStats() {
		return "Atk: " + attack + ", Def: " + defense;
	}

	public void setCapacity(int newCapacity) {
		inventoryCapacity = newCapacity;
	}

	public void attack(Creature enemy) {
		enemy.hp -= Math.max(0, attack - enemy.defense);
		enemy.turnsSinceHit = 0;
		if(enemy.hp < 0)
			enemy.hp = 0;
	}

	public abstract CardinalDirection getNextMove();

	public void heal(int hpGain) {
		hp = Math.min(maxHP, hp + hpGain);
	}

	public boolean getItem(Item item) {
		if(item == null)
			return false;
		if(inventory.size() < inventoryCapacity) {
			inventory.add(item);
			return true;
		}
		return false;
	}

	public void tryToUseRevive() {
		for(Item i : getInventory()) {
			if(i instanceof ItemRevive) {
				try {
					useItem(i);
					break;
				}
				catch(ItemException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Item[] getInventory() {
		return inventory.toArray(new Item[0]);
	}

	public void useItem(Item item) throws ItemException {
		boolean discard = item.useItem(this);
		if(discard)
			inventory.remove(item);
	}

	protected boolean wallAtDirection(CardinalDirection direction) {
		Point newPosition = CardinalDirection.addToPoint(position, direction);
		return Land.current().positionIsSolid(newPosition);
	}
}
