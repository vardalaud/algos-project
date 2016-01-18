package com.uncc.edu.algos.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 * 
 *         Min Heap implementation that is used by Dijkstras Algorithm
 */
public class MinQueue {
	private List<Vertex> vertexs = new ArrayList<Vertex>();

	/**
	 * 
	 * @param values
	 */
	public MinQueue(Collection<Vertex> values) {
		// 0 index is always kept null
		vertexs.add(null);
		vertexs.addAll(values);
	}

	/**
	 * 
	 * @param i
	 * @return index of Parent
	 */
	public int getParent(int i) {
		return i / 2;
	}

	/**
	 * 
	 * @param i
	 * @return index of Left child
	 */
	public int getLeft(int i) {
		return 2 * i;
	}

	/**
	 * 
	 * @param i
	 * @return index of Right child
	 */
	public int getRight(int i) {
		return 2 * i + 1;
	}

	/**
	 * Builds Min Heap by calling MinHeapify iteratively
	 */
	public void buildMinHeap() {
		for (int i = (vertexs.size() - 1) / 2; i > 0; i--) {
			minHeapify(i);
		}
	}

	/**
	 * Min Heapify operation on Min Queue. Floats down an element till its
	 * correct location
	 * 
	 * @param i
	 */
	public void minHeapify(int i) {
		int l = getLeft(i);
		int r = getRight(i);
		int smallest;
		if (l <= (vertexs.size() - 1)
				&& vertexs.get(l).getDist() < vertexs.get(i).getDist()) {
			smallest = l;
		} else {
			smallest = i;
		}
		if (r <= (vertexs.size() - 1)
				&& vertexs.get(r).getDist() < vertexs.get(smallest).getDist()) {
			smallest = r;
		}
		if (smallest != i) {
			Vertex temp = vertexs.get(i);
			vertexs.set(i, vertexs.get(smallest));
			vertexs.set(smallest, temp);
			minHeapify(smallest);
		}
	}

	/**
	 * Extract Min operation for Min Heap
	 * 
	 * @return
	 */
	public Vertex extractMin() {
		// Return 1st element as min
		Vertex min = vertexs.get(1);
		Vertex lastVertex = vertexs.get(vertexs.size() - 1);
		vertexs.remove(lastVertex);
		if (vertexs.size() != 1) {
			// Swap 1st and last elements
			vertexs.set(1, lastVertex);
			// Call MinHeapify
			minHeapify(1);
		}
		return min;
	}

	/**
	 * Decrease Key operation for Min Heap
	 * 
	 * @param vertex
	 * @param newDistance
	 */
	public void heapDecreaseKey(Vertex vertex, float newDistance) {
		// Update distance
		int i = vertexs.indexOf(vertex);
		vertexs.get(i).setDist(newDistance);
		// Float up vertex till its correct location
		while (i > 1
				&& vertexs.get(getParent(i)).getDist() > vertexs.get(i)
						.getDist()) {
			Vertex temp = vertexs.get(i);
			vertexs.set(i, vertexs.get(getParent(i)));
			vertexs.set(getParent(i), temp);
			i = getParent(i);
		}
	}

	/**
	 * Checks if queue is empty
	 * 
	 * @return true if queue empty else false
	 */
	public boolean isEmpty() {
		// If only null value remaining in queue at 0 index then queue is empty
		if (vertexs.size() == 1)
			return true;
		return false;
	}
}
