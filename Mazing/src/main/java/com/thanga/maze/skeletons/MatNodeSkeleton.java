package com.thanga.maze.skeletons;

/**
 * This is the MatNodeSkeleton Interface which declares capability of a node
 */


public interface MatNodeSkeleton {
	public boolean isOpen();
	public boolean isDestination(int rows,int columns);
}
