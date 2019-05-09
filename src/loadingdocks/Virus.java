package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Virus extends Agent{
	public String name;
	public float replicationProb = 0.005f;

	public Virus(Point p, String string) {
		super(p,string);
	}
	@Override
	public void agentSimpleDecision() {
		// TODO
		
		//replicate to a free cell around with a given prob

		if (point.x > 0 && Math.random() < replicationProb) {
			Point p1 = new Point(point.x - 1, point.y);
			Virus v1 = new Virus(p1, name);

			Board.addVirus(v1, 100, name);
			return;
		}
		if (point.x < Board.nX - 1 && Math.random() < replicationProb) {
			Point p1 = new Point(point.x + 1, point.y);
			Virus v1 = new Virus(p1, name);

			Board.addVirus(v1, 100, name);
			return;
		}
		if (point.y > 0 && Math.random() < replicationProb) {
			Point p1 = new Point(point.x, point.y-1);
			Virus v1 = new Virus(p1, name);

			Board.addVirus(v1, 100, name);
			return;
		}

		if (point.y < Board.nY -1 && Math.random() < replicationProb) {
			Point p1 = new Point(point.x, point.y+1);
			Virus v1 = new Virus(p1, name);

			Board.addVirus(v1, 100, name);
			return;
		}
	}
	@Override
	public void agentComplexDecision() {
		// TODO
		/*
		Point p1 = new Point(nX-1,0);
		Point p2 = new Point(nX-1,nY-1);

		Virus v1 = new Virus(p1, "flu");
		Virus v2 = new Virus(p2, "flu");

		objects[p1.x][p1.y] = v1;
		board[p1.x][p1.y]._concentration=100;
		board[p1.x][p1.y]._virname = v1.name;
		virusList.add(v1);

		objects[p2.x][p2.y] = v2;
		board[p2.x][p2.y]._concentration=100;
		board[p2.x][p2.y]._virname = v2.name;
		virusList.add(v2);

		System.out.println(objects[0][0]);
		Board.
		*/
		//replicate to a cell free around with a given prob
		
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
    	g.drawRect(15, 15, 20, 20);
    	g.fillRect(15, 15, 20, 20);
	}

}
