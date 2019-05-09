package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class SpecialCell extends Agent{

	public SpecialCell(Point point, String type) {
		super(point, type);
		System.out.println("Special created at: "+ point.getX() +","+ point.getY());
	}

	@Override
	public void agentComplexDecision() {
		updateBeliefs();
		
		Vector<Point> surr = Board.getSurroundingPoints(this);
//		
//		if(hasPlan() && !succeededIntention() && !impossibleIntention()){
//			Action action = plan.remove();
//            if(isPlanSound(action)) execute(action); 
//            else buildPlan();
//            if(reconsider()) deliberate();
//			
//		} else {
//			updateBeliefs();
//			deliberate();
//			buildPlan();
//			if(!hasPlan()) agentReactiveDecision();
//		}
		
	}

	@Override
	public void agentSimpleDecision() {
		ahead = aheadPosition();
		if(isVirusAhead()) {
			Board.deleteVirus(ahead);
		}
		else if(random.nextInt(5) == 0 || isWall()) rotateRandomly();
		else if(isFreeCell()) moveAhead();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.yellow);
    	g.drawRect(15, 15, 20, 20);
    	g.fillRect(15, 15, 20, 20);
	}

}
