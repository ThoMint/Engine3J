package thomas.hofmann;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Engine extends JPanel {

	private static final long serialVersionUID = -8298272391915136927L;

	private ArrayList<Mesh> world;
	private Mesh temp;
	private double x = 0.0;
	private final int cubes = 21;

	private Camera cam;
	private boolean draw = false;

	private final double FPS = 60;
	private long start = System.nanoTime();
	private long end = 0;
	private long diff = 0;
	private long wait = 0;
	private int fps = 0;
	private int frameCounter = (int) (FPS);

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
		world = new ArrayList<>();
		for(int i=0;i<cubes;i++) {
				temp = new Mesh(new double[][][] { 
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
				temp.setCamera(cam);
				temp.translate(i*2-(cubes-1), 0.0, cubes+5);
				world.add(temp);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (draw) {
			for(Mesh m : world) {
				m.rotate(x, 0, 0);
				m.render(g);
			}
			x+=0.01;
		}
		frameRate(g);
		repaint();
	}

	private void frameRate(Graphics g) {
		if(frameCounter>FPS) {
			end = System.nanoTime();
			diff = end-start;
			wait = (long) ((1000.0 / FPS)-(diff/1000000000.0));
			if(wait<1) {
				wait=0;
			}
			fps = (int) (1000000000*FPS/diff);
			start = System.nanoTime();
			frameCounter=0;
		}
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		g.drawString(String.valueOf(fps), 0, 10);
		frameCounter++;
	}
}