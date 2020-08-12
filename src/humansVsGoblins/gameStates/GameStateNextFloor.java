package humansVsGoblins.gameStates;

import humansVsGoblins.Game;

/**
 * A transition game state when a new land is being generated.
 */
public class GameStateNextFloor extends GameState {
	@Override
	public GameState evaluate() {
		System.out.println("\n\tNext Floor!\n");
		sleep(2000);
		Game.current().world.startNextFloor();
		Game.current().goingToNextFloor = false;
		return new GameStateMove();
	}
}
