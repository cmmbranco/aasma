package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class SpecialCell extends Agent{

	public SpecialCell(Point point, String type) {
		super(point, type);
		//System.out.println("Special created at: "+ point.getX() +","+ point.getY());
	}

	@Override
	public void agentComplexDecision() {
		updateBeliefs();

		ahead = aheadPosition();
		if(isVirusAhead()) {
			Board.deleteVirus(ahead);
		}
		else {

			surr = Board.getSurroundingPoints(this);
			Gradient p = null;

			for(int i=0; i<surr.size();i++) {
				Gradient pointCheck = Board.getConcentration(surr.get(i));

				if (pointCheck != null) {
					if((p == null && pointCheck.getConcentration() > 0) || (p != null && pointCheck.getConcentration() > p.getConcentration())
							||(p != null && p.getConcentration() == pointCheck.getConcentration() && facesDirection(pointCheck))) { //When equal highest concentrations, favor the block that is in front instead of to the side
						p = pointCheck;
					}
				} 
			}	


			if(p != null) {
				moveTo(ahead, p.point);
			}
			else if(random.nextInt(5) == 0 || isWall()) {
				rotateRandomly();
			}
			else if(isFreeCell()) {
				moveAhead();
			}
		}

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
		g.drawRect(0, 0, 20, 20);
		g.fillRect(0, 0, 20, 20);
	}

}
