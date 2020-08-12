package humansVsGoblins.creatures;

import humansVsGoblins.CardinalDirection;
import humansVsGoblins.Land;
import humansVsGoblins.RNG;

import java.awt.*;
import java.util.ArrayList;

/**
 * Base class for all the enemies in the game.
 */
public abstract class Goblin extends Creature {
	@Override
	public CardinalDirection getNextMove() {
		var list = new ArrayList<CardinalDirection>();
		for(CardinalDirection c : CardinalDirection.values()) {
			if(!wallAtDirection(c))
				list.add(c);
		}
		if(list.size() == 0)
			return CardinalDirection.None;
		for(CardinalDirection c : list) {
			if(playerAtDirection(c)) {
				return c;
			}
		}
		for(CardinalDirection c : list) {
			if(playerNearSpot(c) && !denizenAtDirection(c)) {
				return c;
			}
		}
		return RNG.choose(list);
	}

	private boolean playerAtDirection(CardinalDirection direction) {
		Point newPosition = CardinalDirection.addToPoint(getPosition(), direction);
		return (Land.current().playerIsAtPosition(newPosition));
	}

	private boolean playerNearSpot(CardinalDirection direction) {
		Point newPosition = CardinalDirection.addToPoint(getPosition(), direction);
		return (Land.current().playerIsNearPosition(newPosition));
	}

	private boolean denizenAtDirection(CardinalDirection direction) {
		Point newPosition = CardinalDirection.addToPoint(getPosition(), direction);
		return (Land.current().getDenizenAtPosition(newPosition) != null);
	}
}
