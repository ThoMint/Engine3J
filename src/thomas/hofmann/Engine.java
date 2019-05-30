package thomas.hofmann;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Engine extends JPanel {

	private static final long serialVersionUID = -8298272391915136927L;

	private Mesh cube;
	private Camera cam;
	private boolean draw = false;

	public Engine(Color background, Window w, double fieldOfView, double zNear, double zFar) {
		super();
		w.add(this);
		w.validate();
		validate();
		setBackground(background);
		cam = new Camera(getWidth(), getHeight(), fieldOfView, zNear, zFar);
		init();
		repaint();
		draw = true;
	}

	private void init() {
		cube = new Mesh(new double[][][] { 
				{ { -1.0, -1.0, -1.0 }, { -1.0, 1.0, -1.0 }, { 1.0, 1.0, -1.0 } },
				{ { -1.0, -1.0, -1.0 }, { 1.0, 1.0, -1.0 }, { 1.0, -1.0, -1.0 } },
				{ { 1.0, -1.0, -1.0 }, { 1.0, 1.0, -1.0 }, { 1.0, 1.0, 1.0 } },
				{ { 1.0, -1.0, -1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, -1.0, 1.0 } },
				{ { 1.0, -1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { -1.0, 1.0, 1.0 } },
				{ { 1.0, -1.0, 1.0 }, { -1.0, 1.0, 1.0 }, { -1.0, -1.0, 1.0 } },
				{ { -1.0, -1.0, 1.0 }, { -1.0, 1.0, 1.0 }, { -1.0, 1.0, -1.0 } },
				{ { -1.0, -1.0, 1.0 }, { -1.0, 1.0, -1.0 }, { -1.0, -1.0, -1.0 } },
				{ { -1.0, 1.0, -1.0 }, { -1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 } },
				{ { -1.0, 1.0, -1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, -1.0 } },
				{ { 1.0, -1.0, 1.0 }, { -1.0, -1.0, 1.0 }, { -1.0, -1.0, -1.0 } },
				{ { 1.0, -1.0, 1.0 }, { -1.0, -1.0, -1.0 }, { 1.0, -1.0, -1.0 } } });
		cube.setCamera(cam);
		cube.translate(0, 0, 3);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (draw) {
			drawBackground(g);
			cube.render(g);
			repaint();
		}
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}