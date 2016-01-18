package com.uncc.edu.algos.graph;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 *
 */
public class Graph {
	private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();

	/**
	 * Add directed weighted edges in both direction to the graph
	 * 
	 * @param vertex1Name
	 * @param vertex2Name
	 * @param weight
	 */
	private void addBiDirectionalEdge(String vertex1Name, String vertex2Name,
			float weight) {
		Vertex vertex1 = getVertex(vertex1Name);
		Vertex vertex2 = getVertex(vertex2Name);

		addEdge(vertex1, vertex2, weight);
		addEdge(vertex2, vertex1, weight);
	}

	/**
	 * Add directed weighted edge to the graph
	 * 
	 * @param sourceName
	 * @param destinationName
	 * @param weight
	 */
	private void addDirectedEdge(String sourceName, String destinationName,
			float weight) {
		Vertex source = getVertex(sourceName);
		Vertex destination = getVertex(destinationName);

		addEdge(source, destination, weight);
	}

	/**
	 * Adds the edge into source vertices adjacency list
	 * 
	 * @param source
	 * @param destination
	 * @param weight
	 */
	private void addEdge(Vertex source, Vertex destination, float weight) {
		if (source.getAdj().containsKey(destination.getName())) {
			// If edge already present update its weight
			Edge edge = source.getAdj().get(destination.getName());
			edge.setWeight(weight);
		} else {
			// If edge not present add it
			Edge edge = getEdge(destination, weight);
			source.getAdj().put(destination.getName(), edge);
		}
	}

	/**
	 * Removes edge in graph
	 * 
	 * @param sourceName
	 * @param destinationName
	 */
	private void removeEdge(String sourceName, String destinationName) {
		Vertex source = vertexMap.get(sourceName);
		source.getAdj().remove(destinationName);
	}

	/**
	 * Changes the edge status to UP or DOWN
	 * 
	 * @param sourceName
	 * @param destinationName
	 */
	private void changeEdgeStatus(String sourceName, String destinationName,
			boolean up) {
		Vertex source = vertexMap.get(sourceName);
		Edge edge;
		if ((edge = source.getAdj().get(destinationName)) != null) {
			edge.setUp(up);
		} else {
			System.err.println("Invalid edge " + sourceName + "-"
					+ destinationName);
		}
	}

	/**
	 * Changes the vertex status to UP or DOWN
	 * 
	 * @param vertexName
	 * @param up
	 */
	private void changeVertexStatus(String vertexName, boolean up) {
		Vertex vertex = vertexMap.get(vertexName);
		vertex.setUp(up);
	}

	/**
	 * Gets the vertex by name from vertexMap. If its not present in vertexMap
	 * create a new one and add it to vertexMap
	 * 
	 * @param vertexName
	 * @return
	 */
	private Vertex getVertex(String vertexName) {
		Vertex v = vertexMap.get(vertexName);
		if (v == null) {
			v = new Vertex(vertexName);
			vertexMap.put(vertexName, v);
		}
		return v;
	}

	/**
	 * Gets a new Edge
	 * 
	 * @param destination
	 * @param weight
	 * @return
	 */
	private Edge getEdge(Vertex destination, float weight) {
		Edge e = new Edge(destination, weight);
		return e;
	}

	/**
	 * Resets the distance, predecessor and visited fields of every vertex
	 */
	private void clearAll() {
		for (Vertex v : vertexMap.values())
			v.reset();
	}

	/**
	 * Driver routine to print total distance. It calls recursive routine to
	 * print shortest path to destNode after a shortest path algorithm has run.
	 */
	private void printPath(String destName) {
		Vertex w = vertexMap.get(destName);
		if (w.getDist() == Constants.INFINITY)
			System.out.println(destName + " is unreachable");
		else {
			printPath(w);
			System.out.print(" " + w.getDist());
			System.out.println();
		}
	}

	/**
	 * Recursive routine to print shortest path to dest after running shortest
	 * path algorithm. The path is known to exist.
	 */
	private void printPath(Vertex dest) {
		if (dest.getPrev() != null) {
			printPath(dest.getPrev());
			System.out.print(" ");
		}
		System.out.print(dest.getName());
	}

	/**
	 * Prints the Graph by traversing all vertices and their adjacent edges
	 * 
	 */
	private void printGraph() {
		for (Vertex vertex : vertexMap.values()) {
			System.out.print(vertex.getName());
			if (!vertex.isUp()) {
				System.out.println(" DOWN");
			} else {
				System.out.println();
			}
			for (Edge edge : vertex.getAdj().values()) {
				System.out.print("  " + edge.getDestinationVertex().getName()
						+ " " + edge.getWeight());
				if (!edge.isUp()) {
					System.out.println(" DOWN");
				} else {
					System.out.println();
				}
			}
		}
	}

	/**
	 * Dijkstras Algorithm implementation
	 * 
	 * @param startName
	 */
	private void runDijkstrasAlgo(String startName) {
		clearAll();

		Vertex start = vertexMap.get(startName);
		if (!start.isUp())
			return;
		start.setDist(0);

		// Create Build Min Heap
		MinQueue q = new MinQueue(vertexMap.values());
		q.buildMinHeap();

		Set<Vertex> set = new HashSet<Vertex>();
		while (!q.isEmpty()) {
			Vertex vertex = q.extractMin();
			set.add(vertex);
			for (Edge adjEdge : vertex.getAdj().values()) {
				Vertex adjVertex = adjEdge.getDestinationVertex();
				// If the adjacent edge is up and the adjacent vertex is up
				// and the nodes shortest distance is not already determined
				if (adjEdge.isUp() && adjVertex.isUp()
						&& !set.contains(adjVertex)) {
					float newDist = vertex.getDist() + adjEdge.getWeight();
					if (adjVertex.getDist() > newDist) {
						// Perform heap decrease key operation if newDist is
						// smaller than earlier distance
						q.heapDecreaseKey(adjVertex, newDist);
						adjVertex.setPrev(vertex);
					}
				}
			}
		}
	}

