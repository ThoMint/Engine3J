package thomas.hofmann;

import java.util.ArrayList;

public class World extends ArrayList<Mesh> {
	private static final long serialVersionUID = -5001752962204142419L;
	Camera cam;

	public World(Camera cam) {
		super();
		this.cam = cam;
	}
	
	public boolean add(Mesh m) {
		m.setCamera(cam);
		return super.add(m);
	}
}