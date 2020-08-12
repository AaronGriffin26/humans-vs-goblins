package humansVsGoblins;

import humansVsGoblins.creatures.*;
import humansVsGoblins.items.Item;
import humansVsGoblins.items.ItemEquipment;
import humansVsGoblins.landmarks.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A container for all landmarks and creatures. It uses LandSettings to generate itself.
 */
public class Land {
	private final int objectLimit;
	private ArrayList<Creature> denizens;
	private Landmark[][] landmarks;
	private int objectTotal;

	public Land(LandSettings settings) {
		denizens = new ArrayList<>();
		landmarks = new Landmark[settings.width][settings.height];
		objectLimit = settings.width * settings.height;
		try {
			placeLandmark(new LandmarkStairs());
		}
		catch(CapacityException e) {
			e.printStackTrace();
		}
		spawnWalls(settings);
		spawnGoblins(settings);
	}

	public static Land current() {
		return World.getCurrentLand();
	}

	public void spawnGoblins(LandSettings settings) {
		int enemyCount = RNG.inclusive(settings.minimumEnemies, settings.maximumEnemies);
		for(int i = 0; i < enemyCount; i++) {
			try {
				int ranking = (int)(Math.random() * settings.enemyRanking);
				placeDenizen(getRankedGoblin(ranking));
			}
			catch(CapacityException e) {
				e.printStackTrace();
			}
		}
	}

	private Creature getRankedGoblin(int ranking) {
		switch(ranking) {
			case 0:
				return new SmallGoblin();
			case 1:
				return new MediumGoblin();
			case 2:
				return new LargeGoblin();
			case 3:
				return RNG.choose(new Goblin[] { new ViolentGoblin(), new TankGoblin(), new SpongeGoblin() });
			case 4:
				return RNG.choose(new Goblin[] { new WarriorGoblin(), new GolemGoblin(), new DrasticGoblin() });
			default:
				return new KingGoblin();
		}
	}

	private void spawnWalls(LandSettings settings) {
		int wallCount = RNG.inclusive(settings.minimumWalls, settings.maximumWalls);
		for(int i = 0; i < wallCount; i++) {
			try {
				placeLandmark(new LandmarkWall());
			}
			catch(CapacityException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean denizenIsNearPosition(Point position) {
		if(denizenIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.North)))
			return true;
		if(denizenIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.South)))
			return true;
		if(denizenIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.East)))
			return true;
		return (denizenIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.West)));
	}

	public boolean denizenIsAtPosition(Point position) {
		return (getDenizenAtPosition(position) != null);
	}

	public Creature getDenizenAtPosition(Point position) {
		for(Creature c : denizens) {
			if(c.getPosition().equals(position))
				return c;
		}
		return null;
	}

	public boolean playerIsNearPosition(Point position) {
		if(playerIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.North)))
			return true;
		if(playerIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.South)))
			return true;
		if(playerIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.East)))
			return true;
		return (playerIsAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.West)));
	}

	public boolean playerIsAtPosition(Point position) {
		if(Game.current() == null || Game.current().player == null)
			return false;
		Creature creature = getDenizenAtPosition(position);
		if(creature != null)
			return (creature == Game.current().player);
		return false;
	}

	public Creature[] getDenizensNearPosition(Point position) {
		ArrayList<Creature> creatures = new ArrayList<>();
		creatures.add(getDenizenAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.North)));
		creatures.add(getDenizenAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.South)));
		creatures.add(getDenizenAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.East)));
		creatures.add(getDenizenAtPosition(CardinalDirection.addToPoint(position, CardinalDirection.West)));
		creatures.removeIf(Objects::isNull);
		return creatures.toArray(new Creature[0]);
	}

	public void placeDenizen(Creature denizen) throws CapacityException {
		if(objectTotal >= objectLimit)
			throw new CapacityException();
		while(true) {
			Point position = new Point(RNG.exclusive(getWidth()), RNG.exclusive(getHeight()));
			if(!denizenIsAtPosition(position)) {
				Landmark landmark = getLandmarkAtPosition(position);
				if(landmark == null || !landmark.isSolid()) {
					if(!playerIsNearPosition(position)) {
						placeDenizen(position, denizen);
						objectTotal++;
						return;
					}
				}
			}
		}
	}

	public void placeDenizen(Point position, Creature denizen) {
		denizens.add(denizen);
		denizen.setPosition(position);
	}

	public void removeDenizen(Creature denizen) {
		boolean wasPresent = denizens.remove(denizen);
		if(wasPresent) {
			for(Item i : denizen.getInventory())
				placeItem(denizen.getPosition(), i);
		}
	}

	public void placeLandmark(Landmark landmark) throws CapacityException {
		if(objectTotal >= objectLimit)
			throw new CapacityException();
		while(true) {
			Point position = new Point(RNG.exclusive(getWidth()), RNG.exclusive(getHeight()));
			if(!landmarkIsAtPosition(position)) {
				placeLandmark(position, landmark);
				objectTotal++;
				return;
			}
		}
	}

	public void placeItem(Point position, Item item) {
		if(item.getDescription().equals("autoequip"))
			placeLandmark(position, new LandmarkEquipment((ItemEquipment)item));
		else
			placeLandmark(position, new LandmarkItem(item));
	}

	public void placeLandmark(Point position, Landmark landmark) {
		if(landmarkIsAtPosition(position)) {
			try {
				placeLandmark(landmark);
			}
			catch(CapacityException e) {
				e.printStackTrace();
			}
		}
		else
			landmarks[position.x][position.y] = landmark;
	}

	public void removeLandmark(Point position) {
		landmarks[position.x][position.y] = null;
	}

	public Creature[] getDenizens() {
		Creature player = Game.current().player;
		int index = denizens.indexOf(player);
		if(index > 0) {
			denizens.remove(player);
			denizens.add(0, player);
		}
		return denizens.toArray(new Creature[0]);
	}

	public boolean positionIsSolid(Point position) {
		if(!positionIsInBounds(position))
			return true;
		if(!landmarkIsAtPosition(position))
			return false;
		return getLandmarkAtPosition(position).isSolid();
	}

	public boolean positionIsInBounds(Point position) {
		return (position.x >= 0 && position.x < getWidth() && position.y >= 0 && position.y < getHeight());
	}

	public boolean landmarkIsAtPosition(Point position) {
		return (landmarks[position.x][position.y] != null);
	}

	public Landmark getLandmarkAtPosition(Point position) {
		return landmarks[position.x][position.y];
	}

	public int getWidth() {
		return landmarks.length;
	}

	public int getHeight() {
		return landmarks[0].length;
	}

	@Override
	public String toString() {
		var builder = new StringBuilder();
		for(int y = 0; y < landmarks[0].length; y++) {
			for(int x = 0; x < landmarks.length; x++) {
				Point position = new Point(x, y);
				Creature denizen = getDenizenAtPosition(position);
				Landmark landmark = getLandmarkAtPosition(position);
				if(denizen != null)
					builder.append(denizen);
				else if(landmark != null)
					builder.append(landmark);
				else
					builder.append(Game.DIM_TEXT + "·" + Game.NORMAL_TEXT);
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
