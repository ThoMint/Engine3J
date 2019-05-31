package thomas.hofmann;

import java.awt.HeadlessException;
import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = -7337775082836981543L;

	public Window(String title, int width, int height) throws HeadlessException {
		super();
		setTitle(title);
		setSize(width, height);
		setLocation(75, 75);
		setFocusable(true);
		setResizable(true);
		requestFocus();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}