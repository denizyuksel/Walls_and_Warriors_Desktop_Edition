package engine;

import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Handler {

	private static Handler INSTANCE = null;

	private final List<GameObject> objects = new CopyOnWriteArrayList<>();

	private Handler() {}
	//returns the current instance of the handler
	public static Handler getInstance() {
		if(INSTANCE == null) {
			return INSTANCE = new Handler();
		}
		return INSTANCE;
	}
	//This ticks every GameObject(Walls,Board,LevelCreator)
	public void tick() {

		objects.forEach(object -> object.tick());

	}
	//This renders every GameObject(Walls,Board,LevelCreator)
	public void render(Graphics2D g) {
		objects.forEach(object -> object.render(g));
	}

	public boolean add(GameObject o) {
		return objects.add(o);
	}

	public boolean remove(GameObject o) {
		return objects.remove(o);
	}

	public void remove(int i) {
		objects.remove(i);
	}
	//clears all the objects out of the engine
	public void clear() {
		objects.forEach(object -> object = null);
		objects.clear();
	}

	public boolean contains(GameObject o) {
		return objects.contains(o);
	}

	public List<GameObject> getObjects() {
		return objects;
	}
}
