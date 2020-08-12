package humansVsGoblins.items;

/**
 * Contains a special message for the player. Blocks code that anticipated a used item.
 */
public class ItemException extends Exception {
	public ItemException(String s) {
		super(s);
	}
}
