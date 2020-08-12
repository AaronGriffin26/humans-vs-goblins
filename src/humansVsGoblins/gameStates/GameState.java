package humansVsGoblins.gameStates;

import humansVsGoblins.Land;

/**
 * Base class for controlling the game flow.
 */
public abstract class GameState {
	protected Land land;

	public GameState() {
		land = Land.current();
	}

	public abstract GameState evaluate();

	protected void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}
		catch(InterruptedException ignored) {
		}
	}
}
