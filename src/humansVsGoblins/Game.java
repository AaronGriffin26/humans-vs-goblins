package humansVsGoblins;

import humansVsGoblins.creatures.Human;
import humansVsGoblins.gameStates.GameState;
import humansVsGoblins.gameStates.GameStateMove;
import humansVsGoblins.items.Item;

import java.util.ArrayList;

/**
 * The main class that contains everything in the program.
 */
public class Game {
	// UTF-8 codes for console colors
	public static final String RED_TEXT = "\033[0;91m";
	public static final String YELLOW_TEXT = "\033[0;93m";
	public static final String GREEN_TEXT = "\033[0;92m";
	public static final String DIM_TEXT = "\033[0;90m";
	public static final String NORMAL_TEXT = "\033[0m";

	private static Game main;
	public final Human player;
	public final World world;
	public boolean goingToNextFloor;
	private boolean gameOver;
	private StringBuilder pendingMessages = new StringBuilder();

	public Game() {
		player = new Human();
		world = new World(player);
	}

	public static Game current() {
		return main;
	}

	public static void main(String[] args) {
		main = new Game();
		main.run();
	}

	private void run() {
		GameState state = new GameStateMove();
		while(state != null) {
			displayState();
			if(gameOver)
				state = null;
			else
				state = state.evaluate();
		}
	}

	private void displayState() {
		StringBuilder view = new StringBuilder();
		view.append(world);
		ArrayList<String> rightColumn = new ArrayList<>();
		rightColumn.add(getHealthBar());
		rightColumn.add(player.getStats());
		Item[] items = player.getInventory();
		for(int i = 0; i < items.length; i++) {
			Item item = items[i];
			rightColumn.add("I" + (i + 1) + ". " + item.getName() + " (" + item.getDescription() + ")");
		}
		addColumnTo(view, rightColumn);
		System.out.print("\n" + view + pendingMessages);
		pendingMessages = new StringBuilder();
	}

	private String getHealthBar() {
		var healthBar = new StringBuilder();
		int hp = player.getHP();
		int maxHP = player.getMaxHP();
		if(hp > 0)
			healthBar.append("@");
		else
			healthBar.append(" ");
		healthBar.append(" HP: ").append(hp).append(" / ").append(maxHP).append(" ");
		if(hp <= maxHP / 4)
			healthBar.append(RED_TEXT);
		else if(hp <= maxHP / 2)
			healthBar.append(YELLOW_TEXT);
		else
			healthBar.append(GREEN_TEXT);
		for(int i = 0; i < maxHP; i += 10) {
			if(hp - i >= 10)
				healthBar.append("█");
			else if(hp - i >= 5)
				healthBar.append("▓");
			else if(hp - i >= 1)
				healthBar.append("▒");
			else
				healthBar.append(DIM_TEXT + "░");
		}
		healthBar.append(NORMAL_TEXT);
		return healthBar.toString();
	}

	private void addColumnTo(StringBuilder view, ArrayList<String> column) {
		int fromIndex = view.indexOf("\n");
		for(String s : column) {
			view.insert(fromIndex, "  " + s);
			fromIndex = view.indexOf("\n", fromIndex);
			fromIndex = view.indexOf("\n", fromIndex + 1);
			if(fromIndex == -1)
				fromIndex = view.length();
		}
		if(view.charAt(view.length() - 1) != '\n')
			view.append('\n');
	}

	public void appendMessage(String message) {
		pendingMessages.append(message).append("\n");
	}

	public void gameOver() {
		gameOver = true;
	}
}