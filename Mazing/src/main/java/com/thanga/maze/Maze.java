package com.thanga.maze;

import java.util.Stack;

public class Maze {
	public static void main(String[] args) {
		int rows = 3; int columns = 3;
		Integer[][] gridHard = new Integer[rows][columns]; 
		
		gridHard[0][0] = 0;
		gridHard[0][1] = 1;
		gridHard[0][2] = 1;
		
		gridHard[1][0] = 0;
		gridHard[1][1] = 0;
		gridHard[1][2] = 1;		
 
		gridHard[2][0] = 1;
		gridHard[2][1] = 1;
		gridHard[2][2] = 0;	
		
		MazeGrid mazegrid = MazeGrid.getInstance(rows,columns,gridHard);
		System.out.println("Grid:");
		System.out.println(mazegrid);

		MouseInEightDirections mouse = new MouseInEightDirections(mazegrid,0,0);
		Stack<String> path = mouse.moveToDestination();		
		System.out.println("Path Found:");
		System.out.println(path);
		
	}
}
