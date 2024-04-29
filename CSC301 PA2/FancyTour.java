/*
 * CSC 301-02: Analysis of Algorithms
 * Programming Assignment 2 : Path Finder
 * October 28, 2023
 * Havin Lim
 */


package csc301.f23.pa2;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

import gamecore.algorithms.GraphAlgorithms;
import gamecore.algorithms.GraphAlgorithms.SumComputer;
import gamecore.datastructures.ArrayList;
import gamecore.datastructures.LinkedList;
import gamecore.datastructures.graphs.AdjacencyListGraph;
import gamecore.datastructures.graphs.IGraph;
import gamecore.datastructures.maps.Dictionary;
import gamecore.datastructures.tuples.Pair;

public class FancyTour
{
	/**
	 * This algorithm will, starting from vertex ID {@code start}, travel to each vertex with IDs listed in {@code items}, afterwhich it will go to vertex ID {@code end} to complete its path.
	 * It will also guarantee that this path will be reasonably efficient, travelling no longer than twice the optimal distance necessary to complete this task. 
	 * @param <V> The type of data stored in vertices.
	 * @param <E> The type of data stored in edges.
	 * @param g The graph to travel thorugh. This graph must be strongly connected if directed and connected if directed (or at least every vertex of interest must lie in a connected component).
	 * @param start The start vertex ID where the path starts.
	 * @param items The IDs of the vertices we must visit on our path. These may be visited in any order so long as they appear after {@code start} and before {@code end}.
	 * @param end The end vertex ID where the path must end.
	 * @param cmp The means by which edge weights are compared.
	 * @param sum The means by which edge weights are summed.
	 * @retur
	 * Returns the path through {@code g} that begins with {@code start}, visits each vertex in {@code items} (in any order with the intermediate vertices listed), and ends with {@code end}.
	 * <br><br>
	 * For example, suppose we have a linear graph A -> B -> C -> D -> E.
	 * If start = A, end = E, and items = {C}, then a (and the only) solution to this would return that path {A,B,C,D,E}.
	 */
	public static <V,E> Iterable<Integer> CollectEverything(IGraph<V,E> g, int start, Iterable<Integer> items, int end, Comparator<? super E> cmp, SumComputer<E> sum)
	{
		// The final path of the square to move.
		ArrayList<Integer> fullPath = new ArrayList<>();

		fullPath.add(start);


		// We use hashset to store the items to visit.  
		int curr = start;
		HashSet<Integer> itemsList = new HashSet<>();
		items.forEach(itemsList::add);

		// While there are still blue rings to visit, we search for the shortest path to the next closest blue ring.
		while(!itemsList.isEmpty()) {

			Dictionary<Integer,Pair<E,Integer>> result = GraphAlgorithms.Dijkstra(g, cmp, sum, curr);
			Pair<E,Integer> shortest = null;

			for(Integer item : itemsList) {
				// Compare the weight of the current item to the stored shortest item.
				if(shortest == null || cmp.compare(shortest.Item1, result.Get(item).Item1) > 0) {

					shortest = result.Get(item);
					curr = item;
				}
			}

			itemsList.remove(curr);

			// Find the path to the item
			Iterable<Integer> path = GraphAlgorithms.DijkstraConstructPath(result, curr);

			boolean first = true;


			for(Integer i : path) {

				// Skips the first element in the path iterable.
				if(first) {
					first = false;
					continue;
				}
				fullPath.add(i);
			}
		}

		// Find the path from the last blue ring to the gold ring(end).
		Iterable<Integer> result = GraphAlgorithms.Dijkstra(g, cmp, sum, curr, end);
		result.forEach(fullPath::add);


		return fullPath;
	}
}
