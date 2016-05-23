package com.thanga.maze;

import com.thanga.maze.skeletons.MatNodeSkeleton;

public class MatNode implements MatNodeSkeleton {

	private int x,y;
	private int open=0;
	
	public MatNode(int x,int y, int open) {
		setX(x);
		setY(y);
		setOpen(open);
	}
	
	public boolean isOpen() {
		if (open==1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean isDestination(int rows,int columns) {
		if (this.x == (rows-1) && this.y == (columns-1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return this.x+":"+this.y;
	}

}
