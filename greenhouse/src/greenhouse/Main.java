package greenhouse;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new Applet1().init();
		});
	}

}
