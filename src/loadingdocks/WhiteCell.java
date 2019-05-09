package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class WhiteCell extends Agent {

	public WhiteCell(Point p, String string) {
		super(p,string);
	}

	@Override
	public void agentComplexDecision() {
		//updateBeliefs();
		
		//ArrayList<Point> surr = Board.getSurroundingPoints(this);
		
		
	}

	@Override
	public void agentSimpleDecision() {
		ahead = aheadPosition();
		if(isVirusAhead()) {
			
			System.out.print("Virus Found at " + ahead.x+"," +ahead.y+" by " + this.name);
			//find location
			String virusname = Board.getEntity(ahead).name;
			
			Vector<Point> surr = Board.getSurroundingPoints(this);
			//System.out.println("# surround " + surr.size());
			Vector<Point> free = new Vector<Point>();
			
			for(Point p : surr) {
				if (!hasCell(p.x,p.y)){
					free.add(p);
				}
			}
			
			if (free.isEmpty()) {
				rotateRandomly();
			}
			else {
				Random randomGenerator = new Random();
				int index = randomGenerator.nextInt(free.size());
				
				Point chosen = free.get(index);
				
				System.out.println("chosen block was " + chosen.x + "," +  chosen.y);
				
				
				
				Board.specialize(this,chosen,virusname);
			}
			
			
		}
		else if(random.nextInt(5) == 0 || isWall()) rotateRandomly();
		else if(isFreeCell()) moveAhead();

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
    	g.drawRect(0, 0, 20, 20);
    	g.fillRect(0, 0, 20, 20);
	}

}
