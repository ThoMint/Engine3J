package thomas.hofmann;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MainApp implements Engine3J, KeyListener{
	public static void main(String[] args) {
		new MainApp().start();
	}

	World world;
	double x,y,z;

	private void start() {
		Mesh temp;
		
		Window window = new Window("Engie3J", 888, 540);
		Camera camera = new Camera(90, 100, 1);
		RenderEngine engine = new RenderEngine(window, camera, this);
		window.addKeyListener(this);
		world = new World(camera);
		try {
			temp=OBJLoader.loadFile("C:\\Users\\Thomas Hofmann\\Desktop\\plane.obj", " ", false, true);
			world.add(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		engine.setFPS(2440);
		engine.start();
	}

	@Override
	public void loop(RenderEngine re, Graphics g) {
		for (Mesh m : world) {
			m.translate(0, 0, 30);
			m.rotate(x, y, z);
			m.render(g);
		}
		re.showFrameRate(g);
		re.capFrameRate();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
			x+=0.03;
			break;
		case 's':
			x-=0.03;
			break;
		case 'a':
			y+=0.03;
			break;
		case 'd':
			y-=0.03;
			break;
		case 'o':
			world.get(0).outLine=!world.get(0).outLine;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}