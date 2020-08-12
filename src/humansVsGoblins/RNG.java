package humansVsGoblins;

import java.util.List;

/**
 * Utility class for creating random numbers.
 */
public class RNG {
	public static int inclusive(int max) {
		return (int)Math.floor(Math.random() * (max + 1.0));
	}

	public static int inclusive(int min, int max) {
		return (int)Math.floor(min + Math.random() * (max + 1.0 - min));
	}

	public static int exclusive(int min, int max) {
		return (int)Math.floor(min + Math.random() * (max - min));
	}

	public static <T> T choose(T[] list) {
		return list[exclusive(list.length)];
	}

	public static int exclusive(int max) {
		return (int)Math.floor(Math.random() * (max));
	}

	public static <T> T choose(List<T> list) {
		return list.get(exclusive(list.size()));
	}

	public static boolean chance(double probability) {
		return (Math.random() < probability);
	}
}
