package com.thanga.maze;


import java.util.HashMap;
import java.util.Stack;

import org.apache.log4j.Logger;


public class MazeGrid {
	static Logger log = Logger.getLogger(MazeGrid.class.getName());
	public HashMap<String,MatNode> matNodes;
	public Stack<String> chosenPath = new Stack<String>();
	private int rows,columns;
	private static MazeGrid mazegrid = null;
	private MatNode currentMatNode;
	
	private MazeGrid() {} // To avoid the other classes to create an object out of this :)
	
	/**
	 * It is a kind of Singleton based on <rows> and <columns>
	 * @param rows
	 * @param columns
	 * @return
	 */
	public static MazeGrid getInstance(int rows,int columns,Integer[][] gridHard) {
		if (mazegrid instanceof MazeGrid && mazegrid.rows==rows && mazegrid.columns==columns) {
			return mazegrid;
		}
		else {
			mazegrid = new MazeGrid(rows,columns,gridHard);
			return mazegrid;
		}
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public MatNode getMatnode(int x,int y) {
		return this.matNodes.get(x+"-"+y);
	}

	/**
	 * Constructor of the Maze Grid
	 */
	
	private MazeGrid(int rows,int columns,Integer[][] gridHard) {
		try {
			
			matNodes = new HashMap<String,MatNode>();
			setRows(rows);
			setColumns(columns);
			for (int i=0; i<rows;i++) {
				for (int j=0; j<columns;j++) {
					this.matNodes.put(i+"-"+j, new MatNode(i, j, gridHard[i][j]));
				}
			}
		}
		catch (Exception e) {
			log.error(e);
		}
	}
		
	/**
	 * To Print the Maze Grid
	 */
	
	public String toString() {
		MatNode tempNode;
		String out="";		
		try {
			for (int i=this.rows-1; i>=0;i--) {
				for (int j=0; j<this.columns;j++) {
					tempNode = getMatnode(i,j);
					out+="| "+ (tempNode.isOpen()?0:1) +" ";
				}
				out += "|\n";
			}
		}
		catch (Exception e) {
			log.error(e);
		}				
		return out;
	}
	
	public boolean validNode(int x,int y) {
		if (x >= 0 && x < mazegrid.getRows() && y >= 0 && y < mazegrid.getColumns()) {
			currentMatNode = mazegrid.getMatnode(x,y);
			if (currentMatNode.isOpen() && !mazegrid.chosenPath.contains(x+"-"+y)) { 
				return true;
			}
		}
		return false;
	}

}
