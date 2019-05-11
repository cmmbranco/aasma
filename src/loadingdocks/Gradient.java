package loadingdocks;

import java.awt.*;

public class Gradient extends Entity {
	int concentration = 100;
	public Gradient(Point p, String string) {
		super(p,string);
	}
	public Gradient(Point p, String string,int _concentration) {
		super(p,string);

		concentration = _concentration;
		//System.out.println(concentration);
	}

	@Override
	public void paint(Graphics g) {
	//	System.out.println(concentration/100.0f);
		g.setColor( new Color(0,0,(int)(255*(concentration/100.0f))));
    	g.drawRect(0, 0, 20, 20);
    	g.fillRect(0, 0, 20, 20);
	}

}
