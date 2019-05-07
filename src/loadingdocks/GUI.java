package loadingdocks;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static JTextField speed;
	static JPanel boardPanel;
	static JButton run, reset, step;
	private int nX, nY;

	public class Cell extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public Vector<Entity> entities = new Vector<Entity>();
		
        @Override
        protected synchronized void paintComponent(Graphics g) {
        	super.paintComponent(g);
			for(int i = 0; i < entities.size();i++){
				Entity entity = entities.get(i);
        		entity.paint(g);   
        	}
        }
	}

	public GUI() {
		setTitle("BloodCells");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(555, 625);
		add(createButtonPanel());
		
		Board.simpleinitialize();
		Board.associateGUI(this);
		
		boardPanel = new JPanel();
		boardPanel.setSize(new Dimension(500,500));
		boardPanel.setLocation(new Point(20,60));
		
		nX = Board.nX;
		nY = Board.nY;
		boardPanel.setLayout(new GridLayout(nX,nY));
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				boardPanel.add(new Cell());
		
		displayBoard();
		Board.displayObjects();
		update();
		add(boardPanel);
	}

	public synchronized void displayBoard() {
		for(int i=0; i<nX; i++){
			for(int j=0; j<nY; j++){
				int row=nY-j-1, col=i;
				JPanel p = ((JPanel)boardPanel.getComponent(row*nX+col));
				p.setBackground(Color.gray);
				p.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
	}
	
	public synchronized void removeObject(Entity object) {
		int row=nY-object.point.y-1, col=object.point.x;
		Cell p = (Cell)boardPanel.getComponent(row*nX+col);
		p.setBorder(BorderFactory.createLineBorder(Color.white));			
		p.entities.remove(object);
	}
	
	public synchronized void displayObject(Entity object) {
		int row=nY-object.point.y-1, col=object.point.x;
		Cell p = (Cell)boardPanel.getComponent(row*nX+col);
		p.setBorder(BorderFactory.createLineBorder(Color.white));			
		p.entities.add(object);
	}

	public synchronized void update() {
		boardPanel.invalidate();
	}

	private Component createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(600,50));
		panel.setLocation(new Point(0,0));
		
		step = new JButton("Step");
		panel.add(step);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")) Board.step();
				else Board.stop();
			}
		});
		reset = new JButton("Reset");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Board.simplereset();
			}
		});
		run = new JButton("Run");
		panel.add(run);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")){
					int time = -1;
					try {
						time = Integer.valueOf(speed.getText());
					} catch(Exception e){
						JTextPane output = new JTextPane();
						output.setText("Please insert an integer value to set the time per step\nValue inserted = "+speed.getText());
						JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.PLAIN_MESSAGE);
					}
					if(time>0){
						Board.run(time);
	 					run.setText("Stop");
					}
 				} else {
					Board.stop();
 					run.setText("Run");
 				}
			}
		});
		speed = new JTextField(" time per step in [1,100] ");
		speed.setMargin(new Insets(5,5,5,5));
		panel.add(speed);
		
		return panel;
	}
}
