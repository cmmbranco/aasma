package loadingdocks;

import java.awt.Point;
import java.util.Random;

public class Entity extends Thread {

	public String name;
	public Point point;
	protected Random random;

	public Entity(Point point, String type){
		this.point = point;
		this.name = type;
		this.random = new Random();
	} 
}
