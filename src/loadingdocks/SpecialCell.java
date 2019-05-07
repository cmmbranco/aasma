package loadingdocks;

import java.awt.Point;
import java.util.ArrayList;

public class SpecialCell extends Agent{

	public SpecialCell(Point point, String type) {
		super(point, type);
		System.out.println("Special created at: "+point.getX()+","+point.getY());
	}

	@Override
	public void agentComplexDecision() {
		updateBeliefs();
		
		ArrayList<Point> surr = Board.getSurroundingPoints(this);
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
		if(isWall()) rotateRandomly();
		else if(isVirusAhead()) {
			//TODO delete virus ahead
		}
		else if(random.nextInt(5) == 0) rotateRandomly();
		else moveAhead();
	}

}
