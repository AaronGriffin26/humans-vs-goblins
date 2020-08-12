package humansVsGoblins.creatures;

import humansVsGoblins.CardinalDirection;
import humansVsGoblins.items.ItemPotion;

/**
 * Represents the player character.
 */
public class Human extends Creature {
	private CardinalDirection nextMove = CardinalDirection.None;

	public Human() {
		setMaxHP(100);
		setAttack(25);
		setDefense(5);
		setCapacity(8);
		getItem(new ItemPotion());
	}

	@Override
	public String getName() {
		return "You";
	}

	@Override
	public CardinalDirection getNextMove() {
		return nextMove;
	}

	public void setNextMove(CardinalDirection move) {
		nextMove = move;
	}

	@Override
	public String toString() {
		return "@";
	}
}
