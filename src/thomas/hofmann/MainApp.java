package thomas.hofmann;

import java.awt.Color;

public class MainApp {
	public static void main(String[] args) {
		Window w = new Window("Engie3J", 888, 540);
		Engine e = new Engine(Color.white, w, 90, 1, 100);
	}
}