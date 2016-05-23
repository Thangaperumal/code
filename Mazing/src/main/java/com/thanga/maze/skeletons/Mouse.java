/**
 * This is a Mouse Interface which declares capability of traverse through Maze grid
 */
package com.thanga.maze.skeletons;

import java.util.Stack;

/**
 * @author thanga
 *
 */
public interface Mouse {
	public Stack<String> moveToDestination();
	public boolean moveNorth();
	public boolean moveSouth();
	public boolean moveEast();
	public boolean moveWest();
}
