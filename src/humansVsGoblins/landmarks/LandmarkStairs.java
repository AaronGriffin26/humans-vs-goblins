package humansVsGoblins.landmarks;

import humansVsGoblins.Game;
import humansVsGoblins.creatures.Creature;

import java.util.Scanner;

/**
 * Landmark that allows the player to advance to a more difficult land.
 */
public class LandmarkStairs extends Landmark {
	@Override
	public boolean interact(Creature creature) {
		if(creature == Game.current().player)
			Game.current().goingToNextFloor = getPlayerMove();
		return false;
	}

	public boolean getPlayerMove() {
		while(true) {
			System.out.print("Would you like to advance to the next level? (y/n) ");
			var scanner = new Scanner(System.in);
			String s = scanner.nextLine().toLowerCase();
			if(s.matches("|y|yes"))
				return true;
			if(s.matches("n|no"))
				return false;
		}
	}

	@Override
	public String toString() {
		return "╗";
	}
}
