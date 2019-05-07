package loadingdocks;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);	
					if (Board.finished()) {
						//save stats
						Board.stop();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
