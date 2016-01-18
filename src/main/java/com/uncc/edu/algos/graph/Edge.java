package com.uncc.edu.algos.graph;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 *
 */
public class Edge {
	private Vertex destinationVertex; // Vertex name
	private boolean up; // Status of edge
	private float weight; // Transmission time

	public Edge(Vertex destinationVertex, float weight) {
		this.destinationVertex = destinationVertex;
		this.weight = weight;
		this.up = true;
	}

	public Vertex getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(Vertex destinationVertex) {
		this.destinationVertex = destinationVertex;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "(" + destinationVertex + ") " + weight + " " + up;
	}
}
