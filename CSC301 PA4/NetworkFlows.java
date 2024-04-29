/*
 * CSC301 Programming Assignment 4
 * December/8/2023
 * Author : Havin Lim
 */

package csc301.f23.pa4;

import java.util.Comparator;

import gamecore.algorithms.GraphAlgorithms;
import gamecore.algorithms.GraphAlgorithms.DifferenceComputer;
import gamecore.algorithms.GraphAlgorithms.Flow;
import gamecore.algorithms.GraphAlgorithms.SumComputer;
import gamecore.datastructures.graphs.AdjacencyListGraph;
import gamecore.datastructures.graphs.IGraph;
import gamecore.datastructures.graphs.exceptions.NoSuchVertexException;
import gamecore.datastructures.tuples.Pair;
import gamecore.datastructures.vectors.Vector2i;



public class NetworkFlows
{
	/**
	 * Computes a maximum flow in the given graph using the given sources and sinks.
	 * @param <V> The data type in the vertices.
	 * @param <E> The edge capacity data type.
	 * @param G The graph to find a maximum flow in.
	 * @param sources The flow source vertex IDs in {@code G}.
	 * @param sinks The flow sink vertex IDs in {@code G}.
	 * @param edge_comparer The way we compare edge values.
	 * @param addition The way we add edge values.
	 * @param subtraction The way we subtract edge values.
	 * @param additive_identity The additive identity for the {@code E} type. This is the value Z that, when added or subtracted to a value C, results in C + Z = C - Z = C. In other words, it is the zero value. 
	 * @return Returns a maximum flow through {@code G}.
	 */

     public static <V,E> Flow<V,E> MultiSourceSinkNetworkFlow(AdjacencyListGraph<V,E> G, Iterable<Integer> sources, Iterable<Integer> sinks, Comparator<? super E> edge_comparer, SumComputer<E> addition, DifferenceComputer<E> subtraction, E additive_identity) {

        // Create a new graph that includes the super-source and super-sink
        AdjacencyListGraph<V,E> superG = new AdjacencyListGraph<>(G);

        // Create a super-source and super-sink
        int superSource = superG.AddVertex(null);
        int superSink = superG.AddVertex(null);
    
        // Connect the super-source to each source and the super-sink to each sink with infinite capacity edges
        for (Integer source : sources) {
            superG.AddEdge(superSource, source, (E) Integer.valueOf(Integer.MAX_VALUE));
        }
        for (Integer sink : sinks) {
            superG.AddEdge(sink, superSink, (E) Integer.valueOf(Integer.MAX_VALUE));
        }
    
        // Run the Edmonds-Karp algorithm on the new graph
        return GraphAlgorithms.EdmondsKarp(superG, superSource, superSink, edge_comparer, addition, subtraction, additive_identity);
    }
	
	/**
	 * Computes a circulation in graph.
	 * <br><br>
	 * A number of vertices are designated as supply vertices which have some quantity of flow supply.
	 * For example, if vertex (ID) 0 has a supply of 5, it can output up to 5 flow (i.e. it emits up to 5 more flow than it takes in).
	 * <br><br>
	 * Similarly, a number of vertices are designated as demand vertices which have quantity of flow demand.
	 * For example, if vertex (ID) 3 has a demand of 4, it MUST recieve exactly 4 more flow than it emits.
	 * <br><br>
	 * No supply vertex is a demand vertex nor vice versa.
	 * <br><br>
	 * A circulation is possible if and only if every vertex with a demand can have its demand fully satisfied by a flow through the graph with flow originating from the supply vertices.
	 * @param <V> The data type in the vertices.
	 * @param <E> The edge capacity data type.
	 * @param G The graph to find a circulation in.
	 * @param supplies The supply vertex IDs paired with the quantity of supply they have.
	 * @param demands The demand vertex IDs paired with the quantity of demand they have.
	 * @param edge_comparer The way we compare edge values.
	 * @param addition The way we add edge values.
	 * @param subtraction The way we subtract edge values.
	 * @param additive_identity The additive identity for the {@code E} type. This is the value Z that, when added or subtracted to a value C, results in C + Z = C - Z = C. In other words, it is the zero value. 
	 * @return Returns a circulation (a flow) through {@code G} that satisfies every demand. If that is not possible, then this returns null instead.
	 */
    public static <V,E> Flow<V,E> Circulation(AdjacencyListGraph<V,E> G, Iterable<Pair<Integer,E>> supplies, Iterable<Pair<Integer,E>> demands, Comparator<? super E> edge_comparer, SumComputer<E> addition, DifferenceComputer<E> subtraction, E additive_identity) {
       
        // Create a new graph for super source and super sink
        AdjacencyListGraph<V,E> graph = new AdjacencyListGraph<>(G);
    
        // Adds super source and super sink to the graph
        int superSource = graph.AddVertex(null);
        int superSink = graph.AddVertex(null);
    
        // Connect super source to all supply vertices and super sink to all demand vertices
        for (Pair<Integer,E> supply : supplies)
            graph.AddEdge(superSource, supply.Item1, supply.Item2);

        for (Pair<Integer,E> demand : demands)
            graph.AddEdge(demand.Item1, superSink, demand.Item2);
        
    
        // Run Edmonds Karp Algorithm
        return GraphAlgorithms.EdmondsKarp(graph, superSource, superSink, edge_comparer, addition, subtraction, additive_identity);
    }
}