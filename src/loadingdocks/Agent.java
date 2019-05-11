package loadingdocks;

import java.awt.Point;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import javafx.util.Pair;

public abstract class Agent extends Entity {

	public enum Desire { } //TODO
	public enum Action { moveAhead, rotate, grab, drop, rotateRight, rotateLeft}
	
	public int direction = 90;
	
	public Point initialPoint;
	
	public List<Desire> desires;
	public Pair<Desire,Point> intention;
	public Queue<Action> plan;
	public Action lastAction;
	
	protected Point ahead;
	protected Vector<Point> surr;
	
	public Agent(Point point, String type){ 
		super(point, type);
		
	} 
	
	/**********************
	 **** A: decision ***** 
	 **********************/
	
	
	abstract public void agentComplexDecision();


	/*******************************/
	/**** B: Simple behavior ****/
	/*******************************/

	abstract public void agentSimpleDecision();
	
	/**************************/
	/**** C: communication ****/
	/**************************/
	
	protected void updateBeliefs() {
		ahead = aheadPosition();
		
	}
	
//	private void sendMessage(Object object) {
//		Board.broadcastBeliefs(object);
//	}

//	public void receiveMessage(Object object) {
//	}
	
	/*******************************/
	/**** D: planning auxiliary ****/
	/*******************************/

//	private Queue<Action> buildPathPlan(Point p1, Point p2) {
//		Stack<Point> path = new Stack<Point>();
//		Node node = shortestPath(p1,p2);
//		path.add(node.point);
//		while(node.parent!=null) {
//			node = node.parent;
//			path.push(node.point);
//		}
//		Queue<Action> result = new LinkedList<Action>();
//		p1 = path.pop();
//		int auxdirection = direction;
//		while(!path.isEmpty()) {
//			p2 = path.pop();
//			result.add(Action.moveAhead);
//			result.addAll(rotations(p1,p2));
//			p1 = p2;
//		}
//		direction = auxdirection;
//		result.remove();
//		return result;
//	}
	
//	private List<Action> rotations(Point p1, Point p2) {
//		List<Action> result = new ArrayList<Action>();
//		while(!p2.equals(aheadPosition())) {
//			Action action = rotate(p1,p2);
//			if(action==null) break;
//			execute(action);
//			result.add(action);
//		}
//		return result;
//	}

//	private Action rotate(Point p1, Point p2) {
//		boolean vertical = Math.abs(p1.x-p2.x)<Math.abs(p1.y-p2.y);
//		boolean upright = vertical ? p1.y<p2.y : p1.x<p2.x;
//		if(vertical) {  
//			if(upright) { //move up
//				if(direction!=0) return direction==90 ? Action.rotateLeft : Action.rotateRight;
//			} else if(direction!=180) return direction==90 ? Action.rotateRight : Action.rotateLeft;
//		} else {
//			if(upright) { //move right
//				if(direction!=90) return direction==180 ? Action.rotateLeft : Action.rotateRight;
//			} else if(direction!=270) return direction==180 ? Action.rotateRight : Action.rotateLeft;
//		}
//		return null;
//	}

	
	/********************/
	/**** E: sensors ****/
	/********************/

	/* Check if the cell ahead is floor (which means not a wall, not a shelf nor a ramp) and there are any robot there */
	public boolean isFreeCell() {
	  return Board.getEntity(ahead)==null;
	}
	
	/* Check if the cell ahead contains a box */
	public boolean isVirusAhead(){
		Entity entity = Board.getEntity(ahead);
		return entity!=null && entity instanceof Virus;
	}

	/* Return the type of cell */
//	public Shape cellType() {
//	  return Board.getBlock(ahead).shape;
//	}

	/* Return the color of cell */
//	public Color cellColor() {
//	  return Board.getBlock(ahead).color;
//	}

	/* Check if the cell ahead is a wall */
	protected boolean isWall() {
		return (ahead.x<0 || ahead.y<0 || ahead.x >= Board.nX || ahead.y>=Board.nY);
	}
	
	protected boolean hasCell(int x, int y) {		
		Point p = new Point(x, y);
		return Board.hasCell(p);
	}

//	/* Check if the cell ahead is a wall */
//	private boolean isWall(int x, int y) {
//		return x<0 || y<0 || x>=Board.nX || y>=Board.nY;
//	}

	/**********************/
	/**** F: actuators ****/
	/**********************/

	/* Rotate agent to right */
	public void rotateRandomly() {
		if(random.nextBoolean()) rotateLeft();
		else rotateRight();
	}
	
