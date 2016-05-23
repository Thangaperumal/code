package com.thanga.maze;

import java.util.Stack;

import com.thanga.maze.skeletons.Mouse;

public class MouseInEightDirections implements Mouse {
	private int currentX;
	private int currentY;
	private MatNode currentMatNode;
	private MazeGrid mazegrid;
	
	public MouseInEightDirections(MazeGrid mazegrid,int currentX, int currentY) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.mazegrid = mazegrid;
	}
/**
 * Mouse moves in eight directions to find destination. Returns a Stack of path nodes 
 */
	public Stack<String> moveToDestination() {
		
		while(true) {
			currentMatNode = mazegrid.getMatnode(currentX,currentY);
			
			if (currentMatNode.isDestination(mazegrid.getRows(), mazegrid.getColumns())) {
				
				mazegrid.chosenPath.push(currentX+"-"+currentY);
				return mazegrid.chosenPath;
				
			}
			else {
				
				mazegrid.chosenPath.push(currentX+"-"+currentY);

				if (this.moveNorthWest()) {
					continue;
				}
				if (this.moveNorth()) {
					continue;
				}				
				if (this.moveNorthEast()) {
					continue;
				}
				if (this.moveEast()) {
					continue;
				}				
				if (this.moveSouthEast()) {
					continue;
				}
				if (this.moveSouth()) {
					continue;
				}
				if (this.moveSouthWest()) {
					continue;
				}				
				if (this.moveWest()) {
					continue;
				}				
		
				if (mazegrid.chosenPath.size() > 1) {			
					currentMatNode = mazegrid.getMatnode(currentX,currentY);
					currentMatNode.setOpen(1);
					String popout = mazegrid.chosenPath.pop();
					System.out.println("Pop out : " + popout);
					
					String peekout = mazegrid.chosenPath.peek();
					String[] coord = peekout.split("-");
					currentX = Integer.parseInt(coord[0]);
					currentY = Integer.parseInt(coord[1]);
					
					System.out.println(mazegrid.chosenPath);
					System.out.println(mazegrid.toString());
					
					continue;
				}
				else {
					System.out.println("There is no path to reach destination");
					return null;
				}
				
			}
		}
	}
	
	public boolean moveNorth() {
		if ( mazegrid.validNode(currentX+1,currentY)) {
			currentX++;
			return true;
		}
	    return false;
	}
	
	public boolean moveSouth() {
		if ( mazegrid.validNode(currentX-1,currentY)) {
			currentX--;
			return true;
		}
	    return false;
	}	
	
	public boolean moveEast() {
		if ( mazegrid.validNode(currentX,currentY+1)) {
			currentY++;
			return true;
		}
	    return false;
	}	

	public boolean moveWest() {
		if ( mazegrid.validNode(currentX,currentY-1)) {
			currentY--;
			return true;
		}
	    return false;
	}
	
	public boolean moveNorthWest() {
		if ( mazegrid.validNode(currentX+1,currentY-1)) {
			currentX++;
			currentY--;
			return true;
		}
	    return false;
	}
	
	public boolean moveNorthEast() {
		if ( mazegrid.validNode(currentX+1,currentY+1)) {
			currentX++;
			currentY++;
			return true;
		}
	    return false;
	}	
	
	public boolean moveSouthWest() {
		if ( mazegrid.validNode(currentX-1,currentY-1)) {
			currentX--;
			currentY--;
			return true;
		}
	    return false;
	}		

	public boolean moveSouthEast() {
		if ( mazegrid.validNode(currentX-1,currentY+1)) {
			currentX--;
			currentY++;
			return true;
		}
	    return false;
	}			
	


}
