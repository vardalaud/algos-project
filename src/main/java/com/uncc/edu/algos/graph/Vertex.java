package com.uncc.edu.algos.graph;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 *
 */
public class Vertex {
	private String name; // Vertex name
	private boolean up; // Status of vertex
	private Map<String, Edge> adj; // Adjacent vertices
	private Vertex prev; // Previous vertex on shortest path
	private float dist; // Distance of path
	private boolean visited;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public Map<String, Edge> getAdj() {
		return adj;
	}

	public void setAdj(Map<String, Edge> adj) {
		this.adj = adj;
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Vertex(String name) {
		this.name = name;
		// TreeMap used for printing in alphabetical order
		this.adj = new TreeMap<String, Edge>();
		this.up = true;
		reset();
	}

	public Vertex() {
	}

	public void reset() {
		this.dist = Constants.INFINITY;
		this.prev = null;
		this.visited = false;
	}

	@Override
	public String toString() {
		return "Name : " + name + ", Dist : " + dist + ", Up : " + up
				+ ", Visited : " + visited;
	}

}
