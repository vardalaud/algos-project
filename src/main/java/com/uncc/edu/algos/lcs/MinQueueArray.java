package com.uncc.edu.algos.lcs;
//package done;
///**
// * @author Varda Laud
// *
// */
//public class MinQueueArray {
//
//	public static int getParent(int i) {
//		return i / 2;
//	}
//
//	public static int getLeft(int i) {
//		return 2 * i;
//	}
//
//	public static int getRight(int i) {
//		return 2 * i + 1;
//	}
//
//	public static void buildMinHeap(Vertex[] vertexs) {
//		for (int i = vertexs.length; i > 0; i--) {
//			minHeapify(vertexs, i);
//		}
//	}
//
//	public static void minHeapify(Vertex[] vertexs, int i) {
//		int l = getLeft(i);
//		int r = getRight(i);
//		int largest;
//		if (l <= vertexs.length && vertexs[l].getDist() < vertexs[i].getDist()) {
//			largest = l;
//		} else {
//			largest = i;
//		}
//		if (r <= vertexs.length
//				&& vertexs[r].getDist() < vertexs[largest].getDist()) {
//			largest = r;
//		}
//		if (largest != i) {
//			Vertex temp = vertexs[i];
//			vertexs[i] = vertexs[largest];
//			vertexs[largest] = temp;
//			minHeapify(vertexs, largest);
//		}
//	}
//
//	public static Vertex extractMin(Vertex[] vertexs) {
//		Vertex min = vertexs[1];
//		vertexs[1] = vertexs[vertexs.length];
//		minHeapify(vertexs, 1);
//		return min;
//	}
//
//	public static void heapDecreaseKey(Vertex[] vertexs, int i, float key) {
//		vertexs[i].setDist(key);
//		while (i > 1 && vertexs[getParent(i)].getDist() > vertexs[i].getDist()) {
//			Vertex temp = vertexs[i];
//			vertexs[i] = vertexs[getParent(i)];
//			vertexs[getParent(i)] = temp;
//			i = getParent(i);
//		}
//	}
//}
