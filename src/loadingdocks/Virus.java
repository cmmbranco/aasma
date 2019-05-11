package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.Vector;

public class Virus extends Agent{
	public String name;
	public float replicationProb = 0.03f;

	public Virus(Point p, String string) {
		super(p,string);
	}
	@Override
	public void agentSimpleDecision() {
		//replicate to a free cell around with a given prob
		Vector<Point> surr = Board.getSurroundingPoints(this);
		Vector<Point> free = new Vector<Point>();
		
		for(Point p : surr) {
			if (!hasCell(p.x,p.y)){
				free.add(p);
			}
		}
		
		if (free.isEmpty()) {
			return;
		}
		
		double rand = Math.random();
		Collections.shuffle(free);
		
		Point p1 = free.get(0);
		
		if (rand <= replicationProb) {
			Virus v1 = new Virus(p1, name);

			Board.addVirus(v1, 100, name);
		}

	}
	@Override
	public void agentComplexDecision() {
		
		agentSimpleDecision(); //TODO real implementation
		
		
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
    	g.drawRect(0, 0, 20, 20);
    	g.fillRect(0, 0, 20, 20);
	}

}
