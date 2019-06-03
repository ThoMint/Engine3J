package thomas.hofmann;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileNotFoundException;

public class MainApp implements Engine3J, KeyListener, MouseWheelListener{
	public static void main(String[] args) {
		new MainApp().start();
	}

	World world;
	double rotX,rotY,rotZ;
	double transZ=25;
	
	private void start() {
		Mesh temp;
		
		Window window = new Window("Engie3J", 888, 540);
		Camera camera = new Camera(90, 100, 1);
		RenderEngine engine = new RenderEngine(window, camera, this);
		window.addKeyListener(this);
		window.addMouseWheelListener(this);
		world = new World(camera);
		try {
			temp=OBJLoader.loadFile("object.obj", " ", false, true);
			world.add(temp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		engine.setFPS(600);
		engine.start();
	}

	@Override
	public void loop(RenderEngine re, Graphics g) {
		for (Mesh m : world) {
			m.translate(0, 0, transZ);
			m.rotate(rotX, rotY, rotZ);
			m.render(g);
		}
		re.showFrameRate(g);
		re.capFrameRate();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
			rotX+=0.03;
			break;
		case 's':
			rotX-=0.03;
			break;
		case 'a':
			rotY+=0.03;
			break;
		case 'd':
			rotY-=0.03;
			break;
		case 'q':
			rotZ-=0.03;
			break;
		case 'e':
			rotZ+=0.03;
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		if(m.getWheelRotation()>0) {
			transZ-=1;
		}
		if(m.getWheelRotation()<0) {
			transZ+=1;
		}
	}
}