package thomas.hofmann;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RenderEngine extends JPanel {
	private static final long serialVersionUID = -8298272391915136927L;

	public boolean draw = false;

	private  double FPS = 30.0;
	private  double SLEEP = 1000.0 / FPS;
	private long start = System.nanoTime();
	private long end = 0;
	private double diff = 0;
	private long wait = 0;

	private long sampleStart = 0;
	private long sampleEnd = 0;
	private long counter = 0;
	private int avgFPS = 1;
	private boolean lagging;

	private Engine3J e;
	private boolean running = false;

	public RenderEngine(Window w, Camera cam, Engine3J e) {
		super();
		this.e = e;
		w.add(this);
		w.validate();
		validate();
		cam.init(getWidth(), getHeight());
		repaint();
	}
	
	public void setFPS(double FPS) {
		this.FPS = FPS;
		SLEEP = 1000.0 / FPS;
	}

	public void start() {
		running = true;
		repaint();
	}

	public void stop() {
		running = false;
	}

	@Override
	public void paint(Graphics g) {
		if (running) {
			super.paint(g);
			e.loop(this, g);
			repaint();
		}
	}

	public void showFrameRate(Graphics g) {
		if (counter > avgFPS) {
			sampleEnd = System.nanoTime();
			avgFPS = (int) ((counter * 1000000000) / ((sampleEnd - sampleStart)));
			sampleStart = System.nanoTime();
			counter = 0;
		}
		if (lagging) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		g.drawString(String.valueOf(avgFPS) + " fps", 0, 10);
		counter++;
	}

	public void capFrameRate() {
		end = System.nanoTime();
		diff = (end - start) / 1000000.0;
		wait = (long) (SLEEP - diff);
		if (diff > SLEEP) {
			lagging = true;
			wait = 0;
		} else {
			lagging = false;
		}
		if (!lagging) {
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		start = System.nanoTime();
	}
}