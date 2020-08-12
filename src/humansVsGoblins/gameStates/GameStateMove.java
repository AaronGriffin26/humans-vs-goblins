package humansVsGoblins.gameStates;

import humansVsGoblins.*;
import humansVsGoblins.creatures.Creature;
import humansVsGoblins.creatures.Human;
import humansVsGoblins.items.Item;
import humansVsGoblins.items.ItemException;
import humansVsGoblins.landmarks.Landmark;

import java.awt.*;
import java.util.Scanner;

/**
 * Game state that allows all denizens of a land to move. The player can also use items.
 */
public class GameStateMove extends GameState {
	private Human player;
	private boolean decidedOnMove;
	private CardinalDirection chosenDirection = CardinalDirection.None;
	private String optionsString;

	public GameStateMove() {
		player = Game.current().player;
		if(land.getDenizens().length < 2) {
			LandSettings settings = new LandSettings();
			settings.minimumEnemies = 1;
			settings.maximumEnemies = 1;
			settings.enemyRanking = World.getEnemyRanking();
			land.spawnGoblins(settings);
		}
	}

	@Override
	public GameState evaluate() {
		player.setNextMove(getPlayerMove());

		for(Creature c : land.getDenizens()) {
			if(c.isDead())
				Land.current().removeDenizen(c);
			else
				denizenUpdate(c);
		}
		if(Game.current().goingToNextFloor)
			return new GameStateNextFloor();
		return new GameStateMove();
	}

	public CardinalDirection getPlayerMove() {
		inspectPlayerOptions();
		System.out.print(optionsString);
		while(!decidedOnMove) {
			var scanner = new Scanner(System.in);
			String s = scanner.nextLine().toLowerCase();
			if(s.equals("") && playerNearEnemy()) {
				sleep(500);
				System.out.print("Type 'p' to pass turn. " + optionsString);
			}
			else {
				if(s.startsWith("i"))
					decideOnItem(s);
				else
					decideOnDirection(s);
				if(!decidedOnMove)
					System.out.print(optionsString);
			}
		}
		return chosenDirection;
	}

	private void inspectPlayerOptions() {
		Item[] items = player.getInventory();
		StringBuilder builder = new StringBuilder();
		builder.append("Move: n/s/e/w");
		if(player.getHP() < player.getMaxHP())
			builder.append(", hold Enter to heal");
		if(items.length > 0)
			builder.append(", Item: iⁿ");
		optionsString = builder.toString() + ": ";
	}

	private boolean playerNearEnemy() {
		return Land.current().denizenIsNearPosition(player.getPosition());
	}

	private void decideOnItem(String input) {
		Item[] items = player.getInventory();
		if(items.length > 0) {
			if(input.length() == 1) {
				System.out.print("Which item to use? 1 to " + items.length + ": ");
				var scanner = new Scanner(System.in);
				input = "i" + scanner.nextLine().toLowerCase();
			}
			try {
				int index = Integer.parseInt(input.substring(1)) - 1;
				if(index >= 0 && index < items.length) {
					player.useItem(items[index]);
					decidedOnMove = true;
				}
				else
					System.out.println("Please enter I and then a number from 1 to " + items.length + ".");
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter I and then a number from 1 to " + items.length + ".");
			}
			catch(ItemException e) {
				System.out.println(e.getMessage());
			}
		}
		else
			System.out.println("You do not have any items.");
	}

	private void decideOnDirection(String input) {
		if(input.matches("| |p|pass"))
			decidedOnMove = true;
		else if(input.matches("n|north|u|up|8"))
			chosenDirection = CardinalDirection.North;
		else if(input.matches("s|south|d|dn|down|2"))
			chosenDirection = CardinalDirection.South;
		else if(input.matches("e|east|r|right|6"))
			chosenDirection = CardinalDirection.East;
		else if(input.matches("w|west|l|left|4"))
			chosenDirection = CardinalDirection.West;
		if(chosenDirection != CardinalDirection.None)
			decidedOnMove = true;
	}

	private void denizenUpdate(Creature denizen) {
		CardinalDirection nextMove = denizen.getNextMove();
		Point position = CardinalDirection.addToPoint(denizen.getPosition(), nextMove);
		if(!Land.current().positionIsInBounds(position))
			position = denizen.getPosition();
		Landmark landmark = Land.current().getLandmarkAtPosition(position);
		Creature enemy = Land.current().getDenizenAtPosition(position);
		if(enemy == denizen)
			enemy = null;
		boolean canMove = (enemy == null);
		if(landmark != null && landmark.isSolid()) {
			canMove = false;
			performInteraction(denizen, position);
		}
		performAttack(denizen, enemy);
		if(canMove) {
			denizen.setPosition(position);
			performInteraction(denizen, position);
		}
	}

	private void performInteraction(Creature user, Point position) {
		Landmark landmark = Land.current().getLandmarkAtPosition(position);
		if(landmark != null) {
			boolean taken = landmark.interact(user);
			if(taken)
				Land.current().removeLandmark(position);
		}
	}

	private void performAttack(Creature attacker, Creature defender) {
		if(defender == null)
			return;
		if(attacker != defender && (attacker == player || defender == player))
			attacker.attack(defender);
		if(defender.isDead() && defender instanceof Human)
			defender.tryToUseRevive();
		if(defender.isDead()) {
			Land.current().removeDenizen(defender);
			if(defender == player) {
				Game.current().appendMessage("You were killed by a " + attacker.getName() + ".");
				Game.current().gameOver();
			}
		}
	}
}