	/**
	 * Prints reachable vertices by performing bfs on each vertex. Time
	 * Complexity is O(V*(E+V)) which would be O(V^2) for sparse matrix and
	 * O(V^3) for a dense one.
	 */
	private void printReachable() {
		for (Vertex vertex : vertexMap.values()) {
			if (vertex.isUp()) {
				System.out.println(vertex.getName());
				Set<String> visitedVertices = bfs(vertex);
				for (String visitedVertex : visitedVertices) {
					System.out.println("  " + visitedVertex);
				}
			}
		}
	}

	/**
	 * Performs bfs on vertex
	 * 
	 * @param vertex
	 * @return
	 */
	public Set<String> bfs(Vertex vertex) {
		clearAll();

		// TreeSet for storing visited vertices in alphabetical order
		Set<String> visitedVertices = new TreeSet<String>();
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(vertex);
		vertex.setVisited(true);

		while (!queue.isEmpty()) {
			Vertex v = queue.remove();

			// If the adjacent edge is not down or the adjacent vertex is not
			// down and the node is not already visited
			for (Edge adjEdge : v.getAdj().values()) {
				if (adjEdge.isUp() && adjEdge.getDestinationVertex().isUp()
						&& !adjEdge.getDestinationVertex().isVisited()) {
					visitedVertices.add(adjEdge.getDestinationVertex()
							.getName());
					adjEdge.getDestinationVertex().setVisited(true);
					queue.add(adjEdge.getDestinationVertex());
				}
			}
		}
		return visitedVertices;
	}

	/**
	 * Process a request; return false if quit entered.
	 */
	public boolean processRequest(Scanner in) {
		System.out.print("\nQuery : ");
		String query = in.nextLine();

		StringTokenizer st = new StringTokenizer(query);
		int count = st.countTokens();
		if (count < 1) {
			System.err.println("Invalid command");
			return true;
		}

		String command = st.nextToken();
		if (command.equals("quit") || command.equals("print")
				|| command.equals("reachable")) {
			if (count != 1) {
				System.err.println("Invalid command");
				return true;
			}

			switch (command) {
			case "quit":
				return false;
			case "print":
				printGraph();
				break;
			case "reachable":
				printReachable();
				break;
			}
		} else if (command.equals("vertexup") || command.equals("vertexdown")) {
			if (count != 2) {
				System.err.println("Invalid command");
				return true;
			}
			String vertexName = st.nextToken();
			if (!vertexMap.containsKey(vertexName)) {
				System.err.println("Invalid vertex " + vertexName);
				return true;
			}
			switch (command) {
			case "vertexup":
				changeVertexStatus(vertexName, true);
				break;
			case "vertexdown":
				changeVertexStatus(vertexName, false);
				break;
			}
		} else if (command.equals("path") || command.equals("edgedown")
				|| command.equals("edgeup") || command.equals("deleteedge")) {
			if (count != 3) {
				System.err.println("Invalid command");
				return true;
			}
			String sourceName = st.nextToken();
			if (!vertexMap.containsKey(sourceName)) {
				System.err.println("Invalid vertex " + sourceName);
				return true;
			}
			String destinationName = st.nextToken();
			if (!vertexMap.containsKey(destinationName)) {
				System.err.println("Invalid vertex " + destinationName);
				return true;
			}

			switch (command) {
			case "path":
				runDijkstrasAlgo(sourceName);
				printPath(destinationName);
				break;
			case "edgeup":
				changeEdgeStatus(sourceName, destinationName, true);
				break;
			case "edgedown":
				changeEdgeStatus(sourceName, destinationName, false);
				break;
			case "deleteedge":
				removeEdge(sourceName, destinationName);
				break;
			}
		} else if (command.equals("addedge")) {
			if (count != 4) {
				System.err.println("Invalid command");
				return true;
			}
			String sourceName = st.nextToken();
			String destinationName = st.nextToken();
			float weight = 0;
			try {
				weight = Float.parseFloat(st.nextToken());
			} catch (NumberFormatException e) {
				System.err.println("Invalid command");
				return true;
			}
			addDirectedEdge(sourceName, destinationName, weight);
		} else {
			System.err.println("Invalid command");
		}
		return true;
	}

	/**
	 * A main routine that: 1. Reads a file containing edges (supplied as a
	 * command-line parameter); 2. Forms the graph; 3. Repeatedly prompts for
	 * queries to be executed on graph. The data file is a sequence of lines of
	 * the format source destination weight
	 */
	public static void main(String[] args) {
		Graph g = new Graph();
		try {
			if (args.length != 1) {
				System.out.println("Invalid arguments");
				return;
			}
			FileReader fileReader = new FileReader(args[0]);
			Scanner graphFile = new Scanner(fileReader);

			// Read the edges and insert
			String line;
			while (graphFile.hasNextLine()) {
				line = graphFile.nextLine();
				StringTokenizer st = new StringTokenizer(line);
				try {
					if (st.countTokens() != 3) {
						System.err.println("Skipping ill-formatted line "
								+ line);
						continue;
					}
					String vertex1 = st.nextToken();
					String vertex2 = st.nextToken();
					float weight = Float.parseFloat(st.nextToken());
					g.addBiDirectionalEdge(vertex1, vertex2, weight);
				} catch (NumberFormatException e) {
					System.err.println("Skipping ill-formatted line " + line);
				}
			}
			graphFile.close();
		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("File read");

		Scanner in = new Scanner(System.in);
		while (g.processRequest(in))
			;
	}
}