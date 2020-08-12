package humansVsGoblins;

import java.awt.*;

/**
 * Enumeration for all possible directions in the game. Contains helper methods for positioning.
 */
public enum CardinalDirection {
	None,
	North,
	South,
	East,
	West;

	public static Point addToPoint(Point point, CardinalDirection direction) {
		Point destination = new Point(point);
		destination.x += evaluateX(direction);
		destination.y += evaluateY(direction);
		return destination;
	}

	public static int evaluateX(CardinalDirection direction) {
		switch(direction) {
			case East:
				return 1;
			case West:
				return -1;
			default:
				return 0;
		}
	}

	public static int evaluateY(CardinalDirection direction) {
		switch(direction) {
			case South:
				return 1;
			case North:
				return -1;
			default:
				return 0;
		}
	}
}
