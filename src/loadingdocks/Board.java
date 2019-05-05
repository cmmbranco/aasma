package loadingdocks;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Environment
 * @author Rui Henriques
 */
public class Board {

	/** The environment */

	public static int nX = 23, nY = 23;
	private static Block[][] board = new Block[nX][nY];
	private static Entity[][] objects = new Agent[nX][nY];
	private static ArrayList<Agent> toticells = new ArrayList<Agent>();
	private static ArrayList<Agent> specialized = new ArrayList<Agent>();
	private static ArrayList<Agent> virus = new ArrayList<Agent>();
	
	
	/****************************
	 ***** A: SETTING BOARD *****
	 ****************************/
	
	public static void simpleinitialize() {
		board = new Block[nX][nY];
		objects = new Agent[nX][nY];
		toticells = new ArrayList<Agent>();
		specialized = new ArrayList<Agent>();
		virus = new ArrayList<Agent>();
		
		
		
		
		//Color[] colors = new Color[] {Color.red, Color.blue, Color.green, Color.yellow};
		/** A: create board */
		for(int i=0; i<nX; i++) 
			for(int j=0; j<nY; j++) 
				board[i][j] = new Block(i,j);
				
		/** B: create 2 Virus at bottom right corner and top right*/
		Point p1 = new Point(nX-1,0);
		Point p2 = new Point(nX-1,nY-1);
		
		Virus v1 = new Virus(p1, "flu");
		Virus v2 = new Virus(p2, "flu");
		
		objects[p1.x][p1.y] = v1;
		board[p1.x][p1.y]._concentration=100;
		board[p1.x][p1.y]._virname = v1.name;
		virus.add((Agent) v1);
		
		objects[p2.x][p2.y] = v2;
		board[p2.x][p2.y]._concentration=100;
		board[p2.x][p2.y]._virname = v2.name;
		virus.add((Agent) v2);
		
		
		
		/** Probabilistic approach */
//		for(int i=0; i < nX; i++) {
//			for(int j=0; j < nY; j++) {
//				// virus appear with a 1% probability
//				if (Math.random() <= 0.005) {
//					
//					Point p = new Point(i,j);
//					
//					Virus v = new Virus(p, "flu");
//					
//					objects[i][j] = v;
//					virus.add((Agent) v);
//					board[i][j]._concentration=100;
//					board[i][j]._virname = objects[i][j].name;
//					
//					
//				}
//				
//			}
//		}
		
		/** C: create agents, only 1 toti */

		
		Point p3 = new Point(0,0);
		WhiteCell w1 = new WhiteCell(p3, "white1");
		
		objects[0][0] = w1;
		board[0][0]._concentration=0;
		board[0][0]._virname = "";
		toticells.add((Agent) w1);
		
//		for(int i=0; i < nX; i++) {
//			for(int j=0; j < nY; j++) {
//				// virus appear with a .5% probability
//				if (objects[i][j] == null) {
//					if (Math.random() <= 0.0001) {
//						
//						Point p = new Point(i,j);
//						
//						WhiteCell c = new WhiteCell(p,"wite");
//						
//						objects[i][j] = c;
//						toticells.add((Agent) c);
//						board[i][j]._concentration=0;
//						board[i][j]._virname = null;
//					
//					}
//				}
//			}
//		}
		
		
	}
	
	/****************************
	 ***** B: BOARD METHODS *****
	 ****************************/
	
	public static Entity getEntity(Point point) {
		return objects[point.x][point.y];
	}
	public static Block getBlock(Point point) {
		return board[point.x][point.y];
	}
	public static void updateEntityPosition(Point point, Point newpoint) {
		objects[newpoint.x][newpoint.y] = objects[point.x][point.y];
		objects[point.x][point.y] = null;
	}	
	public static void removeEntity(Point point) {
		objects[point.x][point.y] = null;
	}
	public static void insertEntity(Entity entity, Point point) {
		objects[point.x][point.y] = entity;
	}

	/***********************************
	 ***** C: ELICIT AGENT ACTIONS *****
	 ***********************************/
	
	private static RunThread runThread;
	private static GUI GUI;

	public static class RunThread extends Thread {
		
		int time;
		
		public RunThread(int time){
			this.time = time*time;
		}
		
	    public void run() {
	    	while(true){
	    		step();
				try {
					sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    }
	}
	
	public static void run(int time) {
		Board.runThread = new RunThread(time);
		Board.runThread.start();
	}

	public static void simplereset() {
		removeObjects();
		simpleinitialize();
		while(toticells.isEmpty() || virus.isEmpty()) {
			simpleinitialize();
		}
		
		GUI.displayBoard();
		displayObjects();	
		GUI.update();
	}

//	public static void broadcastBeliefs(Object object) {
//		for(Agent a : robots) a.receiveMessage(object);		
//	}
	
	public static void step() {
		removeObjects();
		for(Agent a : toticells) a.agentSimpleDecision();
		for(Agent a : specialized) a.agentSimpleDecision();
		for(Agent a : virus) a.agentSimpleDecision();
		displayObjects();
		GUI.update();
	}

	public static void stop() {
		runThread.interrupt();
		runThread.stop();
	}

	public static void displayObjects(){
		for(Agent agent : toticells) GUI.displayObject(agent);
		for(Agent agent : virus) GUI.displayObject(agent);
		for(Agent agent : specialized) GUI.displayObject(agent);
		
		
	}
	
	public static void removeObjects(){
		for(Agent agent : toticells) GUI.removeObject(agent);
		for(Agent agent : virus) GUI.removeObject(agent);
		for(Agent agent : specialized) GUI.removeObject(agent);
	}
	
	public static void associateGUI(GUI graphicalInterface) {
		GUI = graphicalInterface;
	}
	
	public static ArrayList<Point> getSurroundingPoints(Agent agent) {
		// TODO Auto-generated method stub
		ArrayList<Point> toret = new ArrayList<Point>();
		
		double xpos = agent.point.getX();
		double ypos = agent.point.getY();
		
		for (double i = -1; i <= 1; i++) {
			for (double j = -1; j <= 1; j++) {
				if ((xpos + i >= 0) && 
						(ypos + j >= 0) && 
						(xpos+i < Board.nX) && 
						(ypos+j) < Board.nY) {
					
					Point p = new Point( (int)(xpos+i), (int)(ypos+j) );
					toret.add(p);
				}
			}
		}
		
		return toret;
	}

	public static boolean hasCell(Point p) {
		// TODO Auto-generated method stub
		
		if(toticells != null) {
			for(Agent a : toticells) {
				if (p.x == a.point.getX() && p.y == a.point.getY()) {
					return true;
				}
			}
		}
		
		if(specialized != null) {
			for(Agent a : specialized) {
				if (p.x == a.point.getX() && p.y == a.point.getY()) {
					return true;
				}
			}
		}
		
		if(virus != null) {
			for(Agent a : virus) {
				if (p.x == a.point.getX() && p.y == a.point.getY()) {
					return true;
				}
			}
		}
		
		return false;
	}

	public static void specialize(WhiteCell whiteCell, Point chosen, String virusname) {
		// TODO Auto-generated method stub
		
		
		Point p = new Point(chosen.x, chosen.y);
		SpecialCell c = new SpecialCell(p, virusname);
		specialized.add(c);
		objects[chosen.x][chosen.y] = c;
		board[chosen.x][chosen.y]._concentration = 100;
		board[chosen.x][chosen.y]._virname="";
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
}