	/* Rotate agent to right */
	public void rotateRight() {
		direction = (direction+90)%360;
	}
	
	/* Rotate agent to left */
	public void rotateLeft() {
		direction = (direction-90+360)%360;
	}
	
	public void rotateToDirection(Point p) {
		if((faceRight() && (point.y < p.y)) || (faceLeft() && (point.y > p.y)) || (faceUp() && (point.x < p.x)) || (faceDown() && (point.x > p.x))) {
			System.out.println("Right");
			rotateRight();
		}
		else {
			System.out.println("Left");
			rotateLeft();
		}
	}
	
	/* Move agent forward */
	public void moveAhead() {
		Board.updateEntityPosition(point,ahead);
		//if(cargo()) cargo.moveBox(ahead);
		point = ahead;
	}
	
	public void moveTo(Point ahead, Point p) {
		System.out.println(" In moveTo: " + ahead.x + " , " + ahead.y + " , "+ p.x + " , "+ p.y + " , " + Board.getBlock(p).get_concentration());
		if(!isWall() && facesDirection(p)){
			System.out.println("AHEAD");
			moveAhead();
		}
		else{
			rotateToDirection(p);
		}
	}
	
	/**********************/
	/**** G: auxiliary ****/
	/**********************/

//	/* Check if plan is empty */
//	private boolean hasPlan() {
//		return !plan.isEmpty();
//	}

	/* Position ahead */
	protected Point aheadPosition() {
		Point newpoint = new Point(point.x,point.y);
		switch(direction) {
			case 0: newpoint.y++; break;
			case 90: newpoint.x++; break;
			case 180: newpoint.y--; break;
			default: newpoint.x--; 
		}
		return newpoint;
	}
	
	private boolean faceRight() {
		return (point.x < ahead.x && point.y == ahead.y);
	}
	
	private boolean faceLeft() {
		return (point.x > ahead.x && point.y == ahead.y);
	}
	
	private boolean faceUp() {
		return (point.x == ahead.x && point.y < ahead.y);
	}
	
	private boolean faceDown() {
		return (point.x == ahead.x && point.y > ahead.y);
	}
	
	protected boolean facesDirection(Block b) {		
		return facesDirection(b.getPointOfBlock());		
	}
	
	protected boolean facesDirection(Point p) {
		return((point.x < p.x && faceRight() && point.y == p.y) || (point.x > p.x && faceLeft() && point.y == p.y) 
				|| (point.y > p.y && faceDown() && point.x == p.x) || (point.y < p.y && faceUp() && point.x == p.x));
	}
	
//	private boolean isRightOf(Point p1, Point p2) {
//		return (p1.x > p2.x);
//	}
//	
//	private boolean isLeftOf(Point p1, Point p2) {
//		return (p1.x < p2.x);
//	}
//	
//	private boolean isUnder(Point p1, Point p2) {
//		return (p1.y < p2.y);
//	}	
//	private boolean isAbove(Point p1, Point p2) {
//		return (p1.y > p2.y);
//	}
	
	/* For queue used in shortest path */
	public class Node { 
	    Point point;   
	    Node parent; //cell's distance to source 
	    public Node(Point point, Node parent) {
	    	this.point = point;
	    	this.parent = parent;
	    }
	    public String toString() {
	    	return "("+point.x+","+point.y+")";
	    }
	} 
	
//	public Node shortestPath(Point src, Point dest) { 
//	    boolean[][] visited = new boolean[100][100]; 
//	    visited[src.x][src.y] = true; 
//	    Queue<Node> q = new LinkedList<Node>(); 
//	    q.add(new Node(src,null)); //enqueue source cell 
//	    
//		//access the 4 neighbours of a given cell 
//		int row[] = {-1, 0, 0, 1}; 
//		int col[] = {0, -1, 1, 0}; 
//	     
//	    while (!q.isEmpty()){//do a BFS 
//	        Node curr = q.remove(); //dequeue the front cell and enqueue its adjacent cells
//	        Point pt = curr.point; 
//	        for (int i = 0; i < 4; i++) { 
//	            int x = pt.x + row[i], y = pt.y + col[i]; 
//    	        if(x==dest.x && y==dest.y) return new Node(dest,curr); 
//	            if(!isWall(x,y) && !warehouse.containsKey(new Point(x,y)) && !visited[x][y]){ 
//	                visited[x][y] = true; 
//	    	        q.add(new Node(new Point(x,y), curr)); 
//	            } 
//	        }
//	    }
//	    return null; //destination not reached
//	} 

}
