package loadingdocks;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public abstract class Entity {

	public String name;
	public Point point;
	protected Random random;

	public Entity(Point point, String type){
		this.point = point;
		this.name = type;
		this.random = new Random();
	} 
	
	public abstract void paint(Graphics g);
}
