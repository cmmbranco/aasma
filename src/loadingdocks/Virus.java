package loadingdocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Virus extends Agent{
	public String name;
	public Virus(Point p, String string) {
		super(p,string);
		
	}
	@Override
	public void agentSimpleDecision() {
		// TODO
		
		//replicate to a free cell around with a given prob
		
	}
	@Override
	public void agentComplexDecision() {
		// TODO
		
		//replicate to a cell free around with a given prob
		
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
    	g.drawRect(15, 15, 20, 20);
    	g.fillRect(15, 15, 20, 20);
	}

}
